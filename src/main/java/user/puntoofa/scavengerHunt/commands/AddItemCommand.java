package user.puntoofa.scavengerHunt.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import user.puntoofa.scavengerHunt.ScavengerHunt;

public class AddItemCommand implements CommandExecutor {
    private final ScavengerHunt plugin;

    public AddItemCommand(ScavengerHunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (plugin.scavStarted) {
            sender.sendMessage("Scavenger hunt has already started!");
            return false;
        }

        int slot;

        try {
            slot = Integer.parseInt(args[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            sender.sendMessage("First argument must be a number!");
            return false;
        }
        if (slot < 1 || slot > 27 || plugin.scavengerList.containsValue(slot)) {
            sender.sendMessage("First argument must be a number between 1 and 27, that's not already taken!");
            return false;
        }

        if (!(sender instanceof Player)) {
            if (!(args.length < 2) && !args[1].isEmpty()) {
                sender.sendMessage("Please specify an item");
                return false;
            } else {
                try {
                    plugin.scavengerList.put(Material.valueOf(args[1].toUpperCase()), slot - 1);
                    sender.sendMessage("Added " + args[1] + " to scavenger list, in slot " + slot);
                    return true;
                } catch (IllegalArgumentException e) {
                    sender.sendMessage("Invalid item name!");
                    return false;
                }
            }
        }

        Player player;
        player = (Player) sender;
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            sender.sendMessage("You need to hold an item!");
            return false;
        } else if (plugin.scavengerList.containsKey(player.getInventory().getItemInMainHand().getType())) {
            sender.sendMessage("You cannot put two of the same items in list");
            return false;
        } else {
            plugin.scavengerList.put(player.getInventory().getItemInMainHand().getType(), Integer.parseInt(args[0]) - 1);
            sender.sendMessage("Added " + player.getInventory().getItemInMainHand().getType().name() + " to scavenger list in slot " + slot);
            return true;
        }
    }
}
