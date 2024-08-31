package user.puntoofa.scavengerHunt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import user.puntoofa.scavengerHunt.ScavengerHunt;

public class PauseCommand implements CommandExecutor {
    private final ScavengerHunt plugin;

    public PauseCommand(ScavengerHunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!plugin.scavStarted) {
            sender.sendMessage("Can't pause scavenger hunt that hasn't started!");
            return false;
        }

        plugin.scavStarted = false;
        plugin.getLogger().info("Paused scavenger hunt");
        return true;
    }
}
