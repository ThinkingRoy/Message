package net.mckitsu.message.Reload;

import net.mckitsu.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OnReload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage("重新讀取檔案");
        }

        Message.getPlugin().loadFile();

        return true;
    }
}
