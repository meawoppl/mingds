package mingds.record;

import mingds.record.base.AsciiRecord;
import mingds.record.base.RecordType;

public class StrName extends AsciiRecord {
    public StrName(byte[] bytes) {
        super(bytes, RecordType.STRNAME);
    }

    public StrName(String name) {
        super(name, RecordType.STRNAME);
    }
}
