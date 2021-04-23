package mingds.record;

import java.nio.ByteBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TextTypeTest {
    @Test
    public void testInit() {
        byte[] array = ByteBuffer.wrap(new byte[2]).putShort(0, (short) 3).array();
        TextType textType = new TextType(array);
        Assertions.assertEquals(3, textType.getTextType());
    }
}
