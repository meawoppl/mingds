package mingds.compose;

import com.google.common.collect.Streams;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Stream;

public class PolygonStreamTest  {
    @Test
    public void testEmpty(){
        PolygonStream.ofPolygons(Stream.of(new ArrayList<>()));
    }

    public void testOfPolygons() {
    }

    public void testPolygonStruct() {
    }
}
