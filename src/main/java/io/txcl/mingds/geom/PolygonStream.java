package io.txcl.mingds.geom;

import io.txcl.mingds.tree.GDSBuilder;
import io.txcl.mingds.tree.structure.BoundaryElement;
import io.txcl.mingds.tree.structure.Structure;
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
