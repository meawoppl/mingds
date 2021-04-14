package mingds.record;

import mingds.record.base.NoData;
import mingds.record.base.RecordType;

public class SRef extends NoData {
    public SRef(byte[] bytes){
        super(bytes);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.SREF;
    }
}
