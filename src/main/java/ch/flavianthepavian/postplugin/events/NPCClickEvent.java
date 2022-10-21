package ch.flavianthepavian.postplugin.events;

import ch.flavianthepavian.postplugin.config.SkinConfig;
import ch.flavianthepavian.postplugin.npcs.NPCType;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NPCClickEvent extends Event implements Cancellable
{
    private final Player player;
    private final EntityPlayer npc;
    private NPCType type;
    private boolean isCancelled;
    private static final HandlerList HANDLERS = new HandlerList();

    public NPCType getType() {
        return type;
    }

    public NPCClickEvent(Player player, EntityPlayer npc)
    {
        this.player = player;
        this.npc = npc;
        Property property = (Property) npc.getProfile().getProperties().get("textures").toArray()[0];
        if(property.getValue().equalsIgnoreCase(SkinConfig.getConfig().getString("PETRA.texture")))
        {
            type = NPCType.PETRA;
        }
        else if(property.getValue().equalsIgnoreCase(SkinConfig.getConfig().getString("ANGELA.texture")))
        {
            type = NPCType.ANGELA;
        }
        else if(property.getValue().equalsIgnoreCase(SkinConfig.getConfig().getString("NORBERT.texture")))
        {
            type = NPCType.NORBERT;
        }
        else
        {
            type = NPCType.HEINRICH;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public EntityPlayer getNPC()
    {
        return npc;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean icCancelled) {
        this.isCancelled = icCancelled;
    }
}
