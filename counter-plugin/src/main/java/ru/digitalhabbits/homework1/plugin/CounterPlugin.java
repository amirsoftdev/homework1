package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CounterPlugin implements PluginInterface {

    private final Pattern pattern = Pattern.compile("(\\b[a-zA-Z][a-zA-Z.0-9]*\\b)");

    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        //TODO: Review
        //Замена и приведение к нижнему регистру
        String textAfterReplace = text.replaceAll("\\\\n", "\n").toLowerCase();

        Matcher matcher = pattern.matcher(textAfterReplace);

        int lineLength = textAfterReplace.split("\\n").length;
        int lettersLength = textAfterReplace.length();
        int wordsCount = 0;

        while (matcher.find()) {
            wordsCount++;
        }
        return lineLength + ";" + wordsCount + ";" + lettersLength;
    }
}
