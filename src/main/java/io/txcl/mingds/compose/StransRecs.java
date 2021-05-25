package io.txcl.mingds.compose;

import io.txcl.mingds.record.Angle;
import io.txcl.mingds.record.Mag;
import io.txcl.mingds.record.SName;
import io.txcl.mingds.record.STrans;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;

import java.util.ArrayList;
import java.util.List;

public class StransRecs {
    public static GDSStream forParameters(double magnification, double angle){
        if(magnification == 1 && angle == 0){
            return GDSStream.empty();
        }

        List<GDSIIRecord<?>> recs = new ArrayList<>();
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
