package ch.flavianthepavian.postplugin.npcs;

import ch.flavianthepavian.postplugin.events.NPCClickEvent;
import ch.flavianthepavian.postplugin.events.PacketReader;
import ch.flavianthepavian.postplugin.invs.AngelaPetra;
import ch.flavianthepavian.postplugin.invs.NorbertHeinrich;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener implements org.bukkit.event.Listener
{
    @EventHandler
    void onJoin(PlayerJoinEvent event)
    {
        PacketReader reader = new PacketReader();
        reader.inject(event.getPlayer());
        if (Spawner.getNPCs() == null)
        {
            return;
        }
        else if(Spawner.getNPCs().isEmpty())
        {
            return;
        }
        Spawner.addJoinPacket(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        PacketReader reader = new PacketReader();
        reader.uninject(event.getPlayer());
    }

    @EventHandler
    void onInteract(NPCClickEvent event)
    {
        if(event.getType() == NPCType.ANGELA || event.getType() == NPCType.PETRA)
        {
            AngelaPetra.openInv(event.getPlayer());
        }
        else if(event.getType() == NPCType.HEINRICH || event.getType() == NPCType.NORBERT)
        {
            NorbertHeinrich.openInv(event.getPlayer());
        }
    }
}
