package io.txcl.mingds.render;

import com.google.common.base.Preconditions;
import io.txcl.mingds.compose.structure.AbstractElement;
import io.txcl.mingds.record.XY;
import io.txcl.mingds.record.base.GDSIIRecord;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Render {
    private final int width;
    private final int height;
    private final Vector2D lower;
    private final Vector2D upper;

    private final BufferedImage bi;

    public Render(Box box, int size) {
        this(box.getLower(), box.getUpper(), size);
    }

    public Render(Vector2D lower, Vector2D upper, int size) {
        this.lower = lower;
        this.upper = upper;
        this.width = size;
        this.height = size;

        this.bi = new BufferedImage(size, size, BufferedImage.TYPE_4BYTE_ABGR);

        // Black background fill call
        this.doGraphics(
                g -> {
                    g.setColor(Color.BLACK);
                    g.fillRect(0, 0, this.bi.getWidth(), this.bi.getHeight());
                });
    }

    public static Render forElement(AbstractElement element, int size) {
        List<Vector2D> pts =
                element.getPolygons().stream()
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

        Box box = Box.covering(pts).paddedToSquare().paddedPercent(0.1);
        final Render render = new Render(box, size);
        element.getPolygons().forEach(poly -> render.fillSegments(poly, Color.BLUE));

        return render;
    }

    public static Render forRecords(List<? extends GDSIIRecord<?>> records, int size) {
        List<XY> xyRecs =
                records.stream()
                        .filter(r -> r instanceof XY)
                        .map(r -> (XY) r)
                        .collect(Collectors.toList());
        List<Vector2D> xyVecs = xyRecs.stream().flatMap(XY::getXYs).collect(Collectors.toList());

        final Box box = Box.covering(xyVecs);

        Render render = new Render(box, size);

        xyRecs.forEach(
                xy -> render.strokeSegments(xy.getXYs().collect(Collectors.toList()), Color.BLUE));

        return render;
    }

    /**
     * This is a small wrapper to make sure graphics calls get antialiasing and that the graphics
     * context itself is properly cleaned up.
     */
    public void doGraphics(Consumer<Graphics2D> callable) {
        Graphics2D g = this.bi.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        try {
            callable.accept(g);
        } finally {
            g.dispose();
        }
    }

    /** Perform the math that maps the position in x/y space to pixel h/w space */
    private int[] mapPointToPix(Vector2D pt) {
        double minX = lower.getX();
        double minY = lower.getY();

        double maxX = upper.getX();
        double maxY = upper.getY();

        // pixel-per-x/pixel-per-y
        double ppx = (this.width - 1) / (maxX - minX);
        double ppy = (this.height - 1) / (maxY - minY);

        // NOTE(meawoppl) this is where the height/width -> / V
        // is transformed to more traditional x/y coordinates.
        int px = (int) Math.round((pt.getX() - minX) * ppx);
        int py = (int) (this.height - Math.round((pt.getY() - minY) * ppy));

        return new int[] {px, py};
    }

    public void strokeSegments(List<Vector2D> points, Color color) {
        doGraphics(
                (g) -> {
                    g.setColor(color);
                    for (int i = 0; i < points.size() - 1; i++) {
                        int[] a = mapPointToPix(points.get(i));
                        int[] b = mapPointToPix(points.get(i + 1));
                        g.drawLine(a[0], a[1], b[0], b[1]);
                    }
                });
    }

    /**
     * Stroke all of the segments in `segments` with the `color` specified.
     *
     * @param points that make up a closed contour.
     * @param color Color to apply to segments
     */
    public void fillSegments(List<Vector2D> points, Color color) {
        final GeneralPath path = new GeneralPath(GeneralPath.WIND_NON_ZERO);
        int[] first = mapPointToPix(points.get(0));
        path.moveTo(first[0], first[1]);

        for (Vector2D point : points) {
            int[] xy = mapPointToPix(point);
            path.lineTo(xy[0], xy[1]);
        }

        doGraphics(
                (g) -> {
                    g.setColor(color);
                    g.fill(path);
                });
    }

    /**
     * Save the result to a .png file at `path`
     *
     * @param path the location to save the rendering to.
     */
    public void saveAsPNG(Path path) {
        Preconditions.checkArgument(
                path.toString().endsWith(".png"),
                "Must have a .png extension, got %s",
                path.toString());
        File outputFile = path.toFile();

        try {
            ImageIO.write(this.bi, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getBi() {
        return bi;
    }
}
