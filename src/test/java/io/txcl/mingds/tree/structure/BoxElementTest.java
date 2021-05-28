package io.txcl.mingds.tree.structure;

import io.txcl.mingds.render.Box;
import io.txcl.mingds.validate.RecordValidator;
import io.txcl.mingds.validate.ValidationException;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoxElementTest {
    @Test
    public void testStream() throws ValidationException {
        Box box = new Box(new Vector2D(-1, -1), new Vector2D(1, 1));
        BoxElement boxElement = new BoxElement(box, 2);
        RecordValidator.validateAgainstRuleName(boxElement.stream(), "boxElement");
    }

    @Test
    public void testStreamExtents() {
        Box box = new Box(new Vector2D(-1, -1), new Vector2D(1, 1));
        BoxElement boxElement = new BoxElement(box, 2);
        Box extents = boxElement.getExtents();

        Assertions.assertEquals(box, extents);
    }
}
