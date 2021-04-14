package mingds.format;

import com.google.common.base.Preconditions;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class Unpack {
    public static short[] shorts(byte[] bytes){
        Preconditions.checkArgument(bytes.length %2 == 0);
        int len = bytes.length / 2;
        ShortBuffer sb = ByteBuffer.wrap(bytes).asShortBuffer();
        short[] shorts = new short[len];
        for (int i = 0; i < len; i++) {
            shorts[i] = sb.get(i);
        }
        return shorts;
    }
}
