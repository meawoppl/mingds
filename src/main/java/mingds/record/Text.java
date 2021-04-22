package mingds.record;

import mingds.record.base.NoData;
import mingds.record.base.RecordType;

public class Text extends NoData {
    public Text() {
        this(new byte[0]);
    }

    public Text(byte[] bytes) {
        super(bytes, RecordType.TEXT);
    }
}
