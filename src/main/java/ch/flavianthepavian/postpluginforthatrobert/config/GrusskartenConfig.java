package ch.flavianthepavian.postpluginforthatrobert.config;

import ch.flavianthepavian.postpluginforthatrobert.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class GrusskartenConfig
{
    static File customConfigFile;
    static FileConfiguration customConfig;

    public static FileConfiguration getConfig() {
        return customConfig;
    }

    public static void createCustomConfig() {
        customConfigFile = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/grusskarten.yml");
        if (!customConfigFile.exists()) {
            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void save()
    {
        try {
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reload()
    {
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPath()
    {
        return Main.getInstance().getDataFolder().getAbsolutePath() + "/grusskarten.yml";
    }
}
