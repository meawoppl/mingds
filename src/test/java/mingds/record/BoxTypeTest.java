package mingds.record;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoxTypeTest {
    @Test
    public void testGetBoxType() {
        BoxType boxType = new BoxType();
        Assertions.assertEquals(0, boxType.getBoxType());

        boxType.setBoxType(2);
        Assertions.assertEquals(2, boxType.getBoxType());
    }
}
