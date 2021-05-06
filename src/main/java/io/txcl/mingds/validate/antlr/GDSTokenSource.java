package io.txcl.mingds.validate.antlr;

import io.txcl.mingds.record.base.GDSIIRecord;
import io.txcl.mingds.stream.GDSStream;
import java.util.Iterator;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;

public class GDSTokenSource implements TokenSource {
    private final Iterator<GDSIIRecord<?>> recordIterator;
    TokenFactory<?> factory;
    private int offset;

    public GDSTokenSource(GDSStream recordStream) {

        this.recordIterator = recordStream.iterator();
        this.offset = 0;
    }

    @Override
    public Token nextToken() {
        if (!recordIterator.hasNext()) {
            return new CommonToken(CommonToken.EOF);
        }
        GDSIIRecord<?> next = recordIterator.next();
        this.offset += next.getRecordSize();
        return next;
    }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public int getCharPositionInLine() {
        return this.offset;
    }

    @Override
    public CharStream getInputStream() {
        return null;
    }

    @Override
    public String getSourceName() {
        return IntStream.UNKNOWN_SOURCE_NAME;
    }

    @Override
    public void setTokenFactory(TokenFactory<?> factory) {
        this.factory = factory;
    }

    @Override
    public TokenFactory<?> getTokenFactory() {
        return this.factory;
    }
}
