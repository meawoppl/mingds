package io.txcl.mingds.compose.structure;

import io.txcl.mingds.record.Boundary;
import io.txcl.mingds.record.DType;
import io.txcl.mingds.record.XY;
import io.txcl.mingds.stream.GDSStream;
import java.util.List;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class GDSBoundary extends GDSElement {
    public static Boundary BOUNDARY = new Boundary();
    public final List<Vector2D> points;

    public GDSBoundary(List<Vector2D> points, int layer) {
        super(BOUNDARY);
        setLayer(layer);
        this.points = points;
    }

    @Override
    protected GDSStream getContents() {
        return GDSStream.of(new DType(), new XY(points));
    }
}
