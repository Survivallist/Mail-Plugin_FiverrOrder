package ch.flavianthepavian.postplugin.config;

import ch.flavianthepavian.postplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.HashMap;
import java.util.Map;

public class Config
{
    static FileConfiguration config = Main.getInstance().getConfig();
    public static void createConfig()
    {
        Main.getInstance().getConfig().addDefaults(getDefaults());
        Main.getInstance().getConfig().options().copyDefaults(true);
        Main.getInstance().saveDefaultConfig();
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
    public static String getAccessErrorMessage()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages.karte_access_error"));
    }
    public static String getBuySuccessMessage()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages.buy_success"));
    }
    public static String getInputBriefMessage()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages.get_input_brief"));
    }
    public static String getInputPaketMessage()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages.get_input_paket"));
    }
    public static String getBuyCancelMessage()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages.buy_cancel"));
    }
    public static String getInputCancelMessage()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages.input_cancel"));
    }
    public static String getInputErrorMessage()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages.input_error"));
    }
    public static String getBriefSentMessage()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages.brief_sent"));
    }
    public static String getPaketSentMessage()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages.paket_sent"));
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
    public static String getChooseKarteTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("titles.choose_karte"));
    }
    public static String getFillPaketTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("titles.fill_paket"));
    }
    public static String getWhatToSendTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("titles.what_to_send"));
    }
    public static String getInsertBriefTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("titles.insert_brief"));
    }
    public static String getInsertPaketTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("titles.insert_paket"));
    }
    public static String getPrinterTexture()
    {
        return config.getString("textures.printer");
    }
    public static String getMailTexture()
    {
        return config.getString("textures.mail");
    }
    public static String getPaketTexture()
    {
        return config.getString("textures.paket");
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
    public static String getPaketItemTitle()
    {
        return ChatColor.translateAlternateColorCodes('&', config.getString("item_titles.paket"));
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
    public static Material getFillMaterial()
    {
        return Material.valueOf(config.getString("materials.fill").toUpperCase());
    }
    public static Material getPaketMaterial()
    {
        return Material.valueOf(config.getString("materials.paket").toUpperCase());
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
    public static float getDruckerPrice()
    {
        return (float) config.getDouble("price.drucker");
    }
    public static float getBriefPrice()
    {
        return (float) config.getDouble("price.brief");
    }
    public static float getPapierPrice()
    {
        return (float) config.getDouble("price.papier");
    }
    public static float getKartePrice()
    {
        return (float) config.getDouble("price.karte");
    }
    public static float getPaketPrice()
    {
        return (float) config.getDouble("price.paket");
    }
    public static float getSendBriefPrice()
    {
        return (float) config.getDouble("price.send_brief");
    }
    public static float getSendPaketPrice()
    {
        return (float) config.getDouble("price.send_paket");
    }

    static Map<String, Object> getDefaults()
    {
        Map<String, Object> map = new HashMap<>();

        map.put("messages.error_console", "Das ist nicht erlaubt");
        map.put("messages.karte_access_error", "Du darfst diese Grusskarte nicht bearbeiten");
        map.put("messages.buy_success", ChatColor.GREEN + "Einkauf erfolgreich");
        map.put("messages.get_input_brief", "Bitte gebe die Adresse des Empfängers in den Chat ein  (\"cancel\" um den Input zu canceln).");
        map.put("messages.get_input_paket", "Hey, du möchtest ein Paket abschicken, Sehr gerne... Nh.. Moment... Ich muss das eben durchscannen... . soo.. perfekt... Dann bräuchte ich einmal " +
                "die Adresse wo es hingeschickt werden soll im Chat (\"cancel\" um den Input zu canceln)");
        map.put("messages.buy_cancel", ChatColor.RED + "Einkauf fehlgeschlagen");
        map.put("messages.input_cancel", ChatColor.GREEN + "Dein Input wurde gecancelt");
        map.put("messages.input_error", ChatColor.RED + "Kann die Adresse nicht lesen. Probiere dieses Muster: (GS/WS-???");
        map.put("messages.brief_sent", "??? > Sie haben erfolgreich den Brief abgesendet, in 1-2h sollte ihr Brief an das gewünschte Briefkasten hingebracht werden! Ich wünsche " +
                "ihnen noch einen schönen Tag!");
        map.put("messages.paket_sent", "Du hast dein Paket zu {adresse} abgeschickt, der Empfänger erhält in 1-2 Tagen sein Paket von der InfinityPost.");
        map.put("titles.angela_petra", "Was willst du kaufen ?");
        map.put("titles.printer_start", "Lege die Dokumente ein");
        map.put("titles.printer_working", "Kopiere...");
        map.put("titles.printer_finish", "Kopieren fertig");
        map.put("titles.norbert_heinrich", "Was willst du kaufen?");
        map.put("titles.choose_karte", "Wähle eine Grusskarte aus");
        map.put("titles.fill_paket", "Fülle das Paket");
        map.put("titles.what_to_send", "Was willst du senden?");
        map.put("titles.insert_brief", "Lege einen Brief ein");
        map.put("titles.insert_paket", "Lege ein Paket ein");
        map.put("textures.printer", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYzZWI4OWYzZmU5M2ViY2JjY2RhOWQwYTE5YjY0MGRkYTc" +
                "xZTI4NGVhZjQ5NzgzMmZmNDdhZDJlYWM4ODIxIn19fQ==");
        map.put("textures.mail", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2IwNmNlNmZmYTNjNzMzYTE4NzMzOWQzZmNjZjAwN2E3ODRmNzM3ODg4" +
                "OGZkMjEwZTYzZGJhMjMzOTQ5ZGUzZSJ9fX0=");
        map.put("textures.paket", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzBmZWY5NDQxNjk5ZDE1YjcxMDQ2NzA3ZmM4ZTE5OGJkM2M1OTEy" +
                "YjBmNzYxYmNjNjg5NjRjMjE4YzczZjg4ZiJ9fX0=");
        map.put("item_titles.printer", ChatColor.WHITE + "Drucker");
        map.put("item_titles.brief", ChatColor.WHITE + "Briefumschlag");
        map.put("item_titles.papier", "Schreibpapier");
        map.put("item_titles.paket", "Paket");
        map.put("item_titles.karte", "Grusskarte");
        map.put("materials.brief", "BOOK");
        map.put("materials.papier", "PAPER");
        map.put("materials.fill", "STAINED_GLASS_PANE");
        map.put("materials.paket", "ENDER_CHEST");
        map.put("sounds.progress", "BLOCK_NOTE_XYLOPHONE");
        map.put("sounds.progress_primary", "BLOCK_NOTE_XYLOPHONE");
        map.put("sounds.progress_finish", "ENTITY_FIREWORK_TWINKLE");
        map.put("price.drucker", 59.99);
        map.put("price.brief", 5);
        map.put("price.papier", 5);
        map.put("price.karte", 5);
        map.put("price.paket", 5.99);
        map.put("price.send_brief", 4.99);
        map.put("price.send_paket", 24.99);

        return map;
    }
}
