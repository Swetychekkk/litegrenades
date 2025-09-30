package net.swetychek.litegrenades.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class litegrenadesTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("help", "give", "reload", "restore");
        }else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            return Arrays.asList("lightgrenades:grenade", "lightgrenades:tier_two");
        } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            return Arrays.asList("0", "5", "10", "20", "40", "80", "120");
        }
        return new ArrayList<>();
    }
}
