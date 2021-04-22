package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.BitArrayRecord;
import mingds.record.base.RecordType;

public class ElFlags extends BitArrayRecord {
    public ElFlags(byte[] bytes) {
        super(bytes, RecordType.ELFLAGS);
        Preconditions.checkArgument(nElements() <= 2);
    }
}
