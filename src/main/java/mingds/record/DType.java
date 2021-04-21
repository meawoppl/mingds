package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class DType extends ShortRecord {
    public DType(byte[] bytes) {
        super(bytes, RecordType.DATATYPE);
        Preconditions.checkArgument(bytes.length == 2);
    }
}
