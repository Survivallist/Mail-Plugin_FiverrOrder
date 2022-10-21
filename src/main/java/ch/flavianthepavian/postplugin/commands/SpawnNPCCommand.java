package ch.flavianthepavian.postplugin.commands;

import ch.flavianthepavian.postplugin.config.Config;
import ch.flavianthepavian.postplugin.npcs.NPCType;
import ch.flavianthepavian.postplugin.npcs.Spawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnNPCCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player))
        {
            commandSender.sendMessage(Config.getErrorConsoleMessage());
            return false;
        }
        else if (!(commandSender.isOp()))
        {
            return false;
        }
        Player player = (Player) commandSender;
        Spawner.createNPC(player, NPCType.valueOf(args[0].toUpperCase()));
        return true;
    }
}
