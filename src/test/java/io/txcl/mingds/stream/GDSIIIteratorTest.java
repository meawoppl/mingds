package io.txcl.mingds.stream;

import io.txcl.mingds.GDSTestFiles;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.record.base.RecordType;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class GDSIIIteratorTest extends GDSTestFiles {
    private void printHex(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            System.out.printf("%02x ", bytes[i]);
        }
        System.out.print("\n");
    }

    @ParameterizedTest
    @MethodSource("getTestPaths")
    public void testStreamWriting(Path path) throws Exception {
        GDSIterator iter = GDSIterator.fromPath(path);
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
}
