package mingds.record.base;

import java.util.HashMap;
import java.util.Map;

public enum GDSIITypes {
    NODATA(0),
    BITARRAY(1),
    INT2(2),
    INT4(3),
    REAL4(4),
    REAL8(5),
    ASCII(6);

    private final int code;

    GDSIITypes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    // Reverse-lookup map for getting a day from an abbreviation
    private static final Map<Integer, GDSIITypes> lookup = new HashMap<>();

    static {
        for (GDSIITypes d : GDSIITypes.values()) {
            lookup.put(d.getCode(), d);
        }
    }

    public static GDSIITypes forCode(int code) {
        return lookup.get(code);
    }
}
