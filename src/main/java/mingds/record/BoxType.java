package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class BoxType extends ShortRecord {
    public BoxType(byte[] bytes) {
        super(bytes);
        Preconditions.checkArgument(nElements() == 1);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.BOXTYPE;
    }
}
