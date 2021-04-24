package io.txcl.mingds.record.base;

import java.util.Arrays;
import io.txcl.mingds.format.ByteMunging;

public abstract class DoubleRecord extends GDSIIRecord<Double> {
    public DoubleRecord(byte[] bytes, RecordType rt) {
        super(rt);
        setBytes(bytes);
    }

    @Override
    public GDSIITypes getDataType() {
        return GDSIITypes.REAL8;
    }

    @Override
    public Double getElement(int i) {
        return ByteMunging.toDouble(Arrays.copyOfRange(getBytes(), i * 8, i * 8 + 8));
    }

    @Override
    public void setElement(int i, Double v) {
        byte[] encoded = ByteMunging.fromDouble(v);

        for (int j = 0; j < encoded.length; j++) {
            setByte(i * 8 + j, encoded[j]);
        }
    }
}
