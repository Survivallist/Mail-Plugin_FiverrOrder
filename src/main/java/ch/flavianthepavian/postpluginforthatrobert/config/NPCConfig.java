package ch.flavianthepavian.postpluginforthatrobert.config;

import ch.flavianthepavian.postpluginforthatrobert.Main;
import ch.flavianthepavian.postpluginforthatrobert.npcs.NPCType;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class NPCConfig
{
    static File customConfigFile;
    static FileConfiguration customConfig;

    public static FileConfiguration getConfig() {
        return customConfig;
    }

    public static void createCustomConfig() {
        customConfigFile = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/npcs.yml");
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
    
    public static void saveNPC(EntityPlayer npc, NPCType type, float yaw)
    {
        int var;

        if (!customConfig.contains("npcs." + type.toString()))
        {
            var = 0;
        }
        else
        {
            var = customConfig.getConfigurationSection("npcs." + type.toString()).getKeys(false).size();
        }
        customConfig.set("npcs." + type.toString() + "." + var + ".x", npc.getBukkitEntity().getLocation().getX());
        customConfig.set("npcs." + type.toString() + "." + var + ".y", npc.getBukkitEntity().getLocation().getY());
        customConfig.set("npcs." + type.toString() + "." + var + ".z", npc.getBukkitEntity().getLocation().getZ());
        customConfig.set("npcs." + type.toString() + "." + var + ".yaw", yaw);
        customConfig.set("npcs." + type.toString() + "." + var + ".pitch", npc.getBukkitEntity().getLocation().getPitch());
        customConfig.set("npcs." + type.toString() + "." + var + ".world", npc.getBukkitEntity().getLocation().getWorld().getName());
        save();
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
        return Main.getInstance().getDataFolder().getAbsolutePath() + "/npcs.yml";
    }
}
