package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.AsciiRecord;
import mingds.record.base.RecordType;

public class SName extends AsciiRecord {

    public SName(byte[] bytes) {
        super(bytes);
        Preconditions.checkArgument(bytes.length < 32);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.SNAME;
    }
}
