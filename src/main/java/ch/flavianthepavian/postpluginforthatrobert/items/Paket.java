package ch.flavianthepavian.postpluginforthatrobert.items;

import ch.flavianthepavian.postpluginforthatrobert.config.Config;
import ch.flavianthepavian.postpluginforthatrobert.config.PaketeConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class Paket
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

    public Paket(Player player, String id)
    {
        this.player = player;
        if(id.equalsIgnoreCase("new"))
        {
            int i = 0;
            if(PaketeConfig.getConfig().contains("0.writer"))
            {
                i = PaketeConfig.getConfig().getKeys(false).size();
            }
            PaketeConfig.getConfig().set(i + ".writer", player.getUniqueId().toString());
            PaketeConfig.getConfig().set(i + ".item", "");
            PaketeConfig.getConfig().set(i + ".sent", false);
            List<String> list = new ArrayList<>();
            if(PaketeConfig.getConfig().contains(player.getUniqueId().toString()))
            {
                list = PaketeConfig.getConfig().getStringList(player.getUniqueId().toString());
            }
            list.add(String.valueOf(i));
            PaketeConfig.getConfig().set(player.getUniqueId().toString(), list);
            PaketeConfig.save();
            this.id = i;
        }
        else
        {
            this.id = Integer.parseInt(id);
        }
        text = PaketeConfig.getConfig().getString(player.getUniqueId() + "." + this.id);

        stack = new ItemStack(Config.getPaketMaterial());
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Config.getPaketItemTitle());
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Paket Nr. " + this.id);
        lore.add(ChatColor.GRAY + "Von " + this.player.getName());
        meta.setLore(lore);
        stack.setItemMeta(meta);
    }
}
