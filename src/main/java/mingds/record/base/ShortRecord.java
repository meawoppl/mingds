package mingds.record.base;

public abstract class ShortRecord extends RecordBase<Short> {
    @Override
    public int getElementSize() {
        return 2;
    }

    public ShortRecord(int nShorts, RecordType rt) {
        super(rt);
        setBytes(new byte[2 * nShorts]);
    }

    public ShortRecord(byte[] raw, RecordType rt) {
        super(rt);
        setBytes(raw);
    }

    @Override
    public void setElement(int i, Short v) {
        getBuffer().putShort(i * 2, v);
    }

    @Override
    public Short getElement(int i) {
        return getBuffer().getShort(i * 2);
    }

    @Override
    public int nElements() {
        return getBytes().length / 2;
    }
}
