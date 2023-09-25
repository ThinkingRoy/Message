package net.mckitsu.message.OnCommand;

import net.mckitsu.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OnCommandLeave implements CommandExecutor {



    Map<String, Character> typeMap = new HashMap<>();

    public OnCommandLeave() {
        this.typeMap.put("public", '#');
        this.typeMap.put("trade", '$');
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(args.length == 0) {
                p.sendMessage("function?");
                return true;
            }else if(args.length == 1) {
                p.sendMessage("channel?");
                return true;
            }else {
                String type = args[0];
                UUID uuid = p.getUniqueId();
                char prefix = this.typeMap.get(type);

                if(prefix == 0)
                    p.sendMessage("no function");

                Message.getPlugin().LeaveViaCommand(uuid, prefix);
                p.sendMessage("success");
            }
        }

        return true;
    }
}
