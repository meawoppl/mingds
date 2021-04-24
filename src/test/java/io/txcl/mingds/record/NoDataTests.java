package io.txcl.mingds.record;

import java.util.stream.Stream;
import io.txcl.mingds.TestHelpers;
import io.txcl.mingds.record.base.NoData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class NoDataTests {
    public static Stream<Class<? extends NoData>> findNoDataRecords() {
        return TestHelpers.findNoDataRecords(NoData.class, "io.txcl.mingds.record");
    }

    @Test
    public void testSimple() {
        Assertions.assertEquals(8, findNoDataRecords().count());
    }

    @Test
    public void testGetSetCallsThrow() {
        Boundary b = new Boundary();
        Assertions.assertThrows(RuntimeException.class, () -> b.getElement(0));
        Assertions.assertThrows(RuntimeException.class, () -> b.setElement(0, null));
    }

    @ParameterizedTest
    @MethodSource("findNoDataRecords")
    public void testNoDataClassHasConstructor(Class<?> cls) throws Exception {
        Assertions.assertNotNull(cls.getConstructor().newInstance());
    }
}
