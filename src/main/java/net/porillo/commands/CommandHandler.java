package net.porillo.commands;

import net.porillo.EntityManager;
import org.bukkit.command.CommandSender;

import java.util.*;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.GREEN;

/**
 * @author krinsdeath
 */
public class CommandHandler {

    private Map<String, Command> cmds = new HashMap<>();

    public CommandHandler(EntityManager plugin) {
        cmds.put("reload", new ReloadCommand(plugin));
        cmds.put("dump", new DumpCommand(plugin));
    }

    public void runCommand(CommandSender s, String l, String[] a) {
        if (a.length == 0 || this.cmds.get(a[0].toLowerCase()) == null) {
            this.showHelp(s, l);
            return;
        }

        List<String> args = new ArrayList<>(Arrays.asList(a));
        Command cmd = this.cmds.get(args.remove(0).toLowerCase());

        if (args.size() < cmd.getRequiredArgs()) {
            cmd.showHelp(s, l);
            return;
        }

        cmd.runCommand(s, args);
    }

    private void showHelp(CommandSender s, String l) {
        s.sendMessage(GREEN + "===" + GOLD + " EntityManager Help " + GREEN + "===");
        this.cmds.values().stream().filter(cmd -> cmd.checkPermission(s)).forEach(cmd -> cmd.showHelp(s, l));
    }
}