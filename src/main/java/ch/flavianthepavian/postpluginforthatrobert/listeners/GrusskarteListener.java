package ch.flavianthepavian.postpluginforthatrobert.listeners;

import ch.flavianthepavian.postpluginforthatrobert.config.Config;
import ch.flavianthepavian.postpluginforthatrobert.config.GrusskartenConfig;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import java.util.ArrayList;
import java.util.List;

public class GrusskarteListener implements Listener
{
    @EventHandler
    public void onEdit(PlayerEditBookEvent event)
    {
        if(event.isSigning())
        {
            return;
        }
        if (!event.getPreviousBookMeta().getLore().get(0).startsWith(Config.getKarteItemTitle()))
        {
            return;
        }
        String lore = event.getPreviousBookMeta().getLore().get(0);
        int id = Integer.parseInt(lore.split(" ")[2]);
        if(!GrusskartenConfig.getConfig().getString(id + ".writer").equalsIgnoreCase(event.getPlayer().getUniqueId().toString()))
        {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Config.getAccessErrorMessage());
            ItemStack toreturn = new ItemStack(Material.BOOK_AND_QUILL);
            BookMeta meta = (BookMeta) toreturn.getItemMeta();
            List<String> list = new ArrayList<>();
            for(String key : GrusskartenConfig.getConfig().getConfigurationSection(id + ".text").getKeys(false))
            {
                list.add(GrusskartenConfig.getConfig().getString(id + ".text." + key));
            }
            meta.setPages(list);
            toreturn.setItemMeta(meta);
            event.getPlayer().getInventory().setItem(event.getSlot(), toreturn);
            return;
        }
        int page = 1;
        for (String pageText : event.getNewBookMeta().getPages())
        {
            GrusskartenConfig.getConfig().set(id + ".text." + page, pageText);
            page++;
        }
        GrusskartenConfig.save();
    }
}
