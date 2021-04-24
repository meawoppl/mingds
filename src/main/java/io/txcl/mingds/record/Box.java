package io.txcl.mingds.record;

import io.txcl.mingds.record.base.NoData;
import io.txcl.mingds.record.base.RecordType;

public class Box extends NoData {
    public Box() {
        this(new byte[0]);
    }

    public Box(byte[] bytes) {
        super(bytes, RecordType.BOX);
    }
}
