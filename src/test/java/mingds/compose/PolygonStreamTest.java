package mingds.compose;

import com.google.common.collect.Streams;
import mingds.record.base.GDSIIRecord;
import mingds.stream.GDSIIValidator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PolygonStreamTest  {
    @Test
    public void testEmpty() throws GDSIIValidator.ValidationException {
        List<GDSIIRecord<?>> records = PolygonStream.ofPolygons(Stream.empty()).collect(Collectors.toList());
        GDSIIValidator.validateRecords(records);
    }

    public void testOfPolygons() {
    }

    public void testPolygonStruct() {
    }
}
