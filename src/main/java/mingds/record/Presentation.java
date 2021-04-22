package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.BitArrayRecord;
import mingds.record.base.RecordType;

/** NOTE(meawoppl) this is a bitarray type, but only two bytes so this works... */
public class Presentation extends BitArrayRecord {
    public Presentation(byte[] bytes) {
        super(bytes, RecordType.PRESENTATION);
        Preconditions.checkArgument(bytes.length == 2);
    }
}
