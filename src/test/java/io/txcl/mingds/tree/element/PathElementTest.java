package io.txcl.mingds.tree.element;

import com.google.common.collect.Lists;
import io.txcl.mingds.render.Box;
import io.txcl.mingds.render.Render;
import io.txcl.mingds.validate.RecordValidator;
import io.txcl.mingds.validate.ValidationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PathElementTest {
    private List<Vector2D> shifted(List<Vector2D> input, Vector2D shift) {
        return input.stream().map(v -> v.add(shift)).collect(Collectors.toList());
    }

    @Test
    public void testStream() throws ValidationException {
        List<Vector2D> points =
                Lists.newArrayList(new Vector2D(0, 0), new Vector2D(50, 0), new Vector2D(50, 35));

        final PathElement path = new PathElement(0, 2, 5, points);
        RecordValidator.validateAgainstRuleName(path.stream(), "pathElement");
    }

    @Test
    public void testRender() {
        Box box = new Box(new Vector2D(0, 0), new Vector2D(100, 100));
        Render render = new Render(box, 512);

        List<Vector2D> points =
                Lists.newArrayList(
                        new Vector2D(25, 25), new Vector2D(75, 25), new Vector2D(75, 35));

        // Round caps
        {
            final PathElement path = new PathElement(0, 2, 5, points);
            path.getPolygons().values().stream()
                    .flatMap(Collection::stream)
                    .forEach((poly) -> render.fillSegments(poly, Color.BLUE));
            render.strokeSegments(points, Color.RED);
        }

        // Square caps
        {
            final List<Vector2D> moved = shifted(points, new Vector2D(0, 25));
            final PathElement path = new PathElement(0, 1, 5, moved);
            path.getPolygons().values().stream()
                    .flatMap(Collection::stream)
                    .forEach((poly) -> render.fillSegments(poly, Color.BLUE));
            render.strokeSegments(moved, Color.RED);
        }

        // No caps
        {
            final List<Vector2D> moved = shifted(points, new Vector2D(0, 50));
            final PathElement path = new PathElement(0, 0, 5, moved);
            path.getPolygons().values().stream()
                    .flatMap(Collection::stream)
                    .forEach((poly) -> render.fillSegments(poly, Color.BLUE));
            render.strokeSegments(moved, Color.RED);
        }

        BufferedImage bi = render.getBi();
        Assertions.assertNotNull(bi);
    }
}
