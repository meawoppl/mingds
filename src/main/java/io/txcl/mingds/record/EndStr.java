package io.txcl.mingds.record;

import io.txcl.mingds.record.base.NoData;
import io.txcl.mingds.record.base.RecordType;

public class EndStr extends NoData {
    public EndStr() {
        this(new byte[0]);
    }

    public EndStr(byte[] bytes) {
        super(bytes, RecordType.ENDSTR);
    }
}
