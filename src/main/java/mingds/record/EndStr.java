package mingds.record;

import mingds.record.base.NoData;
import mingds.record.base.RecordType;

public class EndStr extends NoData {
    public EndStr(byte[] bytes){
        super(bytes);
    }


    @Override
    public RecordType getRecordType() {
        return RecordType.ENDSTR;
    }
}
