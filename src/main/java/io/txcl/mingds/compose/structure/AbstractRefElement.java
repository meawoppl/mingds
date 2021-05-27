package io.txcl.mingds.compose.structure;

import io.txcl.mingds.compose.StransHelp;
import io.txcl.mingds.record.*;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;

public abstract class AbstractRefElement extends AbstractElement {
    private final String name;

    // strans attributes
    private final double magnification;
    private final double angle;

    public AbstractRefElement(GDSIIRecord<?> structureType, String name) {
        super(structureType);
        this.name = name;
        magnification = 1;
        angle = 0;
    }

    public AbstractRefElement(
            GDSIIRecord<?> structureType, String name, double magnitude, double rotation) {
        super(structureType);
        this.name = name;
        this.magnification = magnitude;
        this.angle = rotation;
    }

    public String getName() {
        return name;
    }

    public double getMagnification() {
        return magnification;
    }

    public double getAngle() {
        return angle;
    }

    public GDSStream getRefComponents() {
        StransHelp strans = new StransHelp(magnification, angle);
        return GDSStream.of(new SName(name)).concat(strans.stream());
    }
}
