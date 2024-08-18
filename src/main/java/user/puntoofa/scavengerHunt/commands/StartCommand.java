package user.puntoofa.scavengerHunt.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import user.puntoofa.scavengerHunt.ScavengerHunt;

import java.util.HashSet;
import java.util.Set;

public class StartCommand implements CommandExecutor {
    private final ScavengerHunt plugin;

    public StartCommand(ScavengerHunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        plugin.scavStarted = true;
        Bukkit.getOnlinePlayers().forEach(player -> {
            plugin.finishedList.put(player, new HashSet<>());
        });

        Bukkit.broadcast(Component.text("Scavenger hunt has started!"));

        return true;
    }
}
