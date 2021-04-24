package io.txcl.mingds.record;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimeRecordTests {
    private static final LocalDateTime TEST_TIME = LocalDateTime.of(2020, 3, 22, 5, 5, 5);

    @Test
    public void testTimeEncodingBgnLib() {
        BgnLib bgnLib = new BgnLib();
        bgnLib.setAccessTime(TEST_TIME);
        bgnLib.setModifiedTime(TEST_TIME);
        Assertions.assertEquals(TEST_TIME, bgnLib.getAccessedTime());
        Assertions.assertEquals(TEST_TIME, bgnLib.getModifiedTime());
    }

    @Test
    public void testTimeEncodingBgnStr() {
        BgnStr bgnLib = new BgnStr();
        bgnLib.setAccessTime(TEST_TIME);
        bgnLib.setModifiedTime(TEST_TIME);
        Assertions.assertEquals(TEST_TIME, bgnLib.getAccessedTime());
        Assertions.assertEquals(TEST_TIME, bgnLib.getModifiedTime());
    }
}
