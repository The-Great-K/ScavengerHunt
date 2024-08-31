package user.puntoofa.scavengerHunt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import user.puntoofa.scavengerHunt.ScavengerHunt;

public class RestartCommand implements CommandExecutor {
    private ScavengerHunt plugin;

    public RestartCommand(ScavengerHunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!plugin.scavStarted) {
            sender.sendMessage("Can't restart scavenger hunt that has not yet started");
            return false;
        }

        plugin.scavStarted = false;
        plugin.finishedList.clear();
        plugin.scavengerList.clear();
        plugin.getLogger().info("Restarted scavenger hunt");

        return true;
    }
}
