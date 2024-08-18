package user.puntoofa.scavengerHunt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import user.puntoofa.scavengerHunt.ScavengerHunt;

public class RemoveItemCommand implements CommandExecutor {
    private final ScavengerHunt plugin;

    public RemoveItemCommand(ScavengerHunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        int slot;
        try {
            slot = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Please enter a number!");
            return false;
        }

        if (slot < 1 || slot > 27) {
            sender.sendMessage("Please enter a number between 1 and 27!");
            return false;
        }

        var iterator = plugin.scavengerList.entrySet().iterator();
        while (iterator.hasNext()) {
            var entry = iterator.next();
            if (entry.getValue().equals(--slot)) {
                sender.sendMessage("Removed item " + entry.getKey().name() + " from scavenger list");
                iterator.remove();
                return true;
            }
        }

        return false;
    }
}
