package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.base.AsciiRecord;
import io.txcl.mingds.record.base.RecordType;

public class PropValue extends AsciiRecord {
    public PropValue(byte[] bytes) {
        super(bytes, RecordType.PROPVALUE);
        Preconditions.checkArgument(bytes.length < 126);
        // TODO(meawoppl) there are somewhat complicated context specific length requirements here.
    }
}
