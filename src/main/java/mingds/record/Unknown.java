package mingds.record;

import mingds.record.base.RecordBase;
import mingds.record.base.RecordType;

public class Unknown extends RecordBase<Void> {
    private final int reportedType;

    public Unknown(byte[] bytes, int reportedType) {
        this.reportedType = reportedType;
        this.setBytes(bytes);
    }

    @Override
    public int getElementSize() {
        return 1;
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.NULL;
    }

    @Override
    public Void getElement(int i) {
        throw new RuntimeException("?");
    }

    @Override
    public void setElement(int i, Void v) {
        throw new RuntimeException("?");
    }
}
