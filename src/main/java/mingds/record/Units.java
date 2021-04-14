package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.DoubleRecord;
import mingds.record.base.RecordType;


public class Units extends DoubleRecord {
    public Units(byte[] bytes){
        super(bytes);
        Preconditions.checkArgument(nElements()==2);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.UNITS;
    }

    public double getDatabaseToUserUnit(){
        return getElement(0);
    }

    public double getDatabaseUnitInMeters(){
        return getElement(1);
    }

}
