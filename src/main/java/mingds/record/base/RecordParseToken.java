package mingds.record.base;

import org.antlr.v4.runtime.CommonToken;

public class RecordParseToken extends CommonToken {
    private final RecordBase<?> record;

    public RecordParseToken(int type, String text, RecordBase<?> record) {
        super(type, text);
        this.record = record;
    }

    public RecordBase<?> getRecord() {
        return record;
    }
}
