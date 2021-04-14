package mingds.record.base;

public abstract class IntRecord extends RecordBase<Integer> {
    public IntRecord(byte[] bytes){
        setBytes(bytes);
    }

    @Override
    public int getElementSize() {
        return 4;
    }

    @Override
    public Integer getElement(int i) {
        return this.getBuffer().getInt(i);
    }

    @Override
    public void setElement(int i, Integer v) {
        this.getBuffer().putInt(i, v);
    }
}
