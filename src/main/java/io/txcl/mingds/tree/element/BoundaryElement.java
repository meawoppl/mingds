package io.txcl.mingds.tree.element;

import com.google.common.base.Preconditions;
import io.txcl.mingds.GdsiiParser;
import io.txcl.mingds.record.*;
import io.txcl.mingds.stream.GDSStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    public Map<Integer, List<List<Vector2D>>> getPolygons() {
        Map<Integer, List<List<Vector2D>>> map = new HashMap<>();
        map.put(getLayer(), List.of(points));
        return map;
    }

    public static BoundaryElement fromRecords(GdsiiParser.BoundaryElementContext records) {
        XY xyRec = (XY) records.xy().start;
        Layer layerRec = (Layer) records.layer().start;
        final BoundaryElement boundaryElement =
                new BoundaryElement(
                        xyRec.getXYs().collect(Collectors.toList()), layerRec.getLayer());
        if(records.elflags() != null){
            boundaryElement.setElflags((ElFlags) records.elflags().start);
        }

        if(records.plex() != null){
            boundaryElement.setPlex((Plex) records.elflags().start);
        }

        return boundaryElement;
    }
}
