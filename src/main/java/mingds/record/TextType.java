package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class TextType extends ShortRecord {
    public TextType(byte[] bytes) {
        super(bytes, RecordType.TEXTTYPE);
        Preconditions.checkArgument(nElements() == 1);

        short value = getElement(0);
        if (value < 0 || value > 255) {
            System.err.printf("Value for TEXTTYPE outside expected bounds: %d\n", value);
        }
    }

    public int getTextType() {
        return getElement(0);
    }

    public void setTextType(int value) {
        Preconditions.checkArgument(value >= 0, "%d", value);
        Preconditions.checkArgument(value <= 255, "%d", value);
        setElement(0, (short) value);
    }
}
