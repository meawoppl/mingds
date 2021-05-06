package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.base.AsciiRecord;
import io.txcl.mingds.record.base.RecordType;

public class LibName extends AsciiRecord {
    public static final String DEFAULT_NAME = "io.txcl.mingds.db";

    public LibName() {
        this(DEFAULT_NAME.getBytes());
    }

    public LibName(byte[] bytes) {
        super(bytes, RecordType.LIBNAME);
    }

    public LibName(String name) {
        super(name, RecordType.LIBNAME);
        Preconditions.checkArgument(name.length() > 2);
        Preconditions.checkArgument(name.length() <= 256);
    }

    public String getName() {
        return getElement(0);
    }
}
