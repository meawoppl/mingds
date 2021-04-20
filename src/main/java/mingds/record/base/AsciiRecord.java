package mingds.record.base;

import com.google.common.base.Preconditions;
import mingds.format.ByteMunging;

public abstract class AsciiRecord extends RecordBase<String> {
    @Override
    public int getElementSize() {
        return 1;
    }

    public AsciiRecord(byte[] bytes) {
        this(bytes.length);
        setBytes(bytes);
    }

    public AsciiRecord(int size) {
        Preconditions.checkArgument(size >= 2);
        Preconditions.checkArgument(size % 2 == 0);
        setBytes(new byte[size]);
    }

    public AsciiRecord(String string) {
        setBytes(ByteMunging.fromJavaString(string));
    }

    @Override
    public String getElement(int i) {
        throw new RuntimeException();
    }

    @Override
    public void setElement(int i, String v) {
        throw new RuntimeException();
    }

    @Override
    public int nElements() {
        return getBytes().length;
    }
}
