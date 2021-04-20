package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class ElFlags extends ShortRecord {
    public ElFlags(byte[] bytes) {
        super(bytes);
        Preconditions.checkArgument(nElements() <= 1);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.ELFLAGS;
    }
}
