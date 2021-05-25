package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.record.base.ShortRecord;

public class BoxType extends ShortRecord {
    public BoxType() {
        this(new byte[2]);
    }

    public BoxType(byte[] bytes) {
        super(bytes, RecordType.BOXTYPE);
        Preconditions.checkArgument(nElements() == 1);
    }

    public BoxType(int boxType) {
        this();
        setBoxType(boxType);
    }

    public int getBoxType() {
        return getElement(0);
    }

    public void setBoxType(int value) {
        Preconditions.checkArgument(value >= 0);
        Preconditions.checkArgument(value <= 255);
        setElement(0, (short) value);
    }
}
