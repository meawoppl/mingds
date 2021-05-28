package io.txcl.mingds.stream;

import io.txcl.mingds.GDSTestFiles;
import io.txcl.mingds.tree.GDSBuilder;
import io.txcl.mingds.record.XY;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.render.Render;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GDSIIStreamsTest extends GDSTestFiles {
    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testStreamReading(Path path) throws Exception {
        Map<RecordType, Integer> recordTypeIntegerMap =
                GDSStreamStats.captureStreamStats(GDSStream.from(path));

        recordTypeIntegerMap.keySet().stream()
                .map(Enum::name)
                .sorted()
                .forEach(
                        (key) -> {
                            int value = recordTypeIntegerMap.get(RecordType.forName(key));
                            System.out.printf("Record type: %s count is %d\n", key, value);
                        });
    }

    @Test
    public void testStreamWriting(@TempDir Path path) throws Exception {
        Path gdsPath = path.resolve("tempout.gds");
        GDSStream.to(gdsPath, GDSBuilder.empty().stream());
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testStreamReadWrite(Path path) throws Exception {
        List<GDSIIRecord<?>> records = GDSStream.from(path).collect(Collectors.toList());

        // For every record in the test suite, make sure it serializes
        // back to how it was loaded
        for (int i = 0; i < records.size(); i++) {
            GDSIIRecord<?> rec = records.get(i);
            byte[] truncated = GDSStream.serialize(GDSStream.of(rec));
            GDSIIRecord<?> rec2 = GDSStream.from(truncated).findFirst().get();
            Assertions.assertArrayEquals(rec.serialize(), rec2.serialize());
        }
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testXYExtraction(Path path) throws Exception {
        GDSStream recordBaseStream = GDSStream.from(path);

        recordBaseStream
                .filter(rec -> rec instanceof XY)
                .map(rec -> (XY) rec)
                .forEach(
                        xy -> {
                            Assertions.assertNotNull(xy);
                            Assertions.assertTrue(xy.getXYs().count() > 0);
                        });
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testXYPlotting(Path path) throws Exception {
        List<GDSIIRecord<?>> recs = GDSStream.from(path).collect(Collectors.toList());
        Render render = Render.forRecords(recs, 1024);
        // render.saveAsPNG(Path.of("testout").resolve(path.getFileName().toString() + ".png"));
        BufferedImage bi = render.getBi();

        Assertions.assertEquals(0, 0);
    }
}
