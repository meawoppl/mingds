package mingds.record;

import mingds.record.base.RecordType;

public class BgnStr extends BgnLib {
    @Override
    public RecordType getRecordType() {
        return RecordType.BGNSTR;
    }

    public BgnStr(byte[] bytes) {
        super(bytes);
    }
}
