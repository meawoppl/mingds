package mingds.record;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DTypeTest {
    @Test
    public void testInit() {
        new DType();
    }

    @Test
    public void testGetDataType() {
        DType dType = new DType();
        Assertions.assertEquals(0, dType.getDType());

        dType.setDataType(2);
        Assertions.assertEquals(2, dType.getDType());
    }
}
