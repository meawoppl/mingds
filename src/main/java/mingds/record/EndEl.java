package mingds.record;

import mingds.record.base.NoData;
import mingds.record.base.RecordType;

public class EndEl extends NoData {
    public EndEl(byte[] bytes) {
        super(bytes);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.ENDEL;
    }
}
