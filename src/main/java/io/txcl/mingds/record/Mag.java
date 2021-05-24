package io.txcl.mingds.record;

import com.google.common.base.Preconditions;
import io.txcl.mingds.record.base.DoubleRecord;
import io.txcl.mingds.record.base.RecordType;

public class Mag extends DoubleRecord {

    public Mag() {
        this(new byte[8]);
        setElement(0, 1.0);
    }

    public Mag(double magnification) {
        this(new byte[8]);
        setElement(0, magnification);
    }

    public Mag(byte[] bytes) {
        super(bytes, RecordType.MAG);
        Preconditions.checkArgument(
                nElements() <= 1, "Expected 1 or less elements, got %d", getElementSize());
    }

    public double getMagnification() {
        if (getElementSize() == 1) {
            return getElement(0);
        }

        return 1;
    }
}
