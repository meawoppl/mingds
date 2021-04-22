package mingds.format;

import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ByteMungingTest {
    public Stream<Double> getTestDoubles() {
        Random r = new Random(3);
        Stream<Double> norm =
                DoubleStream.generate(r::nextDouble).map(d -> (d * 200) - 100).boxed().limit(1000);
        Stream<Double> exp =
                DoubleStream.generate(r::nextDouble)
                        .map(d -> Math.pow(10 * d, r.nextInt(100) - 50))
                        .boxed()
                        .limit(1000);
        return Streams.concat(norm, exp);
    }

    @Test
    public void testStringEncoding() {
        Assertions.assertArrayEquals(
                new byte[] {102, 111, 111, 0}, ByteMunging.fromJavaString("foo", 4));
    }

    @Test
    public void testRoundTrip() {
        Assertions.assertEquals(
                "bar", ByteMunging.toJavaString(ByteMunging.fromJavaString("bar", 4)));
    }

    @Test
    public void testDoubleRoundTrip() {
        double val = 1024 * 2;
        byte[] bytes = ByteMunging.fromDouble(val);
        Assertions.assertEquals(val, ByteMunging.toDouble(bytes));
    }

    @Test
    public void testDoubleRoundTripHarder() {
        getTestDoubles()
                .forEach(
                        val -> {
                            byte[] bytes = ByteMunging.fromDouble(val);
                            Assertions.assertEquals(val, ByteMunging.toDouble(bytes));
                        });
    }

    @Test
    public void testDoubleRoundTripShortcut() {
        double val = 0;
        byte[] bytes = ByteMunging.fromDouble(val);
        Assertions.assertEquals(val, ByteMunging.toDouble(bytes));
    }

    @Test
    public void testFloatRoundTrip() {
        float val = 1024 * 2;
        byte[] bytes = ByteMunging.fromFloat(val);
        System.out.println(Arrays.toString(bytes));
        Assertions.assertEquals(val, ByteMunging.toFloat(bytes));
    }

    @Test
    public void testFloatRoundTripHarder() {
        // Some of the doubles are OOB when downcast, so filter them
        getTestDoubles()
                .map(Double::floatValue)
                .filter(Float::isFinite)
                .forEach(
                        f -> {
                            byte[] bytes = ByteMunging.fromFloat(f);
                            Assertions.assertEquals(bytes.length, 4);
                            float actual = ByteMunging.toFloat(bytes);
                            float diff = Math.abs(f - actual);

                            if (diff != 0) {
                                double errPercentage = diff / actual;
                                String explanation =
                                        String.format(
                                                "Expected: %f, Actual: %f, Diff: %f",
                                                f, actual, diff);
                                Assertions.assertTrue(errPercentage < 0.001, explanation);
                            }
                        });
    }
}
