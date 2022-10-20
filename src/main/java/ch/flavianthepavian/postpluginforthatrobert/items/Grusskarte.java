package ch.flavianthepavian.postpluginforthatrobert.items;

import ch.flavianthepavian.postpluginforthatrobert.config.Config;
import ch.flavianthepavian.postpluginforthatrobert.config.GrusskartenConfig;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class Grusskarte
{
    final Player player;
    String text;

    ItemStack stack;

    final int id;


    public void setText(String text) {
        this.text = text;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public Player getPlayer() {
        return player;
    }

    public String getText() {
        return text;
    }

    public ItemStack getStack() {
        return stack;
    }

    public int getId() {
        return id;
    }

    public Grusskarte(Player player, String id)
    {
        this.player = player;
        if(id.equalsIgnoreCase("new"))
        {
            int i = 0;
            if(GrusskartenConfig.getConfig().contains("0.writer"))
            {
                i = GrusskartenConfig.getConfig().getKeys(false).size();
            }
            GrusskartenConfig.getConfig().set(i + ".writer", player.getUniqueId().toString());
            GrusskartenConfig.getConfig().set(i + ".text", "");
            List<String> list = new ArrayList<>();
            if(GrusskartenConfig.getConfig().contains(player.getUniqueId().toString()))
            {
                list = GrusskartenConfig.getConfig().getStringList(player.getUniqueId().toString());
            }
            list.add(String.valueOf(i));
            GrusskartenConfig.getConfig().set(player.getUniqueId().toString(), list);
            GrusskartenConfig.save();
            this.id = i;
        }
        else
        {
            this.id = Integer.parseInt(id);
        }
        text = GrusskartenConfig.getConfig().getString(player.getUniqueId() + "." + this.id);

        stack = new ItemStack(Material.BOOK_AND_QUILL);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Config.getKarteItemTitle());
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Grusskarte Nr. " + this.id);
        lore.add(ChatColor.GRAY + "Geschrieben von " + this.player.getName());
        meta.setLore(lore);
        stack.setItemMeta(meta);
    }
}
