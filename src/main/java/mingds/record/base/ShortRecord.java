package mingds.record.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public abstract class ShortRecord extends RecordBase<Short> {
    @Override
    public int getElementSize() { return 2; }

    public ShortRecord(int nShorts){
        setBytes(new byte[2*nShorts]);
    }

    public ShortRecord(byte[] raw){
        setBytes(raw);
    }

    @Override
    public void setElement(int i, Short v) {
        ByteBuffer.wrap(getBytes()).putShort(i, v);
    }

    @Override
    public Short getElement(int i) {
        return ByteBuffer.wrap(getBytes()).getShort(i);
    }

    @Override
    public int nElements() {
        return getBytes().length / 2;
    }

}
