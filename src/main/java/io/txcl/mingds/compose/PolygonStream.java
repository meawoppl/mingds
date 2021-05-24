package io.txcl.mingds.compose;

import io.txcl.mingds.compose.structure.BoundaryElement;
import io.txcl.mingds.compose.structure.Structure;
import io.txcl.mingds.stream.GDSStream;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class PolygonStream {
    public static GDSStream ofPolygons(int layer, Stream<List<Vector2D>> polygonStream) {
        Structure s = new Structure("polygons");
        polygonStream.forEach(points -> s.addElement(new BoundaryElement(points, layer)));
        GDSBuilder gds = GDSBuilder.empty();
        gds.addStructure(s);

        return gds.stream();
    }
}
