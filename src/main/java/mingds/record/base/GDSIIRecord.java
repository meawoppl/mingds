package mingds.record.base;

import com.google.common.base.Preconditions;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import mingds.format.ByteMunging;
import mingds.parse.RecordParseToken;
import mingds.record.Angle;
import mingds.record.BgnLib;
import mingds.record.BgnStr;
import mingds.record.Boundary;
import mingds.record.Box;
import mingds.record.BoxType;
import mingds.record.DType;
import mingds.record.ElFlags;
import mingds.record.EndEl;
import mingds.record.EndLib;
import mingds.record.EndStr;
import mingds.record.Header;
import mingds.record.Layer;
import mingds.record.LibName;
import mingds.record.Mag;
import mingds.record.Path;
import mingds.record.PathType;
import mingds.record.Plex;
import mingds.record.Presentation;
import mingds.record.PropAttr;
import mingds.record.PropValue;
import mingds.record.SName;
import mingds.record.SRef;
import mingds.record.STrans;
import mingds.record.StrName;
import mingds.record.StringRecord;
import mingds.record.Text;
import mingds.record.TextType;
import mingds.record.Units;
import mingds.record.Width;
import mingds.record.XY;
import org.antlr.v4.runtime.CommonToken;

public abstract class GDSIIRecord<E> extends CommonToken {
    private byte[] bytes = new byte[0];
    private final RecordType rt;

    public GDSIIRecord(RecordType recordType) {
        super(recordType.getParseTokenID(), recordType.name());
        this.rt = recordType;
    }

    public RecordType getRecordType() {
        return rt;
    }

    public abstract GDSIITypes getDataType();

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        if (getElementSize() == 0) {
            Preconditions.checkArgument(bytes.length == 0);
        } else {
            Preconditions.checkArgument(bytes.length % getElementSize() == 0);
        }
        this.bytes = Arrays.copyOf(bytes, bytes.length);
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

    public int getElementSize() {
        switch (getDataType()) {
            case NODATA:
                return 0;
            case BITARRAY:
            case ASCII:
                return 1;
            case INT2:
                return 2;
            case INT4:
            case REAL4:
                return 4;
            case REAL8:
                return 8;
        }
        throw new RuntimeException("Unexpected DataType: " + getDataType());
    };

    public abstract E getElement(int i);

    public abstract void setElement(int i, E v);

    public int nElements() {
        if (getElementSize() == 0) {
            return 0;
        }
        return this.bytes.length / getElementSize();
    }

    public RecordParseToken getParseToken() {
        RecordType rt = getRecordType();
        return new RecordParseToken(rt.getParseTokenID(), rt.name(), this);
    }

    public byte[] serialize() {
        int recordSize = 4 + nElements() * getElementSize();
        byte[] ret = new byte[recordSize];
        Preconditions.checkArgument(recordSize < Short.MAX_VALUE);

        ByteBuffer bb = ByteBuffer.wrap(ret);
        bb.putShort((short) (recordSize & 0xffff)); // Unsigned short
        bb.put((byte) getRecordType().getCode());
        bb.put((byte) getDataType().getCode());
        Preconditions.checkArgument(bb.remaining() == bytes.length);
        bb.put(bytes);

        return ret;
    }

    public static GDSIIRecord<?> deserialize(byte[] bytes) throws IOException {
        DataInputStream dis = ByteMunging.toDataInputStream(bytes);
        int recordLength = dis.readUnsignedShort();

        RecordType recType = RecordType.forID(dis.readByte());
        GDSIITypes dataType = GDSIITypes.forCode(dis.readByte());

        byte[] recordRaw = new byte[recordLength - 4];
        int nRead = dis.read(recordRaw);
        Preconditions.checkArgument(
                recordRaw.length == nRead || (nRead == -1 && recordRaw.length == 0),
                String.format("Expected to read %d, actually read %d", recordRaw.length, nRead));

        switch (recType) {
            case ANGLE:
                return new Angle(recordRaw);
            case BGNLIB:
                return new BgnLib(recordRaw);
            case BOUNDARY:
                return new Boundary(recordRaw);
            case BGNSTR:
                return new BgnStr(recordRaw);
            case BOX:
                return new Box(recordRaw);
            case BOXTYPE:
                return new BoxType(recordRaw);
            case DATATYPE:
                return new DType(recordRaw);
            case ELFLAGS:
                return new ElFlags(recordRaw);
            case ENDEL:
                return new EndEl(recordRaw);
            case ENDLIB:
                return new EndLib(recordRaw);
            case ENDSTR:
                return new EndStr(recordRaw);
            case HEADER:
                return new Header(recordRaw);
            case LAYER:
                return new Layer(recordRaw);
            case LIBNAME:
                return new LibName(recordRaw);
            case MAG:
                return new Mag(recordRaw);
            case PATH:
                return new Path(recordRaw);
            case PATHTYPE:
                return new PathType(recordRaw);
            case PLEX:
                return new Plex(recordRaw);
            case PRESENTATION:
                return new Presentation(recordRaw);
            case PROPATTR:
                return new PropAttr(recordRaw);
            case PROPVALUE:
                return new PropValue(recordRaw);
            case SNAME:
                return new SName(recordRaw);
            case SREF:
                return new SRef(recordRaw);
            case STRANS:
                return new STrans(recordRaw);
            case STRING:
                return new StringRecord(recordRaw);
            case STRNAME:
                return new StrName(recordRaw);
            case TEXT:
                return new Text(recordRaw);
            case TEXTTYPE:
                return new TextType(recordRaw);
            case UNITS:
                return new Units(recordRaw);
            case WIDTH:
                return new Width(recordRaw);
            case XY:
                return new XY(recordRaw);
            default:
                System.err.println(
                        String.format(
                                "Length: %04d, RType: %s DType: %s",
                                recordLength, recType, dataType));
                throw new RuntimeException("Unknown Record Type!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GDSIIRecord<?> that = (GDSIIRecord<?>) o;
        return Arrays.equals(this.serialize(), that.serialize());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.serialize());
    }
}
