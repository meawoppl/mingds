package io.txcl.mingds.compose.structure;

import static org.junit.jupiter.api.Assertions.*;

import io.txcl.mingds.render.Box;
import io.txcl.mingds.render.Render;
import io.txcl.mingds.validate.RecordValidator;
import io.txcl.mingds.validate.ValidationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SRefElementTest {
    @Test
    public void testInit() throws ValidationException {
        final SRefElement foo = new SRefElement("foo", new Vector2D(20, 20));
    }

    @Test
    public void testStream() throws ValidationException {
        final SRefElement foo = new SRefElement("foo", new Vector2D(20, 20));
        RecordValidator.validateAgainstRuleName(foo.stream(), "srefElement");
    }

    @Test
    public void testBranches() throws ValidationException {
        final SRefElement foo = new SRefElement("foo", new Vector2D(20, 20), 3, 180);

        RecordValidator.validateAgainstRuleName(foo.stream(), "srefElement");
    }

    @Test
    public void testRender() {
        List<Vector2D> pts = List.of(new Vector2D(0, 0), new Vector2D(0, 5));
        PathElement pe = new PathElement(0, 2, 4, pts);

        Structure s = new Structure("foobarsalot");
        s.addElement(pe);

        final SRefElement base = SRefElement.toStructure(s);
        SRefElement translated = base.translated(new Vector2D(5, 0));
        SRefElement rotated = base.rotated(180);
        SRefElement scaled = base.scaled(1.2);

        Box box = new Box(new Vector2D(-10, -10), new Vector2D(10, 10));
        final Render render = new Render(box, 512);

        scaled.getPolygons().forEach(poly -> render.fillSegments(poly, Color.GREEN));
        pe.getPolygons().forEach(poly -> render.fillSegments(poly, Color.CYAN));
        translated.getPolygons().forEach(poly -> render.fillSegments(poly, Color.BLUE));
        rotated.getPolygons().forEach(poly -> render.fillSegments(poly, Color.BLUE));

        final BufferedImage bi = render.getBi();
        Assertions.assertNotNull(bi);
    }
}
