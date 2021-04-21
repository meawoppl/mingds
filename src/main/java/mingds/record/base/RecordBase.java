package mingds.record.base;

import com.google.common.base.Preconditions;
import java.nio.ByteBuffer;
import mingds.parse.RecordParseToken;
import org.antlr.v4.runtime.CommonToken;

public abstract class RecordBase<E> extends CommonToken {
    private byte[] bytes = new byte[0];
    private final RecordType rt;

    public RecordBase(RecordType recordType) {

        super(recordType.getParseTokenID(), recordType.name());
        this.rt = recordType;
    }

    public final RecordType getRecordType() {
        return rt;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        if (getElementSize() == 0) {
            Preconditions.checkArgument(bytes.length == 0);
        } else {
            Preconditions.checkArgument(bytes.length % getElementSize() == 0);
        }
        this.bytes = bytes;
    }

    public ByteBuffer getBuffer() {
        return ByteBuffer.wrap(this.bytes);
    }

    public byte getByte(int i) {
        return this.bytes[i];
    }

    public void setByte(int i, byte v) {
        this.bytes[i] = v;
    }

    public abstract int getElementSize();

    public abstract E getElement(int i);

    public abstract void setElement(int i, E v);

    public int nElements() {
        return this.bytes.length / getElementSize();
    }

    public RecordParseToken getParseToken() {
        RecordType rt = getRecordType();
        return new RecordParseToken(rt.getParseTokenID(), rt.name(), this);
    }
}
