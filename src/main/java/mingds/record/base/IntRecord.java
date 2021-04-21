package mingds.record.base;

public abstract class IntRecord extends RecordBase<Integer> {
    public IntRecord(byte[] bytes, RecordType rt) {
        super(rt);
        setBytes(bytes);
    }

    @Override
    public int getElementSize() {
        return 4;
    }

    @Override
    public Integer getElement(int i) {
        return this.getBuffer().getInt(i * getElementSize());
    }

    @Override
    public void setElement(int i, Integer v) {
        this.getBuffer().putInt(i, v);
    }
}
