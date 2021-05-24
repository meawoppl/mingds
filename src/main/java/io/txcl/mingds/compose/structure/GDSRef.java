package io.txcl.mingds.compose.structure;

import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;

public abstract class GDSRef extends GDSElement {
    private final String refName;

    public GDSRef(GDSIIRecord<?> structureType, String refName) {
        super(structureType);
        this.refName = refName;
    }

    public String getRefName() {
        return refName;
    }

    protected abstract GDSStream getRefContents();

    @Override
    protected GDSStream getContents() {
        return ;
    }
}
