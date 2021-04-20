package mingds.stream;

import mingds.record.base.RecordBase;
import mingds.record.base.RecordType;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GDSStreamStats {
    public static Map<RecordType, Integer> captureStreamStats(Stream<RecordBase<?>> stream){
        Map<RecordType, Integer> ret = new HashMap<>();

        stream.forEach(rec->{
            RecordType t = rec.getRecordType();
            ret.put(t, ret.getOrDefault(t, 0) + 1);
        });

        return ret;
    }
}
