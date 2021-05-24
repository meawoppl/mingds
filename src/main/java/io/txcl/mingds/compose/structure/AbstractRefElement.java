package io.txcl.mingds.compose.structure;

import io.txcl.mingds.record.*;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;
import java.util.ArrayList;
import java.util.List;

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
        if (magnification == 1 && angle == 0) {
            return GDSStream.of(new SName(name));
        }

        List<GDSIIRecord<?>> recs = new ArrayList<>();
        recs.add(new SName(name));
        recs.add(new STrans());

        if (magnification != 1) {
            recs.add(new Mag(magnification));
        }

        if (angle != 0) {
            recs.add(new Angle(angle));
        }

        return GDSStream.of(recs);
    }
}
