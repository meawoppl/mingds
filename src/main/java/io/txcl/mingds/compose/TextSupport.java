package io.txcl.mingds.compose;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.txcl.mingds.record.base.GDSIIRecord;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.CombinatoricsUtils;

public class TextSupport {
    private static final int FONT_QUANT = 10;

    private static double bernPoly(int n, int m, double t) {
        double comb = CombinatoricsUtils.binomialCoefficient(n, m);
        return comb * Math.pow(t, m) * Math.pow(1 - t, n - m);
    }

    private static Vector2D quadMath(Vector2D cp, Vector2D p1, Vector2D p2, double t) {
        Vector2D term0 = cp.scalarMultiply(bernPoly(2, 0, t));
        Vector2D term1 = p1.scalarMultiply(bernPoly(2, 1, t));
        Vector2D term2 = p2.scalarMultiply(bernPoly(2, 2, t));
        return term0.add(term1).add(term2);
    }

    private static Vector2D cubicMath(
            Vector2D cp, Vector2D p1, Vector2D p2, Vector2D p3, double t) {
        Vector2D term0 = cp.scalarMultiply(bernPoly(3, 0, t));
        Vector2D term1 = p1.scalarMultiply(bernPoly(3, 1, t));
        Vector2D term2 = p2.scalarMultiply(bernPoly(3, 2, t));
        Vector2D term3 = p3.scalarMultiply(bernPoly(3, 3, t));
        return term0.add(term1).add(term2).add(term3);
    }

    private static Vector2D splineMath(Vector2D[] pts, double t) {
        Vector2D summed = Vector2D.ZERO;

        for (int i = 0; i < pts.length; i++) {
            Vector2D p = pts[i];
            summed = summed.add(p.scalarMultiply(bernPoly(pts.length - 1, i, t)));
        }
        return summed;
    }

    /**
     * A basic implementation of <a
     * href="https://en.wikipedia.org/wiki/Linear_interpolation#Programming_language_support">Linear
     * interpolation</a>
     *
     * @param v0 Value when t=0
     * @param v1 Value when t=1
     * @param t A value which describes the relative ratios of `a` and `b`
     * @return interpolated values
     */
    public static double lerp(double v0, double v1, double t) {
        return (1 - t) * v0 + t * v1;
    }

    private static double[] linspace(double start, double stop, int n) {
        Preconditions.checkArgument(n >= 2, "linspace() requires 2 or more steps got %s", n);

        double[] ret = new double[n];
        for (int i = 0; i < n; i++) {
            ret[i] = lerp(start, stop, (double) i / (n - 1));
        }
        return ret;
    }

    private static List<Vector2D> quadTo(Vector2D cp, Vector2D p1, Vector2D p2) {
        double[] ts = linspace(0, 1, FONT_QUANT);
        return Arrays.stream(ts)
                .mapToObj(t -> quadMath(cp, p1, p2, t))
                .collect(Collectors.toList());
    }

    private static List<Vector2D> cubeTo(Vector2D cp, Vector2D p1, Vector2D p2, Vector2D p3) {
        double[] ts = linspace(0, 1, FONT_QUANT);
        return Arrays.stream(ts)
                .mapToObj(t -> cubicMath(cp, p1, p2, p3, t))
                .collect(Collectors.toList());
    }

    private static List<Vector2D> splineTo(Vector2D... points) {
        double[] ts = linspace(0, 1, FONT_QUANT);
        return Arrays.stream(ts).mapToObj(t -> splineMath(points, t)).collect(Collectors.toList());
    }

