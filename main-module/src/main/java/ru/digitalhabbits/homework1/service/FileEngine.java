package ru.digitalhabbits.homework1.service;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import static java.util.Arrays.stream;

public class FileEngine {
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";

    private final String currentDirectory = System.getProperty("user.dir");
    private final File resultDirectory = new File(currentDirectory + "/" + RESULT_DIR);

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) throws IOException {
        // TODO: Review
        String fileName = String.format(RESULT_FILE_PATTERN, pluginName);

        boolean isDirectoryCreated = resultDirectory.exists();

        if (!isDirectoryCreated) {
            isDirectoryCreated = resultDirectory.mkdir();
        }
        if (isDirectoryCreated) {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
            bufferedWriter.write(text);
        }
        return true;
    }

    public void cleanResultDir() {
        if (resultDirectory.exists() && resultDirectory.isDirectory()) {
            stream(Objects.requireNonNull(resultDirectory
                    .list((dir, name) -> name.endsWith(RESULT_EXT))))
                    .forEach(fileName -> new File(resultDirectory + "/" + fileName)
                            .delete());

        }
    }
}
