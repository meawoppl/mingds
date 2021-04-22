package mingds.record;

import junit.framework.TestCase;

public class BoxTypeTest extends TestCase {
    public void testGetBoxType() {
        BoxType boxType = new BoxType();
        assertEquals(0, boxType.getBoxType());

        boxType.setBoxType(2);
        assertEquals(2, boxType.getBoxType());
    }
}
