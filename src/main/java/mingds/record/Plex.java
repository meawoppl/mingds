package mingds.record;

import mingds.record.base.RecordType;
import mingds.record.base.ShortRecord;

public class Plex extends ShortRecord {
    public Plex(byte[] bytes) {
        super(bytes, RecordType.PLEX);
        // NOTE(meawoppl) the notes from:
        // https://boolean.klaasholwerda.nl/interface/bnf/gdsformat.html#rec_plex
        // are a bit unclear as to the size/representation of this element...
    }
}
