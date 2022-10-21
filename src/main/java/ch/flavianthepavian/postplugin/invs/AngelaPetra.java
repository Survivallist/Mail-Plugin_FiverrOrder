package ch.flavianthepavian.postplugin.invs;

import ch.flavianthepavian.postplugin.config.Config;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class AngelaPetra
{
    public static ItemStack drucker = null;
    public static ItemStack brief = null;
    public static ItemStack papier = null;
    public static ItemStack grusskarte = null;
    static Inventory baseInv = null;
    public static void openInv(Player player)
    {
        if(baseInv != null)
        {
            player.openInventory(baseInv);
        }
        else
        {
            Inventory inv = Bukkit.createInventory(player, 27, Config.getAngelaPetraTitle());

            ItemStack drucker = getSkull(Config.getPrinterTexture(), Config.getPrinterItemTitle(), Collections.singletonList(ChatColor.GRAY +
                    "Clicke, um einen Drucker zu kaufen"));
            inv.setItem(10, drucker);

            ItemStack brief = new ItemStack(Config.getBriefMaterial());
            ItemMeta briefMeta = brief.getItemMeta();
            briefMeta.setDisplayName(Config.getBriefItemTitle());
            briefMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Clicke, um einen Briefumschlag zu kaufen"));
            brief.setItemMeta(briefMeta);
            inv.setItem(12, brief);

            ItemStack papier = new ItemStack(Config.getPapierMaterial());
            ItemMeta papierMeta = papier.getItemMeta();
            papierMeta.setDisplayName(Config.getPapierItemTitle());
            papierMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Clicke, um Schreibpapier zu kaufen"));
            papier.setItemMeta(papierMeta);
            inv.setItem(14, papier);

            ItemStack grusskarte = new ItemStack(Material.BOOK_AND_QUILL);
            ItemMeta grusskarteMeta = grusskarte.getItemMeta();
            grusskarteMeta.setDisplayName(Config.getKarteItemTitle());
            grusskarteMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Clicke, um eine Grusskarte zu kaufen"));
            grusskarte.setItemMeta(grusskarteMeta);
            inv.setItem(16, grusskarte);

            player.openInventory(inv);
            baseInv = inv;

            AngelaPetra.drucker = getSkull(Config.getPrinterTexture(), Config.getPrinterItemTitle(), Collections.emptyList());

            AngelaPetra.brief = new ItemStack(Config.getBriefMaterial());
            briefMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Keine Grusskarte"));
            AngelaPetra.brief.setItemMeta(briefMeta);

            AngelaPetra.papier = new ItemStack(Config.getPapierMaterial());
            papierMeta.setLore(Collections.emptyList());
            AngelaPetra.papier.setItemMeta(papierMeta);

            AngelaPetra.grusskarte = new ItemStack(Material.BOOK_AND_QUILL);
            grusskarteMeta.setLore(Collections.emptyList());
            AngelaPetra.grusskarte.setItemMeta(grusskarteMeta);
        }
    }

    public static ItemStack getSkull(String url, String name, List<String> lore) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        if (url.isEmpty())
        {
            Bukkit.getLogger().warning("Head URL empty");
            return head;
        }
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "null");

        profile.getProperties().put("textures", new Property("textures", url, null));

        try
        {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        }
        catch (IllegalArgumentException|NoSuchFieldException|SecurityException | IllegalAccessException error)
        {
            error.printStackTrace();
        }
        headMeta.setDisplayName(name);
        headMeta.setLore(lore);

        head.setItemMeta(headMeta);

        return head;
    }
}
