package mingds.record;

import mingds.record.base.IntRecord;
import mingds.record.base.RecordType;

public class XY extends IntRecord {
    public XY(byte[] bytes){
        super(bytes);
    }
    @Override
    public RecordType getRecordType() {
        return RecordType.XY;
    }
}
