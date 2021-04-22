package mingds.record;

import junit.framework.TestCase;

public class DTypeTest extends TestCase {
    public void testGetDataType() {
        DType dType = new DType();
        assertEquals(0, dType.getDType());

        dType.setDataType(2);
        assertEquals(2, dType.getDType());
    }
}
