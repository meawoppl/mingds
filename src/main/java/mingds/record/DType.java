package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class DType extends ShortRecord {
    public DType(byte[] bytes){
        super(bytes);
        Preconditions.checkArgument(bytes.length==2);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.DATATYPE;
    }
}
