package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.DoubleRecord;
import mingds.record.base.RecordType;

public class Mag extends DoubleRecord {
    public Mag(byte[] bytes){
        super(bytes);
        Preconditions.checkArgument(nElements() <= 1, "Expected 1 or less elements, got %d", getElementSize());
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.MAG;
    }

    public double getMagnification() {
        if(getElementSize() == 1){
            return getElement(0);
        }

        return 1;
    }
}
