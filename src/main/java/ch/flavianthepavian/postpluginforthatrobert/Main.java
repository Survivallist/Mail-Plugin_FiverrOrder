package ch.flavianthepavian.postpluginforthatrobert;

import ch.flavianthepavian.postpluginforthatrobert.commands.SpawnNPCCommand;
import ch.flavianthepavian.postpluginforthatrobert.config.ConfigManager;
import ch.flavianthepavian.postpluginforthatrobert.config.NPCConfig;
import ch.flavianthepavian.postpluginforthatrobert.config.SkinConfig;
import ch.flavianthepavian.postpluginforthatrobert.events.PacketReader;
import ch.flavianthepavian.postpluginforthatrobert.invs.AngelaPetra;
import ch.flavianthepavian.postpluginforthatrobert.items.Grusskarte;
import ch.flavianthepavian.postpluginforthatrobert.listeners.GrusskarteListener;
import ch.flavianthepavian.postpluginforthatrobert.listeners.InteractListener;
import ch.flavianthepavian.postpluginforthatrobert.listeners.InvListener;
import ch.flavianthepavian.postpluginforthatrobert.npcs.Listener;
import ch.flavianthepavian.postpluginforthatrobert.npcs.NPCType;
import ch.flavianthepavian.postpluginforthatrobert.npcs.Spawner;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.UUID;


public final class Main extends JavaPlugin {

    static Main main;

    @Override
    public void onEnable() {
        // Plugin startup logic
        main = this;
        registerListeners();
        registerCommands();
        saveConfig();
        ConfigManager.createConfigs();
        ConfigManager.reload();
        loadNPCs();
        if(!Bukkit.getOnlinePlayers().isEmpty())
        {
            PacketReader reader = new PacketReader();
            reader.injectAll();
        }
    }

    @Override
    public void onDisable() {
        if(!Bukkit.getOnlinePlayers().isEmpty())
        {
            try
            {
                PacketReader reader = new PacketReader();
                reader.uninjectAll();
            }catch (NoClassDefFoundError e)
            {

            }

            for (Player player : Bukkit.getOnlinePlayers())
            {
                for (EntityPlayer npc : Spawner.getNPCs())
                {
                    Spawner.removeNPC(player, npc);
                }
            }
        }
    }

    void registerListeners()
    {
        getServer().getPluginManager().registerEvents(new Listener(), this);
        getServer().getPluginManager().registerEvents(new InvListener(), this);
        getServer().getPluginManager().registerEvents(new GrusskarteListener(), this);
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
    }

    void registerCommands()
    {
        getCommand("npc").setExecutor(new SpawnNPCCommand());
    }

    public static Main getInstance()
    {
        return main;
    }

    public void loadNPCs()
    {
        if(!NPCConfig.getConfig().contains("npcs"))
        {
            return;
        }
        for(String type : NPCConfig.getConfig().getConfigurationSection("npcs").getKeys(false))
        {
           for (String npc : NPCConfig.getConfig().getConfigurationSection("npcs." + type.toString().toUpperCase()).getKeys(false))
           {
               float x = (float) NPCConfig.getConfig().getDouble("npcs." + type + "." + npc + ".x");
               float y = (float) NPCConfig.getConfig().getDouble("npcs." + type + "." + npc + ".y");
               float z = (float) NPCConfig.getConfig().getDouble("npcs." + type + "." + npc + ".z");
               float yaw = (float) NPCConfig.getConfig().getDouble("npcs." + type + "." + npc + ".yaw");
               float pitch = (float) NPCConfig.getConfig().getDouble("npcs." + type + "." + npc + ".pitch");
               String world = NPCConfig.getConfig().getString("npcs." + type + "." + npc + ".world");

               Location location = new Location(Bukkit.getWorld(world), x, y, z);
               location.setYaw(yaw);
               location.setPitch(pitch);

               GameProfile profile = new GameProfile(UUID.randomUUID(), type);

               Spawner.loadNPC(location, profile, NPCType.valueOf(type));
           }
        }
    }
}
