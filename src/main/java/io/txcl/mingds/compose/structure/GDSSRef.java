package io.txcl.mingds.compose.structure;

import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;

public class GDSSRef extends GDSRef {

    public GDSSRef(GDSIIRecord<?> structureType, String refName) {
        super(structureType, refName);
    }

    @Override
    protected GDSStream getContents() {
        return null;
    }
}
