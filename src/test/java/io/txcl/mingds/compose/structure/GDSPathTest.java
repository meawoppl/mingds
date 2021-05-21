package io.txcl.mingds.compose.structure;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Lists;
import io.txcl.mingds.render.Box;
import io.txcl.mingds.render.Render;
import io.txcl.mingds.validate.RecordValidator;
import io.txcl.mingds.validate.ValidationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GDSPathTest {
    private List<Vector2D> shifted(List<Vector2D> input, Vector2D shift) {
        return input.stream().map(v -> v.add(shift)).collect(Collectors.toList());
    }

    @Test
    public void testStream() throws ValidationException {
        List<Vector2D> points =
                Lists.newArrayList(new Vector2D(0, 0), new Vector2D(50, 0), new Vector2D(50, 35));

        final GDSPath path = new GDSPath(0, 2, 5, points);
        RecordValidator.validateAgainstRuleName(path.stream(), "pathElement");
    }

    @Test
    public void testRender() {
        Box box = new Box(new Vector2D(0, 0), new Vector2D(100, 100));
        Render render = new Render(box, 512);

        List<Vector2D> points =
                Lists.newArrayList(new Vector2D(25, 25), new Vector2D(75, 25), new Vector2D(75, 35));

        // Round caps
        {
            final GDSPath path = new GDSPath(0, 2, 5, points);
            path.render((poly) -> render.fillSegments(poly, Color.BLUE));
            render.strokeSegments(points, Color.RED);
        }

        // Square caps
        {
            final List<Vector2D> moved = shifted(points, new Vector2D(0, 25));
            final GDSPath path = new GDSPath(0, 1, 5, moved);
            path.render((poly) -> render.fillSegments(poly, Color.BLUE));
            render.strokeSegments(moved, Color.RED);
        }

        // No caps
        {
            final List<Vector2D> moved = shifted(points, new Vector2D(0, 50));
            final GDSPath path = new GDSPath(0, 0, 5, moved);
            path.render((poly) -> render.fillSegments(poly, Color.BLUE));
            render.strokeSegments(moved ,Color.RED);
        }

        BufferedImage bi = render.getBi();
        Assertions.assertNotNull(bi);
    }
}
