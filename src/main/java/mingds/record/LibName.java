package mingds.record;

import com.google.common.base.Preconditions;
import mingds.format.ByteMunging;
import mingds.record.base.AsciiRecord;
import mingds.record.base.RecordType;

public class LibName extends AsciiRecord {
    public LibName(byte[] bytes) {
        this(ByteMunging.toJavaString(bytes));
    }

    public LibName(String name) {
        super(name, RecordType.LIBNAME);
        Preconditions.checkArgument(name.length() > 2);
        Preconditions.checkArgument(name.length() <= 256);
    }
}
