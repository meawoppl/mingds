package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class BoxType extends ShortRecord {
    public BoxType(byte[] bytes) {
        super(bytes, RecordType.BOXTYPE);
        Preconditions.checkArgument(nElements() == 1);
    }
}
