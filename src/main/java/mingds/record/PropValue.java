package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.AsciiRecord;
import mingds.record.base.RecordType;

public class PropValue extends AsciiRecord {
    public PropValue(byte[] bytes) {
        super(bytes);
        Preconditions.checkArgument(bytes.length < 126);
        // TODO(meawoppl) there are somewhat complicated context specific length requirements here.
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.PROPVALUE;
    }
}
