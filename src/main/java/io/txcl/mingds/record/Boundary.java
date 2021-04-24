package io.txcl.mingds.record;

import io.txcl.mingds.record.base.NoData;
import io.txcl.mingds.record.base.RecordType;

public class Boundary extends NoData {
    public Boundary() {
        super(new byte[0], RecordType.BOUNDARY);
    }

    public Boundary(byte[] bytes) {
        super(bytes, RecordType.BOUNDARY);
    }
}
