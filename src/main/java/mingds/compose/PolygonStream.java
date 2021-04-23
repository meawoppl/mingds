package mingds.compose;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import mingds.record.BgnLib;
import mingds.record.Boundary;
import mingds.record.DType;
import mingds.record.EndEl;
import mingds.record.EndLib;
import mingds.record.Header;
import mingds.record.Layer;
import mingds.record.LibName;
import mingds.record.Units;
import mingds.record.XY;
import mingds.record.base.GDSIIRecord;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;
import java.util.stream.Stream;

public class PolygonStream {
    public static Stream<GDSIIRecord<?>> ofPolygons(Stream<List<Vector2D>> polygonStream){
        // Header and associated whatnot
        Header header = new Header();
        header.setVersion(3);

        BgnLib bgnLib = new BgnLib();
        LibName libName = new LibName("mingds.db");
        Stream<GDSIIRecord<?>> frontMatter = Stream.of(header, bgnLib, libName, new Units());

        // Actual polygon data
        Stream<GDSIIRecord<?>> polygons = polygonStream.flatMap(pts->polygonStruct(pts).stream());

        // Footer
        Stream<GDSIIRecord<?>> footer = Stream.of(new EndLib());
        return Streams.concat(frontMatter, polygons, footer);
    }

    public static List<GDSIIRecord<?>> polygonStruct(List<Vector2D> points){
        return Lists.newArrayList(
            new Boundary(), new Layer(), new DType(), new XY(points), new EndEl()
        );
    }
}
