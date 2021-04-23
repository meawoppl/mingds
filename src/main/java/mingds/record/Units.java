package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.DoubleRecord;
import mingds.record.base.RecordType;

public class Units extends DoubleRecord {
    public Units() {
        // These are by far the most common units found in the wild
        this(1e-3, 1e-9);
    }

    public Units(double dbToUser, double dbToMeter){
        this(new byte[16]);
        setElement(0, dbToUser);
        setElement(1, dbToMeter);
    }

    public Units(byte[] bytes) {
        super(bytes, RecordType.UNITS);
        Preconditions.checkArgument(nElements() == 2);
    }

    public double getDatabaseToUserUnit() { return getElement(0); }
    public double getDatabaseUnitInMeters() {
        return getElement(1);
    }
}
