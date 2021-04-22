package mingds.record;

import junit.framework.TestCase;

public class AngleTest extends TestCase {

    public void testGetAngle() {
        assertEquals(0.0, new Angle().getAngle());
    }
}
