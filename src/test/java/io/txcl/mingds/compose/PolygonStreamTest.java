package io.txcl.mingds.compose;

import com.google.common.collect.Lists;
import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.validate.RecordValidator;
import io.txcl.mingds.validate.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.jupiter.api.Test;

public class PolygonStreamTest {
    @Test
    public void testEmpty() throws ValidationException {
        List<GDSIIRecord<?>> records =
                PolygonStream.ofPolygons(0, Stream.empty()).collect(Collectors.toList());
        new RecordValidator().validate(GDSStream.of(records));
    }

    @Test
    public void testOfPolygons() throws ValidationException, IOException {
        // Make a checkerboard
        List<List<Vector2D>> squares = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    continue;
                }
                squares.add(
                        Lists.newArrayList(
                                        new Vector2D(i, j),
                                        new Vector2D(i + 1, j),
                                        new Vector2D(i + 1, j + 1),
                                        new Vector2D(i, j + 1),
                                        new Vector2D(i, j))
                                .stream()
                                .map(v -> v.scalarMultiply(100))
                                .collect(Collectors.toList()));
            }
        }

        List<GDSIIRecord<?>> records =
                PolygonStream.ofPolygons(0, squares.stream()).collect(Collectors.toList());
        new RecordValidator().validate(GDSStream.of(records));
        // GDSIIStream.to(Path.of("tempout", "checkerboard.gds"), records.stream());
    }

    @Test
    public void testPolyPinwheel() throws ValidationException, IOException {
        // Make a Pinwheel (make sure convex polygons work...)
        List<Vector2D> lumpogon =
                IntStream.range(0, 22)
                        .mapToObj(
                                i -> {
                                    if (i % 3 == 0) {
                                        return Vector2D.ZERO;
                                    }
                                    double theta = Math.PI * 2 * i / 20.0;
                                    return new Vector2D(50 * Math.cos(theta), 50 * Math.sin(theta));
                                })
                        .collect(Collectors.toList());

        List<GDSIIRecord<?>> records =
                PolygonStream.ofPolygons(3, Stream.of(lumpogon)).collect(Collectors.toList());
        new RecordValidator().validate(GDSStream.of(records));
        // GDSIIStream.to(Path.of("tempout", "lump.gds"), records.stream());
    }
}
