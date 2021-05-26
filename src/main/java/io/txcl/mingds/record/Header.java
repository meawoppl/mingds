package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.format.ByteMunging;
import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.record.base.ShortRecord;

public class Header extends ShortRecord {
    public Header() {
        this(new byte[2]);

        // Most modern GDS files report version 2 (GDSII...)
        setVersion(2);
    }

    public Header(byte[] bytes) {
        super(bytes, RecordType.HEADER);
        Preconditions.checkArgument(bytes.length == 2);
    }

    public int getVersion() {
        return getElement(0);
    }

    public void setVersion(int version) {
        setElement(0, ByteMunging.requireShort(version));
    }
}
