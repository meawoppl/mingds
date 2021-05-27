package io.txcl.mingds.record.base;

import com.google.common.base.Preconditions;
import io.txcl.mingds.format.ByteMunging;
import io.txcl.mingds.record.Angle;
import io.txcl.mingds.record.BgnLib;
import io.txcl.mingds.record.BgnStr;
import io.txcl.mingds.record.Boundary;
import io.txcl.mingds.record.Box;
import io.txcl.mingds.record.BoxType;
import io.txcl.mingds.record.ColRow;
import io.txcl.mingds.record.DType;
import io.txcl.mingds.record.ElFlags;
import io.txcl.mingds.record.EndEl;
import io.txcl.mingds.record.EndLib;
import io.txcl.mingds.record.EndStr;
import io.txcl.mingds.record.Header;
import io.txcl.mingds.record.Layer;
import io.txcl.mingds.record.LibName;
import io.txcl.mingds.record.Mag;
import io.txcl.mingds.record.Path;
import io.txcl.mingds.record.PathType;
import io.txcl.mingds.record.Plex;
import io.txcl.mingds.record.Presentation;
import io.txcl.mingds.record.PropAttr;
import io.txcl.mingds.record.PropValue;
import io.txcl.mingds.record.SName;
import io.txcl.mingds.record.SRef;
import io.txcl.mingds.record.STrans;
import io.txcl.mingds.record.StrName;
import io.txcl.mingds.record.StringRecord;
import io.txcl.mingds.record.Text;
import io.txcl.mingds.record.TextType;
import io.txcl.mingds.record.Units;
import io.txcl.mingds.record.Width;
import io.txcl.mingds.record.XY;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
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

    public int getRecordSize() {
        return bytes.length + 4;
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
            case COLROW:
                return new ColRow(recordRaw);
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
