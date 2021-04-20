package mingds.record;

import mingds.record.base.NoData;
import mingds.record.base.RecordType;

public class Boundary extends NoData {
    public Boundary() {
        super(new byte[0]);
    }

    public Boundary(byte[] bytes) {
        super(bytes);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.BOUNDARY;
    }
}
