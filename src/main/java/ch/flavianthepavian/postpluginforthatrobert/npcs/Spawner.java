package ch.flavianthepavian.postpluginforthatrobert.npcs;

import ch.flavianthepavian.postpluginforthatrobert.config.NPCConfig;
import ch.flavianthepavian.postpluginforthatrobert.config.SkinConfig;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Spawner
{
    //The NPCs are stored here
    private static List<EntityPlayer> NPC = new ArrayList<>();

    //Creates a new NPC
    public static void createNPC(Player player, NPCType type)
    {
        //Creates the components of the NPC
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld(player.getWorld().getName())).getHandle();
        GameProfile profile = new GameProfile(UUID.randomUUID(), type.toString());

        //Spawns the npc
        EntityPlayer npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
        Location loc = player.getLocation();
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        //Byte secondOverlay = (0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40);
        //npc.getDataWatcher().set(new DataWatcherObject<>(16, DataWatcherRegistry.a), secondOverlay);

        //Sets the npcs skin
        String[] skinContent = getSkin(type);
        profile.getProperties().put("textures", new Property("textures", skinContent[0], skinContent[1]));
        //Adds the NPC to the list
        NPC.add(npc);

        //Sends the packets to the players
        addNPCPacket(npc);

        //save the npc
        NPCConfig.saveNPC(npc, type, loc.getYaw());
    }

    //Sends NPC-Packets to all Players on the server
    public static void addNPCPacket(EntityPlayer npc)
    {
        //Selects all players on the server
        for(Player player : Bukkit.getOnlinePlayers())
        {
            //Send the actual Packets
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            //connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
        }
    }

    public static void loadNPC(Location loc, GameProfile profile, NPCType type)
    {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld(loc.getWorld().getName())).getHandle();

        //Spawns the npc
        EntityPlayer npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
        Bukkit.getLogger().info(loc.getYaw() + " ");
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

        //Adds the NPC to the list
        NPC.add(npc);

        String[] skinContent = getSkin(type);
        profile.getProperties().put("textures", new Property("textures", skinContent[0], skinContent[1]));

        //Sends the packets to the players
        addNPCPacket(npc);
    }

    public static void removeNPC(Player player, EntityPlayer npc)
    {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
    }

    //Sends all NPCs to a joining player
    public static void addJoinPacket(Player player)
    {
        //Selects all NPCs
        for(EntityPlayer npc : NPC)
        {
            //Sends the actual Packets
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            //connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), npc.getDataWatcher(), true));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
        }
    }

    //Gets the skin data
    private static String[] getSkin(NPCType type)
    {
        //Gets the skin data from the config
        return new String[] {SkinConfig.getConfig().getString(type.toString() + ".texture"),
                SkinConfig.getConfig().getString(type.toString() + ".signature")};
    }

    public static List<EntityPlayer> getNPCs() {
        return NPC;
    }
}
