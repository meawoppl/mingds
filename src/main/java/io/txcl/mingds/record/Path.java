package io.txcl.mingds.record;

import io.txcl.mingds.record.base.NoData;
import io.txcl.mingds.record.base.RecordType;

public class Path extends NoData {
    public Path() {
        this(new byte[0]);
    }

    public Path(byte[] bytes) {
        super(bytes, RecordType.PATH);
    }
}
