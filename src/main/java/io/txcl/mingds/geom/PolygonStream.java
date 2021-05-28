package io.txcl.mingds.geom;

import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.tree.Library;
import io.txcl.mingds.tree.Structure;
import io.txcl.mingds.tree.element.BoundaryElement;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class PolygonStream {
    public static GDSStream ofPolygons(int layer, Stream<List<Vector2D>> polygonStream) {
        Structure s = new Structure("polygons");
        polygonStream.forEach(points -> s.addElement(new BoundaryElement(points, layer)));
        Library gds = Library.empty();
        gds.addStructure(s);

        return gds.stream();
    }
}
