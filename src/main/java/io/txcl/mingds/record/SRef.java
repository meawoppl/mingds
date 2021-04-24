package io.txcl.mingds.record;

import io.txcl.mingds.record.base.NoData;
import io.txcl.mingds.record.base.RecordType;

public class SRef extends NoData {
    public SRef() {
        this(new byte[0]);
    }

    public SRef(byte[] bytes) {
        super(bytes, RecordType.SREF);
    }
}
