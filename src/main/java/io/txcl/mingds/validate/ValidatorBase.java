package io.txcl.mingds.validate;

import io.txcl.mingds.stream.GDSStream;
import java.nio.file.Path;

public abstract class ValidatorBase {
    abstract void validate(Path path) throws ValidationException;

    abstract void validate(GDSStream stream) throws ValidationException;
}
