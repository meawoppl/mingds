package mingds;

import com.google.common.base.Preconditions;
import mingds.record.Angle;
import mingds.record.BgnLib;
import mingds.record.BgnStr;
import mingds.record.Boundary;
import mingds.record.DType;
import mingds.record.EndEl;
import mingds.record.EndLib;
import mingds.record.EndStr;
import mingds.record.Header;
import mingds.record.Layer;
import mingds.record.LibName;
import mingds.record.Mag;
import mingds.record.Path;
import mingds.record.PathType;
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

import mingds.record.Unknown;
import mingds.record.Width;
import mingds.record.XY;
import mingds.record.base.GDSIITypes;
import mingds.record.base.RecordBase;
import mingds.record.base.RecordType;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Iterator;

public class GDSIIIterator implements Iterator<RecordBase<?>> {
    DataInputStream dis;
    public GDSIIIterator(DataInputStream dis){
        this.dis = dis;
    }

    public RecordBase<?> next() {
        try{
            return nextExc();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public RecordBase<?> nextExc() throws IOException {
        int recordLength = dis.readUnsignedShort();
        RecordType recType = RecordType.forID(dis.readByte());
        GDSIITypes dataType = GDSIITypes.forCode(dis.readByte());

        byte[] recordRaw = new byte[recordLength-4];
        int nRead = dis.read(recordRaw);
        Preconditions.checkArgument(nRead == recordRaw.length);

        if(RecordType.ENDLIB == recType){
            while(dis.available() > 0){
                Preconditions.checkArgument(dis.read() == 0, "Unexpected padding value after ENDLIB");
            }
        }

        switch (recType){
            case ANGLE:
                return new Angle(recordRaw);
            case BGNLIB:
                return new BgnLib(recordRaw);
            case BOUNDARY:
                return new Boundary(recordRaw);
            case BGNSTR:
                return new BgnStr(recordRaw);
            case DATATYPE:
                return new DType(recordRaw);
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
                System.out.println(String.format("Length: %04d, RType: %s DType: %s", recordLength, recType, dataType));
                throw new RuntimeException("?");
                // return new Unknown(recordRaw, recType.getCode());
                // throw new RuntimeException("Not yet implemented");
        }


    }

    @Override
    public boolean hasNext() {
        try {
            return dis.available() != 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
