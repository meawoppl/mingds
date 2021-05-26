package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.format.ByteMunging;
import io.txcl.mingds.record.base.RecordType;
import io.txcl.mingds.record.base.ShortRecord;

public class DType extends ShortRecord {
    public DType() {
        this(new byte[2]);
    }

    public DType(byte[] bytes) {
        super(bytes, RecordType.DATATYPE);
        Preconditions.checkArgument(bytes.length == 2);
    }

    public static DType of(int value) {
        final DType dType = new DType();
        dType.setDataType(value);
        return dType;
    }

    public int getDType() {
        return getElement(0);
    }

    public void setDataType(int value) {
        Preconditions.checkArgument(value >= 0);
        Preconditions.checkArgument(value <= 255);
        setElement(0, ByteMunging.requireShort(value));
    }
}
