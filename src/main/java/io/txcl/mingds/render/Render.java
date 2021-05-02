package io.txcl.mingds.render;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.XY;
import io.txcl.mingds.record.base.GDSIIRecord;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.DoubleSummaryStatistics;
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

    public static Render forRecords(List<? extends GDSIIRecord<?>> records, int size) {
        List<XY> xyRecs =
                records.stream()
                        .filter(r -> r instanceof XY)
                        .map(r -> (XY) r)
                        .collect(Collectors.toList());
        List<Vector2D> xyVecs = xyRecs.stream().flatMap(XY::getXYs).collect(Collectors.toList());
        DoubleSummaryStatistics xss =
                xyVecs.stream().mapToDouble(Vector2D::getX).summaryStatistics();
        DoubleSummaryStatistics yss =
                xyVecs.stream().mapToDouble(Vector2D::getY).summaryStatistics();

        Vector2D lower = new Vector2D(xss.getMin(), yss.getMin());
        Vector2D upper = new Vector2D(xss.getMax(), yss.getMax());

        Render render = new Render(lower, upper, size);

        xyRecs.forEach(
                xy -> {
                    render.strokeSegments(xy.getXYs().collect(Collectors.toList()), Color.BLUE);
                });

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
     * @param contour to color
     * @param color Color to apply to segments
     */
    public void fillSegments(List<Vector2D> points, Color color) {
        int[] xs = new int[points.size()];
        int[] ys = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            int[] xy = mapPointToPix(points.get(i));
            xs[i] = xy[0];
            ys[i] = xy[1];
        }

        doGraphics(
                (g) -> {
                    g.setColor(color);
                    g.fillPolygon(xs, ys, xs.length);
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
