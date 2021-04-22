package mingds.format;

import com.google.common.base.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class ByteMunging {
    private ByteMunging() {}

    public static byte[] fromJavaString(String data) {
        int outputLength = data.length() + (data.length() % 2 == 0 ? 0 : 1);
        return fromJavaString(data, outputLength);
    }

    public static byte[] fromJavaString(String data, int len) {
        Preconditions.checkArgument((len % 2) == 0, "Must result in an even number of bytes");

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        OutputStreamWriter outStreamWriter =
                new OutputStreamWriter(outStream, StandardCharsets.US_ASCII);

        data = data.substring(0, len - 1);
        byte[] result;
        try {
            outStreamWriter.write(data);
            outStreamWriter.close();

            // Pad to the end of the required length
            while (outStream.size() < len) {
                outStream.write((byte) 0x00);
            }

            result = outStream.toByteArray();
            outStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static String toJavaString(byte[] bytes) {
        return new String(bytes).trim();
    }

    public static double toDouble(byte[] bytes) throws NumberFormatException {
        Preconditions.checkArgument(bytes.length == 8);

        int sign = bytes[0] & 0x80;
        int exponent = (bytes[0] & 0x0000007F) - 64;
        long mantissa_int = 0;

        for (int i = 1; i < 8; i++) {
            mantissa_int <<= 8;
            mantissa_int |= (bytes[i] & 0x000000FF);
        }

        double mantissa_float = mantissa_int / Math.pow(2, 56);
        return (sign == 0 ? 1 : -1) * mantissa_float * Math.pow(16, (float) exponent);
    }

    public static byte[] fromDouble(double data) {
        // Shortcut the 0 case
        if (data == 0.0) {
            return new byte[8];
        }
        int exponent = 64;
        BigDecimal reg = new BigDecimal(data);
        boolean negsign = reg.doubleValue() < 0;

        if (negsign) {
            reg = reg.negate();
        }

        while ((reg.doubleValue() < 0.0625) && (exponent > 0)) {
            reg = reg.multiply(new BigDecimal("16.0"));
            exponent -= 1;
        }
        Preconditions.checkArgument(exponent != 0);

        while ((reg.doubleValue() >= 1) && (exponent < 128)) {
            reg = reg.divide(new BigDecimal("16.0"));
            exponent += 1;
        }
        Preconditions.checkArgument(exponent < 127);

        if (negsign) {
            exponent |= 0x00000080;
        }

        BigDecimal f_mantissa = reg.remainder(new BigDecimal("1.0"));

        for (int i = 0; i < 56; i++) {
            f_mantissa = f_mantissa.multiply(new BigDecimal("2.0"));
        }

        long mantissa = f_mantissa.longValue();

        byte[] result = new byte[8];
        result[0] = (byte) exponent;
        result[1] = (byte) ((mantissa >> (6 * 8)) & 0x000000FF);
        result[2] = (byte) ((mantissa >> (5 * 8)) & 0x000000FF);
        result[3] = (byte) ((mantissa >> (4 * 8)) & 0x000000FF);

        result[4] = (byte) ((mantissa >> (3 * 8)) & 0x000000FF);
        result[5] = (byte) ((mantissa >> (2 * 8)) & 0x000000FF);
        result[6] = (byte) ((mantissa >> 8) & 0x000000FF);
        result[7] = (byte) ((mantissa) & 0x000000FF);

        return result;
    }

    public static float toFloat(byte[] bytes) throws NumberFormatException {
        Preconditions.checkArgument(bytes.length == 4);

        int sign = bytes[0] & 0x80;
        int exponent = (bytes[0] & 0x0000007F) - 64;
        long mantissa_int = 0;

        for (int i = 1; i < 4; i++) {
            mantissa_int <<= 8;
            mantissa_int |= (bytes[i] & 0x000000FF);
        }

        double mantissa_float = mantissa_int / Math.pow(2, 24);
        double result = mantissa_float * Math.pow(16, (float) exponent);

        if (sign != 0) {
            result *= -1;
        }

        return (float) result;
    }

    public static byte[] fromFloat(float data) {
        Preconditions.checkArgument(Float.isFinite(data));

        if (data == 0.0) {
            return new byte[4];
        }

        int exponent = 64;
        BigDecimal reg = new BigDecimal(data);

        boolean negsign = reg.doubleValue() < 0;
        if (negsign) {
            reg = reg.negate();
        }

        for (; (reg.doubleValue() < 0.0625) && (exponent > 0); exponent--) {
            reg = reg.multiply(new BigDecimal("16.0"));
        }

        Preconditions.checkArgument(exponent != 0);

        for (; (reg.doubleValue() >= 1) && (exponent < 128); exponent++) {
            reg = reg.divide(new BigDecimal("16.0"));
        }

        Preconditions.checkArgument(exponent <= 127);

        if (negsign) {
            exponent |= 0x00000080;
        }

        BigDecimal f_mantissa = reg.remainder(new BigDecimal("1.0"));

        for (int i = 0; i < 24; i++) {
            f_mantissa = f_mantissa.multiply(new BigDecimal("2.0"));
        }

        int mantissa = f_mantissa.intValue();

        byte[] result = new byte[4];
        result[0] = (byte) exponent;
        result[1] = (byte) ((mantissa >> 16) & 0x000000FF);
        result[2] = (byte) ((mantissa >> 8) & 0x000000FF);
        result[3] = (byte) ((mantissa) & 0x000000FF);

        return result;
    }

    public static DataInputStream toDataInputStream(byte[] bytes) {
        return new DataInputStream(new ByteArrayInputStream(bytes));
    }
}
