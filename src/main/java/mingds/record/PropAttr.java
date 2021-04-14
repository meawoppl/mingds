package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class PropAttr extends ShortRecord {
    public PropAttr(byte[] bytes){
        super(bytes);
        Preconditions.checkArgument(bytes.length == 2);

        short value = getElement(0);
        Preconditions.checkArgument(value >= 1);
        Preconditions.checkArgument(value <= 127);
    }

    public int getValue() {
        return getElement(0);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.PROPATTR;
    }
}
