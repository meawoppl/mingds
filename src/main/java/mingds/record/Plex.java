package mingds.record;

import com.google.common.base.Preconditions;
import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class Plex extends ShortRecord {
    public Plex(byte[] bytes){
        super(bytes);
        // NOTE(meawoppl) the notes from:
        // https://boolean.klaasholwerda.nl/interface/bnf/gdsformat.html#rec_plex
        // are a bit unclear as to the size/representation of this element...
    }

    @Override
    public RecordType getRecordType() {
        return RecordType.PLEX;
    }
}
