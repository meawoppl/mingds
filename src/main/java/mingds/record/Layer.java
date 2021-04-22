package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class Layer extends ShortRecord {
    public Layer() {
        this(new byte[2]);
    }

    public Layer(byte[] bytes) {
        super(bytes, RecordType.LAYER);
        Preconditions.checkArgument(bytes.length == 2);
    }
}
