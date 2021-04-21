package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.DoubleRecord;
import mingds.record.base.RecordType;

public class Angle extends DoubleRecord {
    public Angle(byte[] bytes) {
        super(bytes, RecordType.ANGLE);
        Preconditions.checkArgument(bytes.length == 8);
    }
}
