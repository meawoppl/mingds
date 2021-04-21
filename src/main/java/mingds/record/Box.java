package mingds.record;

import mingds.record.base.NoData;
import mingds.record.base.RecordType;

public class Box extends NoData {
    public Box(byte[] bytes) {
        super(bytes, RecordType.BOX);
    }
}
