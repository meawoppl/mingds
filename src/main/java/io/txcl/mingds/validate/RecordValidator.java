package io.txcl.mingds.validate;

import io.txcl.mingds.GdsiiParser;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.validate.antlr.GDSTokenSource;
import io.txcl.mingds.validate.antlr.ThrowingErrorListener;
import java.io.IOException;
import java.nio.file.Path;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class RecordValidator extends ValidatorBase {
    public RecordValidator() {}

    @Override
    public void validate(Path path) throws ValidationException {
        GDSStream s;
        try {
            s = GDSStream.from(path);
        } catch (IOException e) {
            throw new ValidationException("Could not read/access file: " + path.toString());
        }

        validate(s);
    }

    @Override
    public void validate(GDSStream stream) throws ValidationException {
        UnbufferedTokenStream<CommonToken> tokenStream =
                new UnbufferedTokenStream<>(new GDSTokenSource(stream));
        validateAntlrStream(tokenStream);
    }

    private static void validateAntlrStream(TokenStream stream) throws ValidationException {
        GdsiiParser parser = new GdsiiParser(stream);
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);
        try {
            GdsiiParser.StreamContext sc = parser.stream();
        } catch (ParseCancellationException e) {
            throw new ValidationException(e);
        }
    }
}
