package io.txcl.mingds.validate;

import com.google.common.base.Preconditions;
import io.txcl.mingds.stream.GDSStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

public class KLayoutValidator extends ValidatorBase {
    public KLayoutValidator() {}

    public static final String ANCHOR = "REPLACEME";
    public static final String CHECK_CODE =
            "import pya; layout = pya.Layout(); layout.read(\""
                    + ANCHOR
                    + "\"); print(\"SUCCESS\")\n";

    private static final AtomicBoolean KLAYOUT_AVAILABLE = new AtomicBoolean(false);

    public static boolean isKLayoutAvailable() {
        // Shortcut if we already have checked successfully
        if (KLAYOUT_AVAILABLE.get()) {
            return true;
        }

        boolean success = false;
        try {
            success = Runtime.getRuntime().exec("klayout -h").waitFor() == 0;
        } catch (Exception e) {
            System.err.println("Error while detecting klayout:");
            e.printStackTrace();
        }
        KLAYOUT_AVAILABLE.set(success);
        return success;
    }

    @Override
    public void validate(GDSStream stream) throws ValidationException {
        File tempFile;
        try {
            tempFile = File.createTempFile("testscript-", ".py");
            GDSStream.to(Path.of(tempFile.getPath()), stream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ValidationException("Unable to stream gds to tempfile?");
        }

        validate(tempFile.toPath());
    }

    @Override
    public void validate(Path path) throws ValidationException {
        Preconditions.checkArgument(
                isKLayoutAvailable(), "This validator requires klayout is available");

        // Make a tempdir with the script:
        File tempFile;
        try {
            tempFile = File.createTempFile("testscript", ".py");
            byte[] bytes = CHECK_CODE.replace(ANCHOR, path.toAbsolutePath().toString()).getBytes();
            Files.write(tempFile.toPath(), bytes);
        } catch (IOException e) {
            System.err.println("Can't write tempfile?");
            throw new ValidationException(e);
        }

        Process exec = null;
        try {
            exec =
                    Runtime.getRuntime()
                            .exec("klayout -zz -r " + tempFile.getAbsolutePath().toString());
            exec.waitFor();
        } catch (Exception e) {
            System.err.println("Problem running klayout?");
            throw new ValidationException(e);
        }

        if (exec.exitValue() != 0) {
            BufferedReader stdInput =
                    new BufferedReader(new InputStreamReader(exec.getInputStream()));

            BufferedReader stdError =
                    new BufferedReader(new InputStreamReader(exec.getErrorStream()));

            // Read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            try {
                while ((s = stdInput.readLine()) != null) {
                    System.out.println(s);
                }

                // Read any errors from the attempted command
                System.out.println("Here is the standard error of the command (if any):\n");
                while ((s = stdError.readLine()) != null) {
                    System.out.println(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new ValidationException(e);
            }

            throw new ValidationException("File did not validate.");
        }
    }
}
