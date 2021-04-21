package mingds.record;

import mingds.record.base.RecordType;
import mingds.record.base.TimeRecord;

public class BgnStr extends TimeRecord {
    public BgnStr(byte[] bytes) {
        super(bytes, RecordType.BGNSTR);
    }
}
