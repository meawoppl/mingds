package mingds.parse;

import mingds.record.base.GDSIIRecord;
import org.antlr.v4.runtime.CommonToken;

public class RecordParseToken extends CommonToken {
    private final GDSIIRecord<?> record;

    public RecordParseToken(int type, String text, GDSIIRecord<?> record) {
        super(type, text);
        this.record = record;
    }

    public GDSIIRecord<?> getRecord() {
        return record;
    }
}
