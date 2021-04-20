package mingds.record.base;

import com.google.common.base.Preconditions;
import mingds.GdsiiParser;
import org.antlr.v4.runtime.CommonToken;

import java.util.HashMap;
import java.util.Map;

public enum RecordType {
    NULL(-0x01),

    HEADER(0x00),
    BGNLIB(0x01),
    LIBNAME(0x02),
    UNITS(0x03),
    ENDLIB(0x04),
    BGNSTR(0x05),
    STRNAME(0x06),
    ENDSTR(0x07),

    BOUNDARY(0x08),
    PATH(0x09),
    SREF(0x0A),
    AREF(0x0B),
    TEXT(0x0C),
    LAYER(0x0D),
    DATATYPE(0x0E),
    WIDTH(0x0F),

    XY(0x10),
    ENDEL(0x11),
    SNAME(0x12),
    COLROW(0x13),
    TEXTNODE(0x14),
    NODE(0x15),
    TEXTTYPE(0x16),
    PRESENTATION(0x17),

    SPACING(0x18),
    STRING(0x19),
    STRANS(0x1A),
    MAG(0x1B),
    ANGLE(0x1C),
    UINTEGER(0x1D),
    USTRING(0x1E),
    REFLIBS(0x1F),

    FONTS(0x20),
    PATHTYPE(0x21),
    GENERATIONS(0x22),
    ATTRTABLE(0x23),
    STYPTABLE(0x24),
    STRTYPE(0x25),
    ELFLAGS(0x26),
    ELKEY(0x27),

    LINKTYPE(0x28),
    LINKKEYS(0x29),
    NODETYPE(0x2A),
    PROPATTR(0x2B),
    PROPVALUE(0x2C),
    BOX(0x2D),
    BOXTYPE(0x2E),
    PLEX(0x2F),

    BGNEXTN(0x30),
    ENDEXTN(0x31),
    TAPENUM(0x32),
    TAPECODE(0x33),
    STRCLASS(0x34),
    RESERVED(0x35),
    FORMAT(0x36),
    MASK(0x37),

    ENDMASKS(0x38),
    LIBDIRSIZE(0x39),
    SRFNAME(0x3A),
    LIBSECUR(0x3B),
    BORDER(0x3C),
    SOFTFENCE(0x3D),
    HARDFENCE(0x3E),
    SOFTWIRE(0x3F),

    HARDWIRE(0x40),
    PATHPORT(0x41),
    NODEPORT(0x42),
    USERCONSTRAINT(0x43),
    SPACER_ERROR(0x44),
    CONTACT(0x45);

    private final int code;
    RecordType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    // Reverse-lookup map for getting a day from an abbreviation
    private static final Map<Integer, RecordType> lookupByCode = new HashMap<>();
    private static final Map<String, RecordType> lookupByName = new HashMap<>();
    private static final Map<RecordType, Integer> recordToParseToTokenID = new HashMap<>();

    static {
        for (RecordType d : RecordType.values()) {
            lookupByCode.put(d.getCode(), d);
            lookupByName.put(d.name(), d);
        }

        for (int i = 1; i <= GdsiiParser.VOCABULARY.getMaxTokenType(); i++) {
            String name = GdsiiParser.VOCABULARY.getLiteralName(i);
            name = name.replace("'", "");
            RecordType rt = RecordType.forName(name);
            recordToParseToTokenID.put(rt, i);
        }
    }

    public static RecordType forID(int id){
        Preconditions.checkArgument(lookupByCode.containsKey(id), "No RecordType for id:" + id);
        return lookupByCode.get(id);
    }
    public static RecordType forName(String name) {
        Preconditions.checkArgument(lookupByName.containsKey(name), "No key for: '" + name + "'");
        return  lookupByName.get(name);
    }

    public CommonToken getParseToken() {
        Preconditions.checkArgument(recordToParseToTokenID.containsKey(this), "Missing TokenID for %s", this);
        int tokenID = recordToParseToTokenID.get(this);
        return new CommonToken(tokenID, this.name());

    }
}
