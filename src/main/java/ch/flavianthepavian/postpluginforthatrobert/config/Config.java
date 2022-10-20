package ch.flavianthepavian.postpluginforthatrobert.config;

import ch.flavianthepavian.postpluginforthatrobert.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;

public class Config
{
    static FileConfiguration config = Main.getInstance().getConfig();
    public static void createConfig()
    {
        Main.getInstance().reloadConfig();
        Main.getInstance().saveConfig();
    }

    public static void reload()
    {
        Main.getInstance().reloadConfig();
    }

    public static String getErrorConsoleMessage()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages.error_console"));
    }

    public static String getAngelaPetraTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("titles.angela_petra"));
    }
    public static String getPrinterStartTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("titles.printer_start"));
    }
    public static String getPrinterWorkingTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("titles.printer_working"));
    }
    public static String getPrinterFinishTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("titles.printer_finish"));
    }
    public static String getNorbertHeinrichTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("titles.norbert_heinrich"));
    }
    public static String getPrinterTexture()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("textures.printer"));
    }
    public static String getPrinterItemTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("item_titles.printer"));
    }
    public static String getBriefItemTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("item_titles.brief"));
    }
    public static String getPapierItemTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("item_titles.papier"));
    }
    public static String getKarteItemTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("item_titles.karte"));
    }
    public static Material getBriefMaterial()
    {
        return Material.valueOf(config.getString("materials.brief").toUpperCase());
    }
    public static Material getPapierMaterial()
    {
        return Material.valueOf(config.getString("materials.papier").toUpperCase());
    }
    public static Material getKarteMaterial()
    {
        return Material.valueOf(config.getString("materials.karte").toUpperCase());
    }
    public static Material getFillMaterial()
    {
        return Material.valueOf(config.getString("materials.fill").toUpperCase());
    }
    public static Sound getProgressSound()
    {
        return Sound.valueOf(config.getString("sounds.progress").toUpperCase());
    }
    public static Sound getProgressSoundPrimary()
    {
        return Sound.valueOf(config.getString("sounds.progress_primary").toUpperCase());
    }
    public static Sound getProgressSoundFinish()
    {
        return Sound.valueOf(config.getString("sounds.progress_finish").toUpperCase());
    }
}
