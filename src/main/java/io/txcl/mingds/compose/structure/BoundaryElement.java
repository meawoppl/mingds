package io.txcl.mingds.compose.structure;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.Boundary;
import io.txcl.mingds.record.DType;
import io.txcl.mingds.record.XY;
import io.txcl.mingds.stream.GDSStream;
import java.util.List;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class BoundaryElement extends AbstractElement {
    public static Boundary BOUNDARY = new Boundary();
    public final List<Vector2D> points;

    public BoundaryElement(List<Vector2D> points, int layer) {
        super(BOUNDARY);
        super.setLayer(layer);
        this.points = points;

        Vector2D first = points.get(0);
        Vector2D last = points.get(points.size() - 1);
        Preconditions.checkArgument(first.equals(last));
    }

    @Override
    protected GDSStream getContents() {
        return GDSStream.of(new DType(), new XY(points));
    }

    @Override
    public List<List<Vector2D>> getPolygons() {
        return List.of(points);
    }
}
