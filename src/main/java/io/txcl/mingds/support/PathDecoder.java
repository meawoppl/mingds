package io.txcl.mingds.support;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import java.awt.geom.PathIterator;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.CombinatoricsUtils;

public class PathDecoder {
    private static final int FONT_QUANT = 10;

    private static Vector2D splineMath(Vector2D[] pts, double t) {
        Vector2D summed = Vector2D.ZERO;

        for (int i = 0; i < pts.length; i++) {
            Vector2D p = pts[i];
            summed = summed.add(p.scalarMultiply(PathDecoder.bernPoly(pts.length - 1, i, t)));
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

    private static List<Vector2D> splineTo(Vector2D... points) {
        double[] ts = linspace(0, 1, PathDecoder.FONT_QUANT);
        return Arrays.stream(ts).mapToObj(t -> splineMath(points, t)).collect(Collectors.toList());
    }

    public static List<List<Vector2D>> processPathIterator(PathIterator pi) {
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

        return contours;
    }

    private static double bernPoly(int n, int m, double t) {
        double comb = CombinatoricsUtils.binomialCoefficient(n, m);
        return comb * Math.pow(t, m) * Math.pow(1 - t, n - m);
    }
}
