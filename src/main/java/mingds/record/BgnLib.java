package mingds.record;

import mingds.record.base.RecordType;
import mingds.record.base.TimeRecord;

public class BgnLib extends TimeRecord {
    public BgnLib(byte[] bytes) {
        super(bytes, RecordType.BGNLIB);
    }
}
