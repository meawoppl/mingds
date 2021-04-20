package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.DoubleRecord;
import mingds.record.base.RecordType;

public class Angle extends DoubleRecord {
    public Angle(byte[] bytes) {
        super(bytes);
        Preconditions.checkArgument(bytes.length == 8);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.ANGLE;
    }
}
