package mingds.stream;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import mingds.record.base.GDSIIRecord;
import mingds.record.base.RecordType;

public class GDSStreamStats {
    public static Map<RecordType, Integer> captureStreamStats(Stream<GDSIIRecord<?>> stream) {
        Map<RecordType, Integer> ret = new HashMap<>();

        stream.forEach(
                rec -> {
                    RecordType t = rec.getRecordType();
                    ret.put(t, ret.getOrDefault(t, 0) + 1);
                });

        return ret;
    }
}
