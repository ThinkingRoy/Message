package net.mckitsu.message.OnCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements CommandExecutor {


    Map<String, CommandExecutor> functionMap = new HashMap<>();

    public CommandHandler() {
        this.functionMap.put("join", new OnCommandJoin());
        this.functionMap.put("leave", new OnCommandLeave());
    }




    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(args.length == 0){
                player.sendMessage("請輸入要使用的功能");
                return true;
            }else {
                label = args[0];
                int newArrayLength = args.length - 1;

                String[] newArgs = new String[newArrayLength];
                int newIndex = 0;

                for (int i = 0; i < args.length; i++){
                    if( i > 0){
                        newArgs[newIndex] = args[i];
                        newIndex++;
                    }
                }

                CommandExecutor commandExecutor = functionMap.get(args[0]);
                if(commandExecutor == null){
                    player.sendMessage("查無此功能");
                    return true;
                }else {
                    commandExecutor.onCommand(sender, command, label, newArgs);
                    return true;
                }
            }
        }

        return false;
    }
}
