package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class STrans extends ShortRecord {
    public STrans(byte[] bytes) {
        super(bytes, RecordType.STRANS);
        Preconditions.checkArgument(bytes.length == 2);
    }
}
