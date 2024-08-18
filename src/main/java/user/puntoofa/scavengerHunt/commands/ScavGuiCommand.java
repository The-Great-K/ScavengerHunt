package user.puntoofa.scavengerHunt.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import user.puntoofa.scavengerHunt.ScavengerHunt;

public class ScavGuiCommand implements CommandExecutor {
    private final ScavengerHunt plugin;
    public ScavGuiCommand(ScavengerHunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return false;
        }
        if (!plugin.scavStarted) {
            sender.sendMessage("Scavenger hunt has not yet started!");
            return false;
        }

        Inventory inventory = Bukkit.createInventory(player, 9 * 3, Component.text("Scavenger List", NamedTextColor.DARK_RED));

        plugin.scavengerList.forEach((item, slot) -> inventory.setItem(slot, new ItemStack(item)));

        plugin.finishedList.get(player).forEach(value -> inventory.setItem(value, new ItemStack(Material.BARRIER)));

        player.openInventory(inventory);
        player.setMetadata("OpenedScavMenu", new FixedMetadataValue(ScavengerHunt.getInstance(), inventory));

        return true;
    }
}
