package io.txcl.mingds.compose;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import java.util.List;
import java.util.stream.Stream;
import io.txcl.mingds.record.BgnLib;
import io.txcl.mingds.record.BgnStr;
import io.txcl.mingds.record.Boundary;
import io.txcl.mingds.record.DType;
import io.txcl.mingds.record.EndEl;
import io.txcl.mingds.record.EndLib;
import io.txcl.mingds.record.EndStr;
import io.txcl.mingds.record.Header;
import io.txcl.mingds.record.Layer;
import io.txcl.mingds.record.LibName;
import io.txcl.mingds.record.StrName;
import io.txcl.mingds.record.Units;
import io.txcl.mingds.record.XY;
import io.txcl.mingds.record.base.GDSIIRecord;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class PolygonStream {
    public static Stream<GDSIIRecord<?>> ofPolygons(Stream<List<Vector2D>> polygonStream) {
        // Header and associated whatnot
        Header header = new Header();
        header.setVersion(3);

        BgnLib bgnLib = new BgnLib();
        LibName libName = new LibName("io.txcl.mingds.db");
        Stream<GDSIIRecord<?>> frontMatter = Stream.of(header, bgnLib, libName, new Units());

        Stream<GDSIIRecord<?>> struct = Stream.of(new BgnStr(), new StrName("ply"));

        // Actual polygon data
        Stream<GDSIIRecord<?>> polygons =
                polygonStream.flatMap(pts -> polygonElement(pts).stream());

        // Footer
        Stream<GDSIIRecord<?>> footer = Stream.of(new EndStr(), new EndLib());
        return Streams.concat(frontMatter, struct, polygons, footer);
    }

    public static List<GDSIIRecord<?>> polygonElement(List<Vector2D> points) {
        return Lists.newArrayList(
                new Boundary(), new Layer(), new DType(), new XY(points), new EndEl());
    }
}
