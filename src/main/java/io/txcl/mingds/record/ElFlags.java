package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.base.BitArrayRecord;
import io.txcl.mingds.record.base.RecordType;

public class ElFlags extends BitArrayRecord {
    public ElFlags(byte[] bytes) {
        super(bytes, RecordType.ELFLAGS);
        Preconditions.checkArgument(nElements() <= 2);
    }
}
