package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class TextType extends ShortRecord {
    public TextType(byte[] bytes) {
        super(bytes, RecordType.TEXTTYPE);
        Preconditions.checkArgument(nElements() == 1);

        short value = getElement(0);
        Preconditions.checkArgument(value >= 0);
        Preconditions.checkArgument(value <= 255);
    }
}
