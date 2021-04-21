package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

/** NOTE(meawoppl) this is a bitarray type, but only two bytes so this works... */
public class Presentation extends ShortRecord {

    public Presentation(byte[] bytes) {
        super(bytes, RecordType.PRESENTATION);
        Preconditions.checkArgument(nElements() == 1);
    }
}