    private static List<Vector2D> processPathIterator(PathIterator pi) {
        double[] raw = new double[6];

        List<List<Vector2D>> contours = Lists.newArrayList();
        List<Vector2D> currentLoop = null;
        while (!pi.isDone()) {
            int type = pi.currentSegment(raw);
            Vector2D[] pts =
                    new Vector2D[] {
                        new Vector2D(raw[0], -raw[1]),
                        new Vector2D(raw[2], -raw[3]),
                        new Vector2D(raw[4], -raw[5])
                    };

            switch (type) {
                case PathIterator.SEG_MOVETO:
                    {
                        currentLoop = Lists.newArrayList(pts[0]);
                    }
                    break;
                case PathIterator.SEG_CLOSE:
                    {
                        // TODO(meawoppl) zip to end?
                        Preconditions.checkNotNull(currentLoop);
                        contours.add(currentLoop);
                        currentLoop = null;
                    }
                    break;
                case PathIterator.SEG_LINETO:
                    {
                        Preconditions.checkNotNull(currentLoop);
                        currentLoop.add(pts[0]);
                    }
                    break;
                case PathIterator.SEG_CUBICTO:
                    {
                        Preconditions.checkNotNull(currentLoop);
                        Vector2D last = currentLoop.get(currentLoop.size() - 1);
                        currentLoop.addAll(splineTo(last, pts[0], pts[1], pts[2]));
                    }
                    break;
                case PathIterator.SEG_QUADTO:
                    {
                        Preconditions.checkNotNull(currentLoop);
                        Vector2D last = currentLoop.get(currentLoop.size() - 1);
                        currentLoop.addAll(splineTo(last, pts[0], pts[1]));
                    }
                    break;
                default:
                    throw new RuntimeException("Unreachable?");
            }
            pi.next();
        }

        // Stitch the endpoints to make hole features come out with the right partiy
        List<Vector2D> flattened = new ArrayList<>();

        for (int i = 0; i < contours.size(); i++) {
            List<Vector2D> thisContour = contours.get(i);
            if (flattened.isEmpty()) {
                flattened.addAll(thisContour);
                continue;
            }

            Vector2D lastPoint = flattened.get(flattened.size() - 1);
            flattened.addAll(thisContour);
            flattened.add(lastPoint);
        }

        return flattened;
    }

    public static List<List<Vector2D>> spoolGlyphVector(GlyphVector gv) {
        List<List<Vector2D>> lol = Lists.newArrayList();
        int nGlyphs = gv.getNumGlyphs();
        for (int i = 0; i < nGlyphs; i++) {
            PathIterator pathIterator =
                    gv.getGlyphOutline(i).getPathIterator(gv.getGlyphTransform(i));
            List<Vector2D> glyphPath = processPathIterator(pathIterator);
            if (glyphPath.size() <= 2) {
                continue;
            }
            lol.add(glyphPath);
        }
        return lol;
    }

    public static int POINT_SIZE = 14;

    public static List<List<Vector2D>> textToPolygons(String text, double targetHeightMM) {
        return textToPolygons(text, targetHeightMM, Vector2D.ZERO);
    }

    public static List<List<Vector2D>> textToPolygons(
            String text, double targetHeightMM, Vector2D location) {
        // This constant makes me kinda angry, but this is the world we live in so... yolo:
        // https://en.wikipedia.org/wiki/Point_(typography)
        double startHeightMM = POINT_SIZE * 0.3527 * 2;
        double scale = targetHeightMM / startHeightMM;
        AffineTransform xform = AffineTransform.getScaleInstance(scale, scale);
        xform.translate(location.getX(), location.getY());
        FontRenderContext fontRenderContext = new FontRenderContext(xform, true, true);
        Font font = new Font("Noto Mono", Font.PLAIN, POINT_SIZE).deriveFont(xform);

        // GlyphVector glyphVector = font.createGlyphVector(fontRenderContext, text);
        GlyphVector glyphVector =
                font.layoutGlyphVector(
                        fontRenderContext,
                        text.toCharArray(),
                        0,
                        text.length(),
                        Font.LAYOUT_LEFT_TO_RIGHT);
        // TODO(meawoppl) resize here...
        return TextSupport.spoolGlyphVector(glyphVector);
    }

    public static List<String> availableFonts() {
        return Lists.newArrayList(
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    }

    public static Stream<GDSIIRecord<?>> textToPolygonRecords(
            String text, double heightMM, Vector2D location) {
        return PolygonStream.ofPolygons(textToPolygons(text, heightMM, location).stream());
    }
}
