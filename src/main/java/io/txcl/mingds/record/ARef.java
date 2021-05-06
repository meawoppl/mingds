package io.txcl.mingds.record;

import io.txcl.mingds.record.base.NoData;
import io.txcl.mingds.record.base.RecordType;

public class ARef extends NoData {
    public ARef() {
        this(new byte[0]);
    }

    public ARef(byte[] bytes) {
        super(bytes, RecordType.AREF);
    }
}
