package mingds.record;

import java.time.LocalDateTime;
import junit.framework.TestCase;

public class TimeRecordTests extends TestCase {
    private static final LocalDateTime TEST_TIME = LocalDateTime.of(2020, 3, 22, 5, 5, 5);

    public void testTimeEncodingBgnLib() {
        BgnLib bgnLib = new BgnLib();
        bgnLib.setAccessTime(TEST_TIME);
        bgnLib.setModifiedTime(TEST_TIME);
        assertEquals(TEST_TIME, bgnLib.getAccessedTime());
        assertEquals(TEST_TIME, bgnLib.getModifiedTime());
    }

    public void testTimeEncodingBgnStr() {
        BgnStr bgnLib = new BgnStr();
        bgnLib.setAccessTime(TEST_TIME);
        bgnLib.setModifiedTime(TEST_TIME);
        assertEquals(TEST_TIME, bgnLib.getAccessedTime());
        assertEquals(TEST_TIME, bgnLib.getModifiedTime());
    }
}
