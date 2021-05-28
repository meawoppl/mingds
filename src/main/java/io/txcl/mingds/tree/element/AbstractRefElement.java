package io.txcl.mingds.tree.element;

import io.txcl.mingds.geom.StransRecs;
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
        return GDSStream.of(new SName(name)).concat(StransRecs.forParameters(magnification, angle));
    }
}
