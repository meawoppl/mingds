package mingds.record.base;

import com.google.common.base.Preconditions;

public abstract class NoData extends RecordBase<Void> {
    public NoData(byte[] bytes) {
        Preconditions.checkArgument(bytes.length == 0);
    }

    @Override
    public int getElementSize() {
        return 0;
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
