package ch.flavianthepavian.postpluginforthatrobert.events;

import ch.flavianthepavian.postpluginforthatrobert.Main;
import ch.flavianthepavian.postpluginforthatrobert.npcs.Spawner;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PacketReader
{
    Channel channel;
    public static Map<UUID, Channel> channels = new HashMap<>();

    public void inject(Player player)
    {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
        channels.put(player.getUniqueId(), channel);
        if(channel.pipeline().get("PacketInjector") != null)
        {
            return;
        }
        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<PacketPlayInUseEntity>() {

            @Override
            protected void decode(ChannelHandlerContext channelHandlerContext, PacketPlayInUseEntity packet, List<Object> list) throws Exception {
                list.add(packet);
                readPacket(player, packet);
            }
        });
    }

    public void injectAll()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            inject(player);
        }
    }

    public void uninjectAll()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            uninject(player);
        }
    }

    public void uninject(Player player)
    {
        channel = channels.get(player.getUniqueId());
        if(channel.pipeline().get("PacketInjector") != null)
        {
            channel.pipeline().remove("PacketInjector");
        }
    }

    public void readPacket(Player player, Packet<?> packet)
    {
        if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK"))
        {
            callEvent((int) getValue(packet, "a"), player);
            return;
        }
        else if(getValue(packet, "d").toString().equalsIgnoreCase("OFF_HAND"))
        {
            return;
        }
        else if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT_AT"))
        {
            return;
        }
        else
        {
            callEvent((int) getValue(packet, "a"), player);
        }
    }

    private void callEvent(int id, Player player)
    {
        for(EntityPlayer npc : Spawner.getNPCs())
        {
            if(npc.getId() == id)
            {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.getPluginManager().callEvent(new NPCClickEvent(player, npc));
                    }
                }, 0);
            }
        }
    }

    private Object getValue(Object instance, String name)
    {
         Object result = null;
         try
         {
             Field field = instance.getClass().getDeclaredField(name);
             field.setAccessible(true);
             result = field.get(instance);
             field.setAccessible(false);
         }catch (Exception e)
         {
             e.printStackTrace();
         }

         return result;
    }
}
