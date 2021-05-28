package io.txcl.mingds.cli;

import io.txcl.mingds.validate.RecordValidator;
import io.txcl.mingds.validate.ValidationException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class GDSValidate {
    public static void mainWrapped(String[] args, Consumer<Integer> onExit) {
        ArgumentParser ap = ArgumentParsers.newArgumentParser("gdsvalidate");
        ap.addArgument("files").nargs("+");

        final Namespace namespace;

        try {
            namespace = ap.parseArgs(args);
        } catch (ArgumentParserException ape) {
            ape.printStackTrace();
            ap.printHelp();
            onExit.accept(1);
            return;
        }

        List<String> files = namespace.getList("files");

        final RecordValidator validator = new RecordValidator();

        for (String name : files) {
            try {
                validator.validate(Path.of(name));
            } catch (ValidationException e) {
                e.printStackTrace();
                onExit.accept(1);
                return;
            }
        }

        onExit.accept(0);
    }

    public static void main(String[] args) {
        mainWrapped(args, System::exit);
    }
}
