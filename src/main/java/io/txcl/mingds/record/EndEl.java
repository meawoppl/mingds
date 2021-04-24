package io.txcl.mingds.record;

import io.txcl.mingds.record.base.NoData;
import io.txcl.mingds.record.base.RecordType;

public class EndEl extends NoData {
    public EndEl() {
        this(new byte[0]);
    }

    public EndEl(byte[] bytes) {
        super(bytes, RecordType.ENDEL);
    }
}
