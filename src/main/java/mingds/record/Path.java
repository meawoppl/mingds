package mingds.record;

import mingds.record.base.NoData;
import mingds.record.base.RecordType;

public class Path extends NoData {
    public Path(byte[] bytes){
        super(bytes);
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.PATH;
    }
}
