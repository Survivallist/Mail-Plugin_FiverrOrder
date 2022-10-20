package ch.flavianthepavian.postpluginforthatrobert.invs;

import ch.flavianthepavian.postpluginforthatrobert.Main;
import ch.flavianthepavian.postpluginforthatrobert.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.*;

public class Drucker
{
    public static List<UUID> uuids = new ArrayList<>();
    public static void openMain(Player player)
    {
        Inventory inv = Bukkit.createInventory(player, 36, Config.getPrinterStartTitle());
        inv.setMaxStackSize(1);
        ItemStack copybuch = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta copybuchMeta = (BookMeta) copybuch.getItemMeta();
        copybuchMeta.setDisplayName(ChatColor.WHITE + "Füge ein zu kopierendes Buch hinzu");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Lege ein Buch, das kopiert werden");
        lore.add(ChatColor.GRAY + "soll, in den freien Slot ein");
        copybuchMeta.setLore(lore);
        copybuch.setItemMeta(copybuchMeta);
        inv.setItem(19, copybuch);

        ItemStack tinte = new ItemStack(Material.INK_SACK);
        ItemMeta tintenMeta = tinte.getItemMeta();
        tintenMeta.setDisplayName(ChatColor.WHITE + "Füge ein Tintensack hinzu");
        tintenMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Lege einen Tintensack in den freien Slot"));
        tinte.setItemMeta(tintenMeta);
        inv.setItem(21, tinte);

        ItemStack buch = new ItemStack(Material.BOOK);
        ItemMeta buchMeta = tinte.getItemMeta();
        buchMeta.setDisplayName(ChatColor.WHITE + "Füge ein Buch hinzu");
        buchMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Lege ein Buch in den freien Slot"));
        buch.setItemMeta(buchMeta);
        inv.setItem(22, buch);

        ItemStack go = AngelaPetra.getSkull(Config.getPrinterTexture(), ChatColor.WHITE + "Kopieren", Collections.singletonList(ChatColor.GRAY + "Clicke, um den" +
                " Kopierprozess zu starten"));
        inv.setItem(25, go);

        ItemStack fill = new ItemStack(Config.getFillMaterial());
        ItemMeta fillMeta = fill.getItemMeta();
        fillMeta.setDisplayName(" ");
        fillMeta.setLore(new ArrayList<>());
        fill.setItemMeta(fillMeta);
        for(int i = 0; i < inv.getSize(); i++)
        {
            if(inv.getItem(i) == null && (i != 10 && i != 12 && i != 13 && i != 16))
            {
                inv.setItem(i, fill);
            }
        }
        player.openInventory(inv);
    }

    public static void openLoading(Player player, List<ItemStack> togiveback)
    {
        Inventory inv = Bukkit.createInventory(player, 27, Config.getPrinterWorkingTitle());

        ItemStack fill = new ItemStack(Config.getFillMaterial());
        ItemMeta fillMeta = fill.getItemMeta();
        fillMeta.setDisplayName(" ");
        fillMeta.setLore(new ArrayList<>());
        fill.setItemMeta(fillMeta);
        for(int i = 0; i < inv.getSize(); i++)
        {
            if(inv.getItem(i) == null && (i != 10 && i != 12 && i != 13 && i != 16))
            {
                inv.setItem(i, fill);
            }
        }

        inv.setItem(10, togiveback.get(0));
        inv.setItem(16, new ItemStack(Material.BOOK));

        player.openInventory(inv);
        final int[] i = {0};
        BukkitRunnable base = new BukkitRunnable() {
            @Override
            public void run() {
                final int[] index = {11};
                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(index[0] != 11)
                        {
                            inv.setItem(index[0] - 1, fill);
                        }
                        else
                        {
                            inv.setItem(15, fill);
                        }
                        inv.setItem(index[0], new ItemStack(Material.INK_SACK));
                        player.updateInventory();
                        index[0]++;
                        player.playSound(player.getLocation(), Config.getProgressSound(), 1, 0.5f);
                        if(index[0] == 16)
                        {
                            cancel();
                            player.playSound(player.getLocation(), Config.getProgressSoundPrimary(), 1, 1);
                        }
                    }
                };
                runnable.runTaskTimer(Main.getInstance(), 0, 4);
                i[0]++;
                if(i[0] == 7)
                {
                    cancel();
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            if(!player.getOpenInventory().getTitle().equals(Config.getPrinterWorkingTitle()))
                            {
                                return;
                            }
                            uuids.add(player.getUniqueId());
                            openDone(player, togiveback);
                            player.playSound(player.getLocation(), Config.getProgressSoundFinish(), 1, 1);
                        }
                    }, 20);
                }
            }
        };
        base.runTaskTimer(Main.getInstance(), 0, 20);
    }

    static void openDone(Player player, List<ItemStack> book)
    {
        Inventory inv = Bukkit.createInventory(player, 27, Config.getPrinterFinishTitle());

        ItemStack fill = new ItemStack(Config.getFillMaterial());
        ItemMeta fillMeta = fill.getItemMeta();
        fillMeta.setDisplayName(" ");
        fillMeta.setLore(new ArrayList<>());
        fill.setItemMeta(fillMeta);
        for(int i = 0; i < inv.getSize(); i++)
        {
            inv.setItem(i, fill);
        }

        inv.setItem(12, book.get(0));
        inv.setItem(14, book.get(0));
        player.openInventory(inv);
    }
}
