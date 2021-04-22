package mingds.record;

import mingds.record.base.NoData;
import mingds.record.base.RecordType;

public class SRef extends NoData {
    public SRef() {
        this(new byte[0]);
    }

    public SRef(byte[] bytes) {
        super(bytes, RecordType.SREF);
    }
}
