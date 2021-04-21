package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class ElFlags extends ShortRecord {
    public ElFlags(byte[] bytes) {
        super(bytes, RecordType.ELFLAGS);
        Preconditions.checkArgument(nElements() <= 1);
    }
}
