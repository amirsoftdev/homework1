package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import static org.slf4j.LoggerFactory.getLogger;

public class PluginLoader {
    private static final Logger logger = getLogger(PluginLoader.class);

    private static final String PLUGIN_EXT = "jar";
    private static final String PACKAGE_TO_SCAN = "ru.digitalhabbits.homework1.plugin";

    @Nonnull
    public List<Class<? extends PluginInterface>> loadPlugins(@Nonnull String pluginDirName) throws IOException {
        // TODO: Review
        List<Class<? extends PluginInterface>> plugins = new ArrayList<>();
        ArrayList<String> classList = new ArrayList<>();
        ArrayList<URL> urlList = new ArrayList<>();

        File pluginDirectory = new File(pluginDirName);
        File[] files = pluginDirectory.listFiles((dir, name) -> name.endsWith(PLUGIN_EXT));

        if (files != null && files.length > 0) {
            for (File file : files) {
                try {
                    JarFile jarFile = new JarFile(file);
                    jarFile.stream().forEach(jarEntry -> {
                        if (jarEntry.getName().endsWith(".class")) {
                            classList.add(jarEntry.getName());
                        }
                    });

                    URL url = file.toURI().toURL();
                    urlList.add(url);

                    URLClassLoader urlClassLoader = new URLClassLoader(urlList.toArray(urlList.toArray(new URL[0])));
                    classList.forEach(classes -> {
                        try {
                            Class cls = urlClassLoader.loadClass(classes.replaceAll("/", ".")
                                    .replace(".class", ""));

                            Class[] interfaces = cls.getInterfaces();

                            for (Class i : interfaces) {
                                if (i.equals(PluginInterface.class)) {
                                    plugins.add(cls);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        return plugins;
    }
}
