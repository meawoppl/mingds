package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.IntRecord;
import mingds.record.base.RecordType;

public class Width extends IntRecord {
    public Width(byte[] bytes) {
        super(bytes, RecordType.WIDTH);
        Preconditions.checkArgument(bytes.length == 4);
    }

    public int getWidth() {
        return getElement(1);
    }
}
