package io.txcl.mingds.record;

import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.record.base.TimeRecord;

public class BgnLib extends TimeRecord {
    public BgnLib() {
        super(RecordType.BGNLIB);
    }

    public BgnLib(byte[] bytes) {
        super(bytes, RecordType.BGNLIB);
    }
}
