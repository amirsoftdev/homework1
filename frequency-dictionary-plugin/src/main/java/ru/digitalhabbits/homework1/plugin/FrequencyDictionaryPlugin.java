package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrequencyDictionaryPlugin implements PluginInterface {

    private final Pattern pattern = Pattern.compile("(\\b[a-zA-Z][a-zA-Z.0-9]*\\b)");


    @Nullable
    @Override
    public String apply(@Nonnull String text) {
        //TODO:Review
        Map<String, Integer> wordQuantity = new TreeMap<>();

        Matcher matcher = pattern.matcher(text);
        matcher.results().forEach(result -> wordQuantity
                .merge(result.group().toLowerCase(), 1, Integer::sum));

        return wordQuantity
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " " + entry.getValue() + "\n")
                .reduce("", String::concat);
    }
}
