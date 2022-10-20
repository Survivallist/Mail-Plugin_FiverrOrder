package ch.flavianthepavian.postpluginforthatrobert.listeners;

import ch.flavianthepavian.postpluginforthatrobert.Main;
import ch.flavianthepavian.postpluginforthatrobert.config.Config;
import ch.flavianthepavian.postpluginforthatrobert.config.PaketeConfig;
import ch.flavianthepavian.postpluginforthatrobert.invs.AngelaPetra;
import ch.flavianthepavian.postpluginforthatrobert.invs.Drucker;
import ch.flavianthepavian.postpluginforthatrobert.invs.NorbertHeinrich;
import ch.flavianthepavian.postpluginforthatrobert.items.Grusskarte;
import ch.flavianthepavian.postpluginforthatrobert.items.Paket;
import de.infinitycity.banksystem.apis.buy.BuyAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class InvListener implements Listener
{
    static HashMap<UUID, ItemStack> getinput = new HashMap<>();
    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        if(event.getCurrentItem() == null)
        {
            return;
        }
        if(!event.getCurrentItem().hasItemMeta())
        {
            return;
        }
        if(event.getCurrentItem().getItemMeta().getDisplayName() == null)
        {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase(Config.getAngelaPetraTitle()))
        {
            event.setCancelled(true);
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Drucker"))
            {
                float price = (float) 6;
                BuyAPI.buySingleItem(player, AngelaPetra.drucker, price, new BuyAPI.BuyCallback() {
                    @Override
                    public void success(Player player) {
                        player.sendMessage("Success");
                        ItemStack stack = AngelaPetra.drucker;
                        if(player.getInventory().firstEmpty() == -1)
                        {
                            player.getWorld().dropItem(player.getLocation(), stack);
                        }
                        else
                        {
                            player.getInventory().addItem(stack);
                        }
                    }

                    @Override
                    public void abort(Player player) {
                        player.sendMessage("Cancelled");
                    }
                });
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Briefumschlag"))
            {
                float price = (float) 6;
                BuyAPI.buySingleItem(player, AngelaPetra.brief, price, new BuyAPI.BuyCallback() {
                    @Override
                    public void success(Player player) {
                        player.sendMessage("Success");
                        ItemStack stack = AngelaPetra.brief;
                        if(player.getInventory().firstEmpty() == -1)
                        {
                            player.getWorld().dropItem(player.getLocation(), stack);
                        }
                        else
                        {
                            player.getInventory().addItem(stack);
                        }
                    }

                    @Override
                    public void abort(Player player) {
                        player.sendMessage("Cancelled");
                    }
                });
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Schreibpapier"))
            {
                float price = (float) 6;
                BuyAPI.buySingleItem(player, AngelaPetra.papier, price, new BuyAPI.BuyCallback() {
                    @Override
                    public void success(Player player) {
                        player.sendMessage("Success");
                        ItemStack stack = AngelaPetra.papier;
                        if(player.getInventory().firstEmpty() == -1)
                        {
                            player.getWorld().dropItem(player.getLocation(), stack);
                        }
                        else
                        {
                            player.getInventory().addItem(stack);
                        }
                    }

                    @Override
                    public void abort(Player player) {
                        player.sendMessage("Cancelled");
                    }
                });
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Grusskarte"))
            {
                float price = (float) 6;
                BuyAPI.buySingleItem(player, AngelaPetra.grusskarte, price, new BuyAPI.BuyCallback() {
                    @Override
                    public void success(Player player) {
                        player.sendMessage("Success");
                        ItemStack stack = AngelaPetra.grusskarte;
                        if(player.getInventory().firstEmpty() == -1)
                        {
                            player.getWorld().dropItem(player.getLocation(), new Grusskarte(player, "new").getStack());
                        }
                        else
                        {
                            player.getInventory().addItem(new Grusskarte(player, "new").getStack());
                        }
                    }

                    @Override
                    public void abort(Player player) {
                        player.sendMessage("Cancelled");
                    }
                });
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase("Wähle eine Grusskarte aus"))
        {
            event.setCancelled(true);
            if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Grusskarte Nr. "))
            {
                int id = Integer.parseInt(event.getCurrentItem().getItemMeta().getDisplayName().split(" ")[2]);

                int slot = 45;
                for(int i = 0; i < 9; i++)
                {
                    if(player.getInventory().getItem(i) != null)
                    {
                        if (player.getInventory().getItem(i).getType() == Material.BOOK)
                        {
                            if(player.getInventory().getItem(i).hasItemMeta())
                            {
                                if(player.getInventory().getItem(i).getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Briefumschlag"))
                                {
                                    slot = i;
                                    break;
                                }
                            }
                        }
                    }
                }
                ItemStack stack = new ItemStack(Material.BOOK);
                ItemMeta meta = stack.getItemMeta();
                meta.setDisplayName(ChatColor.WHITE + "Briefumschlag");
                meta.setLore(Collections.singletonList(ChatColor.GRAY + "Enthält Grusskarte Nr. " + id));
                stack.setItemMeta(meta);
                player.getInventory().setItem(slot, stack);
                player.closeInventory();
            }
        }
        else if(event.getView().getTitle().equals("Füge Dokumente hinzu"))
        {
            if(!(event.getSlot() == 10 || event.getSlot() == 12 || event.getSlot() == 13 || event.getSlot() == 16) && event.getRawSlot() < 36)
            {
                event.setCancelled(true);
            }
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Kopieren"))
            {
                List<ItemStack> togiveback = new ArrayList<>();
                togiveback.add(event.getInventory().getItem(10));
                togiveback.add(event.getInventory().getItem(12));
                togiveback.add(event.getInventory().getItem(13));
                if(event.getInventory().getItem(10) == null)
                {
                    player.sendMessage(ChatColor.RED + "Du hast kein beschriebenes Buch eingelegt.");
                    player.closeInventory();
                    return;
                }
                if(event.getInventory().getItem(10).getType() != Material.WRITTEN_BOOK)
                {
                    player.sendMessage(ChatColor.RED + "Du hast kein beschriebenes Buch eingelegt.");
                    player.closeInventory();
                    return;
                }
                if (!((BookMeta) event.getInventory().getItem(10).getItemMeta()).hasPages())
                {
                    player.sendMessage(ChatColor.RED + "Das eingelegte Buch hat keinen Text");
                    player.closeInventory();
                    return;
                }
                if(((BookMeta) event.getInventory().getItem(10).getItemMeta()).getPages().get(0).equalsIgnoreCase("") ||
                        ((BookMeta) event.getInventory().getItem(10).getItemMeta()).getPages().get(0).isEmpty())
                {
                    player.sendMessage(ChatColor.RED + "Das eingelegte Buch hat keinen Text");
                    player.closeInventory();
                    return;
                }
                if(event.getInventory().getItem(12) == null)
                {
                    player.sendMessage(ChatColor.RED + "Du hast keinen Tintensack eingelegt.");
                    player.closeInventory();
                    return;
                }
                if(event.getInventory().getItem(12).getType() != Material.INK_SACK)
                {
                    player.sendMessage(ChatColor.RED + "Du hast keinen Tintensack eingelegt.");
                    player.closeInventory();
                    return;
                }
                if(event.getInventory().getItem(13) == null)
                {
                    player.sendMessage(ChatColor.RED + "Du hast kein Buch eingelegt.");
                    player.closeInventory();
                    return;
                }
                if(event.getInventory().getItem(13).getType() != Material.BOOK)
                {
                    player.sendMessage(ChatColor.RED + "Du hast kein Buch eingelegt.");
                    player.closeInventory();
                    return;
                }
                Drucker.uuids.add(player.getUniqueId());
                Drucker.openLoading(player, togiveback);
            }
        }
        else if(event.getView().getTitle().equals("Kopieren..."))
        {
            event.setCancelled(true);
        }
        else if(event.getView().getTitle().equals("Fertig"))
        {
            if(event.getSlot() != 12 && event.getSlot() != 14)
            {
                event.setCancelled(true);
            }
        }
        else if(event.getView().getTitle().equalsIgnoreCase("NorbertHeinrich"))
        {
            event.setCancelled(true);
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Drucker"))
            {
                float price = (float) 6;
                BuyAPI.buySingleItem(player, AngelaPetra.drucker, price, new BuyAPI.BuyCallback() {
                    @Override
                    public void success(Player player) {
                        player.sendMessage("Success");
                        ItemStack stack = AngelaPetra.drucker;
                        if(player.getInventory().firstEmpty() == -1)
                        {
                            player.getWorld().dropItem(player.getLocation(), stack);
                        }
                        else
                        {
                            player.getInventory().addItem(stack);
                        }
                    }

                    @Override
                    public void abort(Player player) {
                        player.sendMessage("Cancelled");
                    }
                });
            }

            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Etwas versenden"))
            {
                NorbertHeinrich.openSend(player);
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Grusskarte"))
            {
                event.setCancelled(true);
                float price = (float) 6;
                BuyAPI.buySingleItem(player, AngelaPetra.grusskarte, price, new BuyAPI.BuyCallback() {
                    @Override
                    public void success(Player player) {
                        player.sendMessage("Success");
                        ItemStack stack = AngelaPetra.grusskarte;
                        if(player.getInventory().firstEmpty() == -1)
                        {
                            player.getWorld().dropItem(player.getLocation(), new Grusskarte(player, "new").getStack());
                        }
                        else
                        {
                            player.getInventory().addItem(new Grusskarte(player, "new").getStack());
                        }
                    }

                    @Override
                    public void abort(Player player) {
                        player.sendMessage("Cancelled");
                    }
                });
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Paket"))
            {
                event.setCancelled(true);
                float price = 5.99f;
                BuyAPI.buySingleItem(player, new ItemStack(Material.ENDER_CHEST), price, new BuyAPI.BuyCallback() {
                    @Override
                    public void success(Player player) {
                        player.sendMessage("Success");
                        ItemStack stack = new ItemStack(Material.ENDER_CHEST);
                        ItemMeta meta = stack.getItemMeta();
                        meta.setDisplayName(ChatColor.WHITE + "Paket");
                        meta.setLore(Collections.singletonList(ChatColor.GRAY + "Kein Inhalt"));
                        stack.setItemMeta(meta);
                        if(player.getInventory().firstEmpty() == -1)
                        {
                            player.getWorld().dropItem(player.getLocation(), new Paket(player, "new").getStack());
                        }
                        else
                        {
                            player.getInventory().addItem(new Paket(player, "new").getStack());
                        }
                    }

                    @Override
                    public void abort(Player player) {
                        player.sendMessage("Cancelled");
                    }
                });
            }
        }
        else if(event.getView().getTitle().equals("Lege einen Brief ein"))
        {
            if(event.getSlot() != 13 && event.getRawSlot() < 27)
            {
                event.setCancelled(true);
            }
            if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Brief absenden"))
            {
                if(event.getInventory().getItem(13) == null)
                {
                    return;
                }
                if (!event.getInventory().getItem(13).getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Briefumschlag"))
                {
                    return;
                }
                getinput.put(player.getUniqueId(), event.getInventory().getItem(13));
                player.sendMessage(ChatColor.WHITE + "Gebe die Adresse ein (GS/WS-???. Um abzubrechen, gebe \"cancel\" ein");
                player.closeInventory();
            }
        }
        else if(event.getView().getTitle().equals("Lege ein Paket ein"))
        {
            if(event.getSlot() != 13 && event.getRawSlot() < 27)
            {
                event.setCancelled(true);
            }
            if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Paket absenden"))
            {
                if(event.getInventory().getItem(13) == null)
                {
                    return;
                }
                if (!event.getInventory().getItem(13).getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Paket"))
                {
                    return;
                }
                getinput.put(player.getUniqueId(), event.getInventory().getItem(13));
                player.sendMessage(ChatColor.WHITE + "Hey, du möchtest ein Paket abschicken, Sehr gerne... Nh.. Moment... Ich muss das eben durchscannen... . soo.. perfekt... Dann " +
                        "bräuchte ich einmal die Adresse wo es hingeschickt werden soll. Um abzubrechen, gebe \"cancel\" ein");
                player.closeInventory();
            }
        }
        else if(event.getView().getTitle().equals("Fülle das Paket"))
        {
            event.setCancelled(true);
            player.closeInventory();
        }
        else if(event.getView().getTitle().equals("Was willst du versenden?"))
        {
            event.setCancelled(true);
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE + "Brief absenden"))
            {
                NorbertHeinrich.openSendBrief(player);
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Paket absenden"))
            {
                NorbertHeinrich.openSendPaket(player);
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        if(getinput.containsKey(event.getPlayer().getUniqueId()))
        {
            event.setCancelled(true);
            if(event.getMessage().equalsIgnoreCase("cancel"))
            {
                getinput.remove(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage("Input wurde gecancelt");
                return;
            }
            String id = event.getMessage();
            if(!id.startsWith("GS-") && !id.startsWith("WS-"))
            {
                event.getPlayer().sendMessage("Dein Input kann nicht verarbeitet werden. Halte dich an dieses Muster: (GS/WS-???)");
                return;
            }
            if(id.length() != 6)
            {
                event.getPlayer().sendMessage("Dein Input kann nicht verarbeitet werden. Halte dich an dieses Muster: (GS/WS-???)");
                return;
            }
            try{
                int i = Integer.parseInt(id.split("-")[1]);
            }catch (NumberFormatException e)
            {
                event.getPlayer().sendMessage("Dein Input kann nicht verarbeitet werden. Halte dich an dieses Muster: (GS/WS-???)");
                return;
            }
            Player player = event.getPlayer();
            ItemStack item = getinput.get(player.getUniqueId());
            getinput.remove(player.getUniqueId());
            if(item.getType() == Material.BOOK)
            {
                BuyAPI.buySingleItem(player, new ItemStack(Material.BOOK), 4.99f, new BuyAPI.BuyCallback() {
                    @Override
                    public void success(Player player) {
                        //hier wurde der brief erfolgreich verschickt
                        //Der Itemstack item ist das item das verschickt wurde
                        //Der string id die Briefkasten id
                        player.sendMessage("??? > Sie haben erfolgreich den Brief abgesendet, in 1-2h sollte ihr Brief an das gewünschte Briefkasten hingebracht werden! Ich " +
                                "wünsche ihnen noch einen schönen Tag!");
                    }

                    @Override
                    public void abort(Player player) {
                        player.sendMessage("Cancelled");
                    }
                });
            }
            else
            {
                BuyAPI.buySingleItem(player, new ItemStack(Material.ENDER_CHEST), 24.99f, new BuyAPI.BuyCallback() {
                    @Override
                    public void success(Player player) {
                        //hier wurde das paket erfolgreich verschickt
                        //Der Itemstack item ist das item das verschickt wurde
                        //Der string id die Briefkasten id
                        player.sendMessage("Du hast dein Paket zu " + id + " abgeschickt, der Empfänger erhält in 1-2 Tagen sein Paket von der InfinityPost.");
                    }

                    @Override
                    public void abort(Player player) {
                        player.sendMessage("Cancelled");
                    }
                });
            }
        }
    }

    @EventHandler
    public void close(InventoryCloseEvent event)
    {
        if(Drucker.uuids.contains(event.getPlayer().getUniqueId()))
        {
            Drucker.uuids.remove(event.getPlayer().getUniqueId());
            return;
        }
        if(event.getView().getTitle().equals("Füge Dokumente hinzu"))
        {
            Player player = (Player) event.getPlayer();
            addItem(player, event.getInventory().getItem(10));
            addItem(player, event.getInventory().getItem(12));
            addItem(player, event.getInventory().getItem(13));
        } else if (event.getView().getTitle().equals("Kopieren...")) {
            Player player = (Player) event.getPlayer();
            addItem(player, event.getInventory().getItem(10));
            addItem(player, new ItemStack(Material.INK_SACK));
            addItem(player, event.getInventory().getItem(16));
        } else if (event.getView().getTitle().equals("Fertig")) {
            Player player = (Player) event.getPlayer();
            addItem(player, event.getInventory().getItem(12));
            addItem(player, event.getInventory().getItem(14));
        }
        else if(event.getView().getTitle().equals("Fülle das Paket"))
        {
            int id = Integer.parseInt(event.getInventory().getItem(26).getItemMeta().getLore().get(0).split(" ")[5]);
            PaketeConfig.getConfig().set(id + ".item", event.getInventory().getItem(13));
            PaketeConfig.save();
        }
    }

    static void addItem(Player player, ItemStack stack)
    {
        if(stack == null)
        {
            return;
        }
        if(player.getInventory().firstEmpty() == -1)
        {
            player.getWorld().dropItem(player.getLocation(), stack);
        }
        else
        {
            player.getInventory().addItem(stack);
        }
    }
}
