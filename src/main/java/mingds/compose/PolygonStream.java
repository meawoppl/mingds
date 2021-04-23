package mingds.compose;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import java.util.List;
import java.util.stream.Stream;
import mingds.record.BgnLib;
import mingds.record.BgnStr;
import mingds.record.Boundary;
import mingds.record.DType;
import mingds.record.EndEl;
import mingds.record.EndLib;
import mingds.record.EndStr;
import mingds.record.Header;
import mingds.record.Layer;
import mingds.record.LibName;
import mingds.record.StrName;
import mingds.record.Units;
import mingds.record.XY;
import mingds.record.base.GDSIIRecord;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class PolygonStream {
    public static Stream<GDSIIRecord<?>> ofPolygons(Stream<List<Vector2D>> polygonStream) {
        // Header and associated whatnot
        Header header = new Header();
        header.setVersion(3);

        BgnLib bgnLib = new BgnLib();
        LibName libName = new LibName("mingds.db");
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
