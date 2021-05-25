package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.format.ByteMunging;
import io.txcl.mingds.record.base.AsciiRecord;
import io.txcl.mingds.record.base.RecordType;

public class StringRecord extends AsciiRecord {
    public StringRecord(String string) {
        this(ByteMunging.fromJavaString(string));
    }

    public StringRecord(byte[] bytes) {
        super(bytes, RecordType.STRING);
        Preconditions.checkArgument(bytes.length <= 512);
    }
}
