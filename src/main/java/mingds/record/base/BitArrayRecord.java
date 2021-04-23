package mingds.record.base;

public abstract class BitArrayRecord extends GDSIIRecord<Boolean> {
    public BitArrayRecord(byte[] bytes, RecordType recordType) {
        super(recordType);
        setBytes(bytes);
    }

    @Override
    public GDSIITypes getDataType() {
        return GDSIITypes.BITARRAY;
    }

    @Override
    public Boolean getElement(int i) {
        throw new RuntimeException("NYI");
    }

    @Override
    public void setElement(int i, Boolean v) {
        throw new RuntimeException("NYI");
    }
}
