package mingds.format;

import com.google.common.collect.Streams;
import junit.framework.TestCase;
import mingds.format.ByteMunging;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;


public class ByteMungingTest extends TestCase {
    public Stream<Double> getTestDoubles() {
        Random r = new Random(3);
        Stream<Double> norm = DoubleStream.generate(r::nextDouble).map(d -> (d * 200) - 100).boxed().limit(1000);
        Stream<Double> exp = DoubleStream.generate(r::nextDouble).map(d -> Math.pow(10 * d, r.nextInt(100) - 50)).boxed().limit(1000);
        return Streams.concat(norm, exp);
    }

    public void testStringEncoding() {
        assertArrayEquals(new byte[] {102, 111, 111, 0}, ByteMunging.fromJavaString("foo", 4));
    }

    public void testRoundTrip() {
        assertEquals("bar", ByteMunging.toJavaString(ByteMunging.fromJavaString("bar", 4)));
    }

    public void testDoubleRoundTrip() {
        double val = 1024*2;
        byte[] bytes = ByteMunging.fromDouble(val);
        assertEquals(val, ByteMunging.toDouble(bytes));
    }

    public void testDoubleRoundTripHarder() {
        getTestDoubles().forEach(val->{
            byte[] bytes = ByteMunging.fromDouble(val);
            assertEquals(val, ByteMunging.toDouble(bytes));
        });
    }

    public void testDoubleRoundTripShortcut() {
        double val = 0;
        byte[] bytes = ByteMunging.fromDouble(val);
        assertEquals(val, ByteMunging.toDouble(bytes));
    }

    public void testFloatRoundTrip() {
        float val = 1024*2;
        byte[] bytes = ByteMunging.fromFloat(val);
        System.out.println(Arrays.toString(bytes));
        assertEquals(val, ByteMunging.toFloat(bytes));
    }

    public void testFloatRoundTripHader() {
        // Some of the doubles are OOB when downcast, so filter them
        getTestDoubles().map(Double::floatValue).filter(Float::isFinite).forEach(f->{
            byte[] bytes = ByteMunging.fromFloat(f);
            assertEquals(bytes.length, 4);
            float actual = ByteMunging.toFloat(bytes);
            float diff = Math.abs(f - actual);

            if (diff!=0) {
                double errPercentage = diff / actual;
                String explanation = String.format("Expected: %f, Actual: %f, Diff: %f", f, actual, diff);
                assertTrue(explanation, errPercentage < 0.001);
            }
        });
    }

}
