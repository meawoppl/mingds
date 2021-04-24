package io.txcl.mingds.record;

import io.txcl.mingds.record.base.IntRecord;
import io.txcl.mingds.record.base.RecordType;

public class Plex extends IntRecord {
    public Plex(byte[] bytes) {
        super(bytes, RecordType.PLEX);
        // NOTE(meawoppl) the notes from:
        // https://boolean.klaasholwerda.nl/interface/bnf/gdsformat.html#rec_plex
        // are a bit unclear as to the size/representation of this element...
    }
}
