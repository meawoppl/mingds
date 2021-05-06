package io.txcl.mingds.compose;

import com.google.common.collect.Lists;
import io.txcl.mingds.compose.structure.GDSBoundary;
import io.txcl.mingds.record.Boundary;
import io.txcl.mingds.record.DType;
import io.txcl.mingds.record.EndEl;
import io.txcl.mingds.record.Layer;
import io.txcl.mingds.record.XY;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class PolygonStream {
    public static GDSStream ofPolygons(int layer, Stream<List<Vector2D>> polygonStream) {
        Structure s = new Structure("polygons");
        polygonStream.forEach(points -> s.addElement(new GDSBoundary(points, layer)));
        GDSBuilder gds = GDSBuilder.empty();
        gds.addStructure(s);

        return gds.stream();
    }

    public static List<GDSIIRecord<?>> polygonElement(List<Vector2D> points) {
        return Lists.newArrayList(
                new Boundary(), new Layer(), new DType(), new XY(points), new EndEl());
    }
}
