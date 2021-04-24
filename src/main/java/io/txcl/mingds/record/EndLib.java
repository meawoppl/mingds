package io.txcl.mingds.record;

import io.txcl.mingds.record.base.NoData;
import io.txcl.mingds.record.base.RecordType;

public class EndLib extends NoData {
    public EndLib() {
        this(new byte[0]);
    }

    public EndLib(byte[] bytes) {
        super(bytes, RecordType.ENDLIB);
    }
}
