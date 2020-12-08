package ru.digitalhabbits.homework1.service;

import ru.digitalhabbits.homework1.plugin.PluginInterface;
import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PluginEngine {

    @Nonnull
    public <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {
        // TODO: Review
        String result = "";

        try {
            Method clsMethod = cls.getMethod("apply", String.class);
            PluginInterface plugin = cls.getConstructor().newInstance();
            result = (String) clsMethod.invoke(plugin, text);

        } catch (NoSuchMethodException | IllegalAccessException |
                InstantiationException | InvocationTargetException e) {

            e.printStackTrace();
        }
        return result;
    }
}
