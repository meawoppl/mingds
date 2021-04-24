package io.txcl.mingds.stream;

import io.txcl.mingds.GdsiiParser;
import io.txcl.mingds.record.base.GDSIIRecord;
import java.util.List;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ListTokenSource;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class GDSIIValidator {
    public static class ValidationException extends Throwable {
        public ValidationException(Throwable e) {
            super(e);
        }
    }

    public static void validateRecords(List<GDSIIRecord<?>> records) throws ValidationException {
        ListTokenSource lts = new ListTokenSource(records);
        CommonTokenStream stream = new CommonTokenStream(lts);
        GdsiiParser parser = new GdsiiParser(stream);

        parser.addErrorListener(ThrowingErrorListener.INSTANCE);
        try {
            GdsiiParser.StreamContext sc = parser.stream();
        } catch (ParseCancellationException e) {
            throw new ValidationException(e);
        }
    }
}
