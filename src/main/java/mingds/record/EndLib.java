package mingds.record;

import mingds.record.base.NoData;
import mingds.record.base.RecordType;

public class EndLib extends NoData {
    public EndLib(byte[] bytes) {
        super(bytes, RecordType.ENDLIB);
    }
}
