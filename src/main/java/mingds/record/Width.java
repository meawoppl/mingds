package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.IntRecord;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class Width extends IntRecord {
    public Width(byte[] bytes){
        super(bytes);
        Preconditions.checkArgument(bytes.length==4);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.WIDTH;
    }

    public int getWidth(){
        return getElement(1);
    }
}
