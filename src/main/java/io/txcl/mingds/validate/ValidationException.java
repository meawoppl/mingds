package io.txcl.mingds.validate;

public class ValidationException extends Throwable {
    public ValidationException(Throwable e) {
        super(e);
    }

    public ValidationException(String s) {
        super(new RuntimeException(s));
    }
}
