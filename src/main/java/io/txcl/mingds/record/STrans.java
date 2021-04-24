package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.base.BitArrayRecord;
import io.txcl.mingds.record.base.RecordType;

public class STrans extends BitArrayRecord {
    public STrans() {
        this(new byte[2]);
    }

    public STrans(byte[] bytes) {
        super(bytes, RecordType.STRANS);
        Preconditions.checkArgument(bytes.length == 2);
    }
}
