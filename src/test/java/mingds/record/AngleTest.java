package mingds.record;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AngleTest {
    @Test
    public void testInit() {
        new Angle();
    }

    @Test
    public void testGetAngle() {
        Assertions.assertEquals(0.0, new Angle().getAngle());
    }
}
