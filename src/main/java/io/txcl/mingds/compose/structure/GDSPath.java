package io.txcl.mingds.compose.structure;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.txcl.mingds.record.*;
import io.txcl.mingds.stream.GDSStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class GDSPath extends GDSElement {
    private static final int DISPLAY_QUANT = 9;
    private final int dataType;
    private final int pathType;
    private final int width;
    private final List<Vector2D> xy;

    public GDSPath(int dataType, int pathType, int width, List<Vector2D> xy) {
        super(new Path());
        this.dataType = dataType;
        this.pathType = pathType;
        this.width = width;
        this.xy = xy;
        Preconditions.checkArgument(xy.size() >= 2);
    }

    @Override
    protected GDSStream getContents() {
        return GDSStream.of(DType.of(dataType), PathType.of(pathType), Width.of(width), new XY(xy));
    }

    @VisibleForTesting
    protected double getHalfWidth(){
        return width / 2.0;
    }

    @VisibleForTesting
    protected static Vector2D disp(Vector2D a, Vector2D b) {
        System.out.println(a);
        System.out.println(b);
        return b.subtract(a).normalize();
    }

    @VisibleForTesting
    protected static Vector2D normalTo(Vector2D a, Vector2D b) {
        Vector2D dir = disp(a, b);
        return new Vector2D(-dir.getY(), dir.getX());
    }

    private List<Vector2D> doEndCap(Vector2D a, Vector2D b) {
        List<Vector2D> pts = new ArrayList<>();
        final Vector2D norm = normalTo(a, b);
        final Vector2D disp = disp(a, b);

        switch (pathType) {
            case 0:
                // No cap
                return pts;
            case 1:
                // Cap that standsoff by half-width
                pts.add(b.add(norm).add(disp));
                return pts;
            case 2:
                // Rounded cap
                return IntStream.range(1, DISPLAY_QUANT + 1)
                        .mapToDouble(i -> Math.PI * i / DISPLAY_QUANT)
                        .mapToObj(
                                theta ->
                                        b.add(norm.scalarMultiply(Math.cos(theta)))
                                                .add(disp.scalarMultiply(Math.sin(theta))))
                        .collect(Collectors.toList());
        }

        throw new RuntimeException(
                String.format("Value %d is not a valid end cap style", pathType));
    }

    private List<Vector2D> traceRight(List<Vector2D> xy) {
        List<Vector2D> pts = new ArrayList<>();

        for (int i = 0; i < xy.size() - 1; i++) {
            Vector2D a = xy.get(i);
            Vector2D b = xy.get(i + 1);

            Vector2D norm = normalTo(a, b);
            Vector2D disp = norm.scalarMultiply(getHalfWidth());

            if (i == 0) {
                pts.add(a.add(disp));
            }

            pts.add(b.add(disp));
        }

        return pts;
    }

    private List<Vector2D> halfTrace(List<Vector2D> xys) {
        // Loop along the right edge
        List<Vector2D> pts = new ArrayList<>(traceRight(xys));

        Vector2D e1 = xy.get(xys.size() - 2);
        Vector2D e2 = xy.get(xys.size() - 1);

        // Cap it
        pts.addAll(doEndCap(e1, e2));

        return pts;
    }

    @Override
    public void render(Consumer<List<Vector2D>> renderPolygon) {

        renderPolygon.accept(xy);

        List<Vector2D> pts = new ArrayList<>();
        pts.addAll(halfTrace(xy));
        pts.addAll(halfTrace(Lists.reverse(xy)));
        renderPolygon.accept(pts);
    }
}
