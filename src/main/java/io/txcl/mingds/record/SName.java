package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.base.AsciiRecord;
import io.txcl.mingds.record.base.RecordType;

public class SName extends AsciiRecord {
    public SName(byte[] bytes) {
        super(bytes, RecordType.SNAME);
        Preconditions.checkArgument(bytes.length < 32);
    }
}
