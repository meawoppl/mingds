package mingds.record;

import java.util.stream.Stream;
import mingds.TestHelpers;
import mingds.record.base.GDSIIRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecordTests {
    public Stream<Class<? extends GDSIIRecord>> findAllConcreteRecordClasses() {
        return TestHelpers.findNoDataRecords(GDSIIRecord.class, "mingds.record");
    }

    @Test
    public void testFindsRecords() {
        Assertions.assertEquals(32, findAllConcreteRecordClasses().count());
    }
}
