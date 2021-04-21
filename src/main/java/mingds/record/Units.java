package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.DoubleRecord;
import mingds.record.base.RecordType;

public class Units extends DoubleRecord {
    public Units(byte[] bytes) {
        super(bytes, RecordType.UNITS);
        Preconditions.checkArgument(nElements() == 2);
    }

    public double getDatabaseToUserUnit() {
        return getElement(0);
    }

    public double getDatabaseUnitInMeters() {
        return getElement(1);
    }
}
