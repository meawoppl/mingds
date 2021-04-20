package mingds.stream;

import junit.framework.TestCase;
import mingds.GdsiiLexer;
import mingds.GdsiiParser;
import mingds.record.base.RecordBase;
import mingds.record.base.RecordType;
import mingds.render.Render;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ListTokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.image.BufferedImage;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GDSIIReaderTest extends TestCase {
    private static Stream<Path> getTestPaths() throws Exception {
        Path root = Path.of("src/test/resources/gds_examples");
        return Files.list(root).map(path-> "/gds_examples/" + path.getFileName().toString()).sorted().map(s->{
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

    public void testGetTests() throws Exception {
        assertTrue(getTestPaths().count() > 6);
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testStreamReading(Path path) throws Exception {
        Map<RecordType, Integer> recordTypeIntegerMap = GDSStreamStats.captureStreamStats(GDSIIReader.from(path));

        recordTypeIntegerMap.keySet().stream().map(Enum::name).sorted().forEach(
            (key) -> {
                int value = recordTypeIntegerMap.get(RecordType.forName(key));
                System.out.printf("Record type: %s count is %d\n", key, value);
            });
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testXYExtraction(Path path) throws Exception {
        Stream<RecordBase<?>> recordBaseStream = GDSIIReader.from(path);
        recordBaseStream.forEach(TestCase::assertNotNull);

        //recordBaseStream.filter(rec->rec instanceof XY).map(rec->(XY) rec).flatMap(XY::getXYs).forEach(System.out::println);
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testXYPlotting(Path path) throws Exception {

        List<RecordBase<?>> recs = GDSIIReader.from(path).collect(Collectors.toList());
        Render render = Render.forRecords(recs, 1024);
        render.saveAsPNG(Path.of("testout").resolve(path.getFileName().toString() + ".png"));
        BufferedImage bi = render.getBi();

        assertEquals(0, 0);
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testPassingGrammar(Path p) throws Exception {
        // Name to tokenID
        List<RecordBase<?>> recs = GDSIIReader.from(p).collect(Collectors.toList());

        // Convert a RecordBase to a equivalent common token type...
        List<CommonToken> tokens = recs.stream().map(RecordBase::getRecordType).map(RecordType::getParseToken).collect(Collectors.toList());

        ListTokenSource lts = new ListTokenSource(tokens);
        CommonTokenStream stream = new CommonTokenStream(lts);
        GdsiiParser parser = new GdsiiParser(stream);
        GdsiiParser.StreamContext sc = parser.stream();

        assertTrue(sc.children.size() > 0);
    }
}
