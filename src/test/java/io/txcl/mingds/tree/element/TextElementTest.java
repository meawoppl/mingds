package io.txcl.mingds.tree.element;

import io.txcl.mingds.validate.RecordValidator;
import io.txcl.mingds.validate.ValidationException;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.jupiter.api.Test;

public class TextElementTest {

    @Test
    public void testBasic() {
        new TextElement(0, "Foo bar baz", Vector2D.ZERO);
    }

    @Test
    public void testValidates() throws ValidationException {
        TextElement fbb = new TextElement(0, "Foo bar baz", Vector2D.ZERO);
        RecordValidator.validateAgainstRuleName(fbb.stream(), "textElement");
    }

    @Test
    public void testValidatesComplex() throws ValidationException {
        TextElement fbb = new TextElement(0, "Foo bar baz", Vector2D.ZERO);
        fbb.setMagnification(2);
        fbb.setRotation(3);

        RecordValidator.validateAgainstRuleName(fbb.stream(), "textElement");
    }
}
