package io.txcl.mingds.stream;

import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.render.Render;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GDSIIStreamTest {
    public static Stream<Path> getTestPaths() throws Exception {
        Path root = Path.of("src/test/resources/gds_examples");
        return Files.list(root)
                .map(path -> "/gds_examples/" + path.getFileName().toString())
                .sorted()
                .map(
                        s -> {
                            URL resource = GDSIIStreamTest.class.getResource(s);
                            Path p;
                            try {
                                p = Paths.get(resource.toURI());
                            } catch (URISyntaxException e) {
                                throw new RuntimeException(e);
                            }
                            return p;
                        });
    }

    @Test
    public void testGetTests() throws Exception {
        Assertions.assertTrue(getTestPaths().count() > 6);
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testStreamReading(Path path) throws Exception {
        Map<RecordType, Integer> recordTypeIntegerMap =
                GDSStreamStats.captureStreamStats(GDSIIStream.from(path));

        recordTypeIntegerMap.keySet().stream()
                .map(Enum::name)
                .sorted()
                .forEach(
                        (key) -> {
                            int value = recordTypeIntegerMap.get(RecordType.forName(key));
                            System.out.printf("Record type: %s count is %d\n", key, value);
                        });
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testStreamReadWrite(Path path) throws Exception {
        List<GDSIIRecord<?>> records = GDSIIStream.from(path).collect(Collectors.toList());

        for (int i = 0; i < records.size(); i++) {
            GDSIIRecord<?> rec = records.get(i);
            byte[] truncated = GDSIIStream.serialize(Stream.of(rec));
            GDSIIRecord<?> rec2 = GDSIIStream.from(truncated).findFirst().get();
            Assertions.assertArrayEquals(rec.serialize(), rec2.serialize());
        }
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testStreamWriting(Path path) throws Exception {
        FileInputStream bis = new FileInputStream(path.toFile());
        DataInputStream dis = new DataInputStream(bis);

        GDSIIIterator iter = new GDSIIIterator(dis);
        while (iter.hasNext()) {
            byte[] encoded = iter.nextBlock();
            GDSIIRecord<?> rec = GDSIIRecord.deserialize(encoded);
            byte[] rt = rec.serialize();

            if (!Arrays.equals(encoded, rt)) {
                printHex(encoded);
                printHex(rt);
            }
            Assertions.assertArrayEquals(
                    encoded, rt, String.format("Failed for record type: %s", rec.getRecordType()));
            if (rec.getRecordType() == RecordType.ENDLIB) {
                // 0 padding requires a manual break here :/
                break;
            }
        }
    }

    public void printHex(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            System.out.printf("%02x ", bytes[i]);
        }
        System.out.print("\n");
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testXYExtraction(Path path) throws Exception {
        Stream<GDSIIRecord<?>> recordBaseStream = GDSIIStream.from(path);
        recordBaseStream.forEach(Assertions::assertNotNull);

        // recordBaseStream.filter(rec->rec instanceof XY).map(rec->(XY)
        // rec).flatMap(XY::getXYs).forEach(System.out::println);
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testXYPlotting(Path path) throws Exception {
        List<GDSIIRecord<?>> recs = GDSIIStream.from(path).collect(Collectors.toList());
        Render render = Render.forRecords(recs, 1024);
        // render.saveAsPNG(Path.of("testout").resolve(path.getFileName().toString() + ".png"));
        BufferedImage bi = render.getBi();

        Assertions.assertEquals(0, 0);
    }
}
