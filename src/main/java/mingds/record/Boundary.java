package mingds.record;

import mingds.record.base.NoData;
import mingds.record.base.RecordType;

public class Boundary extends NoData {
    public Boundary() {
        super(new byte[0], RecordType.BOUNDARY);
    }

    public Boundary(byte[] bytes) {
        super(bytes, RecordType.BOUNDARY);
    }
}
