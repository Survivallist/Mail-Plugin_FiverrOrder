package ch.flavianthepavian.postpluginforthatrobert.listeners;

import ch.flavianthepavian.postpluginforthatrobert.config.GrusskartenConfig;
import ch.flavianthepavian.postpluginforthatrobert.config.PaketeConfig;
import ch.flavianthepavian.postpluginforthatrobert.invs.Drucker;
import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class InteractListener implements Listener
{
    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        if(event.getHand().equals(EquipmentSlot.OFF_HAND))
        {
            return;
        }
        if(event.getItem() == null)
        {
            return;
        }
        if(!event.getItem().hasItemMeta())
        {
            return;
        }
        if(event.getItem().getItemMeta().getDisplayName() == null)
        {
            return;
        }
        if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Briefumschlag"))
        {
            event.setCancelled(true);
            Player player = event.getPlayer();
            List<Integer> cards = GrusskartenConfig.getConfig().getIntegerList(player.getUniqueId().toString());
            Inventory inv = Bukkit.createInventory(player, (cards.size() - (cards.size() % 9) + 9), "Wähle eine Grusskarte aus");
            for (int index : cards)
            {
                ItemStack stack = new ItemStack(Material.BOOK_AND_QUILL);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName("Grusskarte Nr. " + index);
                List<String> lore = new ArrayList<>();
                StringBuilder text = new StringBuilder();
                if(GrusskartenConfig.getConfig().getConfigurationSection(index + ".text") != null)
                {
                    for (String key : GrusskartenConfig.getConfig().getConfigurationSection(index + ".text").getKeys(false))
                    {
                        text.append(GrusskartenConfig.getConfig().getString(index + ".text." + key)).append(" ");
                        text.setLength(text.length() - 1);
                    }
                }
                int word = 0;
                int length = text.toString().split(" ").length;
                for(int i = 0; i < 2; i++)
                {
                    if(!(word >= length))
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append(ChatColor.GRAY);
                        while (builder.length() < 25 && !(word >= length))
                        {
                            builder.append(text.toString().split(" ")[word]).append(" ");
                            word++;
                        }
                        builder.setLength(builder.length() - 1);
                        lore.add(builder.toString());
                    }
                }
                if(lore.get(0).equalsIgnoreCase(ChatColor.GRAY.toString()))
                {
                    lore.set(0,ChatColor.GRAY + "Kein Text");
                }
                meta.setLore(lore);
                stack.setItemMeta(meta);
                inv.addItem(stack);
            }
            player.openInventory(inv);
        }
        else if(event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Paket"))
        {
            event.setCancelled(true);
            Player player = event.getPlayer();
            int id = Integer.parseInt(event.getItem().getItemMeta().getLore().get(0).split(" ")[2]);
            Inventory inv = Bukkit.createInventory(player, 27, "Fülle das Paket");

            ItemStack fill = new ItemStack(Material.STAINED_GLASS_PANE);
            ItemMeta fillMeta = fill.getItemMeta();
            fillMeta.setDisplayName(" ");
            fillMeta.setLore(new ArrayList<>());
            fill.setItemMeta(fillMeta);
            for(int i = 0; i < inv.getSize(); i++)
            {
                inv.setItem(i, fill);
            }
            inv.setItem(13, PaketeConfig.getConfig().getItemStack(id + ".item"));
            ItemStack close = new ItemStack(Material.EMERALD_BLOCK);
            ItemMeta closeMeta = close.getItemMeta();
            closeMeta.setDisplayName(ChatColor.WHITE + "Paket schliessen");
            closeMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Clicke, um das Paket Nr. " + id + " zu schliessen"));
            close.setItemMeta(closeMeta);
            inv.setItem(26, close);
            player.openInventory(inv);
        }
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }
        if (event.getClickedBlock() == null) {
            return;
        }
        if (event.getClickedBlock().getType() != Material.SKULL) {
            return;
        }
        SkullMeta owner = ((SkullMeta) event.getClickedBlock().getDrops().iterator().next().getItemMeta());
        GameProfile value = new GameProfile(UUID.randomUUID(), "Name");
        try
        {
            Field field = owner.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            value = (GameProfile) field.get(owner);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if(value == null)
        {
            return;
        }
        String text = value.getProperties().get("textures").iterator().next().getValue();
        if(text.equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNG" +
                "YzZWI4OWYzZmU5M2ViY2JjY2RhOWQwYTE5YjY0MGRkYTcxZTI4NGVhZjQ5NzgzMmZmNDdhZDJlYWM4ODIxIn19fQ=="))
        {
            event.getPlayer().sendMessage(" hit");
            Drucker.openMain(event.getPlayer());
        }
    }
}
