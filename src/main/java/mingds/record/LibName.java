package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.AsciiRecord;
import mingds.record.base.RecordType;

public class LibName extends AsciiRecord {
    public LibName(byte[] bytes) {
        super(bytes, RecordType.LIBNAME);
    }

    public LibName(String name) {
        super(name, RecordType.LIBNAME);
        Preconditions.checkArgument(name.length() > 2);
        Preconditions.checkArgument(name.length() <= 256);
    }
}
