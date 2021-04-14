package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.AsciiRecord;
import mingds.record.base.RecordType;

public class StringRecord extends AsciiRecord {
    public StringRecord(byte[] bytes){
        super(bytes);
        Preconditions.checkArgument(bytes.length <= 512);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.STRING;
    }
}
