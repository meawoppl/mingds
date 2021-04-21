package mingds.record.base;

import com.google.common.base.Preconditions;
import mingds.format.ByteMunging;

public abstract class AsciiRecord extends RecordBase<String> {
    @Override
    public int getElementSize() {
        return 1;
    }

    public AsciiRecord(byte[] bytes, RecordType rt) {
        super(rt);
        Preconditions.checkArgument(bytes.length >= 2);
        Preconditions.checkArgument(bytes.length % 2 == 0);
        setBytes(bytes);
    }

    public AsciiRecord(String string, RecordType rt) {
        this(ByteMunging.fromJavaString(string), rt);
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
