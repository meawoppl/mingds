package mingds.record.base;

import java.util.Arrays;
import mingds.format.ByteMunging;

public abstract class DoubleRecord extends RecordBase<Double> {
    public DoubleRecord(byte[] bytes) {
        setBytes(bytes);
    }

    @Override
    public int getElementSize() {
        return 8;
    }

    @Override
    public Double getElement(int i) {
        return ByteMunging.toDouble(Arrays.copyOfRange(getBytes(), i, i + 8));
    }

    @Override
    public void setElement(int i, Double v) {
        byte[] encoded = ByteMunging.fromDouble(v);

        for (int j = 0; j < encoded.length; j++) {
            setByte(i * 8 + j, encoded[j]);
        }
    }
}
