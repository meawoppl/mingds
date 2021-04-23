package mingds.record.base;

public abstract class IntRecord extends GDSIIRecord<Integer> {
    public IntRecord(byte[] bytes, RecordType rt) {
        super(rt);
        setBytes(bytes);
    }

    @Override
    public GDSIITypes getDataType() {
        return GDSIITypes.INT4;
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
