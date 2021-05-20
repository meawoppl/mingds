package io.txcl.mingds.compose.structure;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.Lists;
import io.txcl.mingds.render.Render;
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
    public void testNormalTo() {
        final Vector2D a = new Vector2D(0, 0);
        final Vector2D b = new Vector2D(50, 0);

        Assertions.assertEquals(GDSPath.normalTo(a, b), new Vector2D(0, 1));
    }

    @Test
    public void testRender() {
        List<Vector2D> points =
                Lists.newArrayList(new Vector2D(0, 0), new Vector2D(50, 0), new Vector2D(50, 35));

        final GDSPath path = new GDSPath(0, 0, 20, points);
        final Render render = Render.forElement(path, 256);

        final BufferedImage bi = render.getBi();
        Assertions.assertNotNull(bi);
    }
}
