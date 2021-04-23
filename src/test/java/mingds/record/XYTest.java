package mingds.record;

import com.google.common.collect.Lists;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class XYTest  {
    @Test
    public void testGetXY() {
        ArrayList<Vector2D> vs = Lists.newArrayList(new Vector2D(0, 0), new Vector2D(0, 1), new Vector2D(0, 0));
        XY xy = new XY(vs);

        for (int i = 0; i < vs.size(); i++) {
            Assertions.assertEquals(vs.get(i), xy.getXY(i));
        }

    }

    public void testGetXYs() {
    }
}
