package io.txcl.mingds.validate;

import io.txcl.mingds.GdsiiParser;
import io.txcl.mingds.stream.GDSStream;
import io.txcl.mingds.validate.antlr.GDSTokenSource;
import io.txcl.mingds.validate.antlr.ThrowingErrorListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
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

    public static TokenStream toTokenStream(GDSStream gdsStream) {
        return new UnbufferedTokenStream<>(new GDSTokenSource(gdsStream));
    }

    public static BufferedTokenStream toBufferedTokenStream(GDSStream stream) {
        return new BufferedTokenStream(new GDSTokenSource(stream));
    }

    @Override
    public void validate(GDSStream stream) throws ValidationException {
        validateAgainstRuleName(stream, "stream");
    }

    public static GdsiiParser getParser(GDSStream stream) {
        TokenStream tokenStream = toTokenStream(stream);
        return new GdsiiParser(tokenStream);
    }

    public static GdsiiParser.StreamContext getParseTree(GDSStream stream) {
        GdsiiParser parser = getParser(stream);
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);
        return parser.stream();
    }

    public static void validateAgainstRuleName(GDSStream stream, String ruleName)
            throws ValidationException {
        TokenStream tokenStream = toTokenStream(stream);
        GdsiiParser parser = new GdsiiParser(tokenStream);
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

        // NOTE(meawoppl) moderately dirty hack below. Edit with caution
        try {
            ParserRuleContext ctx =
                    (ParserRuleContext) parser.getClass().getMethod(ruleName).invoke(parser);
            ctx.getChildCount();
        } catch (ParseCancellationException e) {
            throw new ValidationException(e);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            System.err.printf("Did not find method to build parse component named: %s\n", ruleName);
            throw new RuntimeException("Unreachable?");
        } catch (InvocationTargetException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof ParseCancellationException) {
                throw new ValidationException(cause);
            }
        }
    }
}
