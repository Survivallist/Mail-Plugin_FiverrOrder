package ch.flavianthepavian.postpluginforthatrobert.invs;

import ch.flavianthepavian.postpluginforthatrobert.config.Config;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class NorbertHeinrich
{
    public static ItemStack drucker = null;
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
            Inventory inv = Bukkit.createInventory(player, 27, Config.getNorbertHeinrichTitle());

            ItemStack drucker = getSkull(Config.getPrinterTexture(), Config.getPrinterItemTitle(), Collections.singletonList(ChatColor.GRAY +
                    "Clicke, um einen Drucker zu kaufen"));
            inv.setItem(10, drucker);


            ItemStack grusskarte = new ItemStack(Material.BOOK_AND_QUILL);
            ItemMeta grusskarteMeta = grusskarte.getItemMeta();
            grusskarteMeta.setDisplayName(Config.getKarteItemTitle());
            grusskarteMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Clicke, um eine Grusskarte zu kaufen"));
            grusskarte.setItemMeta(grusskarteMeta);
            inv.setItem(14, grusskarte);

            ItemStack send = getSkull(Config.getMailTexture(), ChatColor.WHITE + "Etwas versenden", Collections.singletonList(ChatColor.GRAY + "Clicke, um etwas " +
                    "zu versenden")); //
            inv.setItem(12, send);

            ItemStack paket = new ItemStack(Config.getPaketMaterial());
            ItemMeta packetMeta = paket.getItemMeta();
            packetMeta.setDisplayName(Config.getPaketItemTitle());
            packetMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Clicke, um ein leeres packet zu kaufen"));
            paket.setItemMeta(packetMeta);
            inv.setItem(16, paket);

            player.openInventory(inv);
            baseInv = inv;

            AngelaPetra.drucker = getSkull(Config.getPrinterTexture(), Config.getPrinterItemTitle(), Collections.emptyList());

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

    public static void openSend(Player player)
    {
        Inventory inv = Bukkit.createInventory(player, InventoryType.HOPPER, Config.getWhatToSendTitle());

        ItemStack fill = new ItemStack(Config.getFillMaterial());
        ItemMeta fillMeta = fill.getItemMeta();
        fillMeta.setDisplayName(" ");
        fillMeta.setLore(new ArrayList<>());
        fill.setItemMeta(fillMeta);
        for(int i = 0; i < inv.getSize(); i++)
        {
            inv.setItem(i, fill);
        }


        ItemStack sendBrief = getSkull(Config.getMailTexture(), ChatColor.WHITE + "Brief absenden", Collections.singletonList(ChatColor.GRAY + "Clicke, um einen Brief " +
                "zu versenden"));
        inv.setItem(0, sendBrief);

        ItemStack sendPaket = getSkull(Config.getPaketTexture(), ChatColor.WHITE + "Paket absenden", Collections.singletonList(ChatColor.GRAY + "Clicke, um ein Paket " +
                "zu versenden"));
        inv.setItem(4, sendPaket);

        player.openInventory(inv);
    }

    public static void openSendBrief(Player player)
    {
        Inventory inv = Bukkit.createInventory(player, 27, Config.getInsertBriefTitle());

        ItemStack fill = new ItemStack(Config.getFillMaterial());
        ItemMeta fillMeta = fill.getItemMeta();
        fillMeta.setDisplayName(" ");
        fillMeta.setLore(new ArrayList<>());
        fill.setItemMeta(fillMeta);
        for(int i = 0; i < inv.getSize(); i++)
        {
            inv.setItem(i, fill);
        }

        inv.setItem(13, null);

        ItemStack send = getSkull(Config.getMailTexture(), ChatColor.WHITE + "Brief absenden", Collections.singletonList(ChatColor.GRAY + "Clicke, um einen Brief " +
                "zu versenden"));
        inv.setItem(22, send);

        player.openInventory(inv);
    }
    public static void openSendPaket(Player player)
    {
        Inventory inv = Bukkit.createInventory(player, 27, Config.getInsertPaketTitle());

        ItemStack fill = new ItemStack(Config.getFillMaterial());
        ItemMeta fillMeta = fill.getItemMeta();
        fillMeta.setDisplayName(" ");
        fillMeta.setLore(new ArrayList<>());
        fill.setItemMeta(fillMeta);
        for(int i = 0; i < inv.getSize(); i++)
        {
            inv.setItem(i, fill);
        }

        inv.setItem(13, null);

        ItemStack send = getSkull(Config.getMailTexture(), ChatColor.WHITE + "Paket absenden", Collections.singletonList(ChatColor.GRAY + "Clicke, um ein Paket " +
                "zu versenden"));
        inv.setItem(22, send);

        player.openInventory(inv);
    }
}
