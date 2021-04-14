package mingds;

import junit.framework.TestCase;
import mingds.record.base.RecordType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GDSIIReaderTest extends TestCase {
    private static Stream<Path> getTestPaths() {
        return Stream.of(
            "/gds_examples/inverter.gds2",
            "/gds_examples/xor.gds2",
            "/gds_examples/nand.gds2",
            "/gds_examples/ring.gds",
            "/gds_examples/dcp1.gds",
            "/gds_examples/h_write.gds"
        ).map(s->{
            URL resource = GDSIIReaderTest.class.getResource(s);
            Path p;
            try {
                p = Paths.get(resource.toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            return p;
        });
    }

    public void testGetTests() {
        assertEquals(6, getTestPaths().count());
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testStreamReading(Path path) throws Exception {
        Map<RecordType, Integer> recordTypeIntegerMap = GDSStreamStats.captureStreamStats(GDSIIReader.from(path));

        recordTypeIntegerMap.forEach((key, value) -> System.out.printf("Record type: %s count is %d\n", key, value));
    }
}
