package io.txcl.mingds.record;

import io.txcl.mingds.record.base.AsciiRecord;
import io.txcl.mingds.record.base.RecordType;

public class StrName extends AsciiRecord {
    public StrName(byte[] bytes) {
        super(bytes, RecordType.STRNAME);
    }

    public StrName(String name) {
        super(name, RecordType.STRNAME);
    }
}
