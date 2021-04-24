package io.txcl.mingds.record;

import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.record.base.TimeRecord;

public class BgnStr extends TimeRecord {
    public BgnStr() {
        super(RecordType.BGNSTR);
    }

    public BgnStr(byte[] bytes) {
        super(bytes, RecordType.BGNSTR);
    }
}
