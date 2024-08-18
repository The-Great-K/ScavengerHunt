package user.puntoofa.scavengerHunt;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import user.puntoofa.scavengerHunt.commands.AddItemCommand;
import user.puntoofa.scavengerHunt.commands.ScavGuiCommand;
import user.puntoofa.scavengerHunt.commands.StartCommand;
import user.puntoofa.scavengerHunt.gui.ScavGuiListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class ScavengerHunt extends JavaPlugin implements Listener {
    public boolean scavStarted = false;

    public HashMap<Material, Integer> scavengerList = new HashMap<>();
    public HashMap<Player, Set<Integer>> finishedList = new HashMap<>();

    public static ScavengerHunt getInstance() {
        return JavaPlugin.getPlugin(ScavengerHunt.class);
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ScavGuiListener(), this);
        getServer().getPluginManager().registerEvents(this, this);

        Objects.requireNonNull(getCommand("start")).setExecutor(new StartCommand(this));
        Objects.requireNonNull(getCommand("scav")).setExecutor(new ScavGuiCommand(this));
        Objects.requireNonNull(getCommand("additem")).setExecutor(new AddItemCommand(this));

        getLogger().info("Scavenger Hunt plugin successfully loaded!");
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPlayerInventoryChange(PlayerInventorySlotChangeEvent event) {
        if (!scavStarted) return;

        Player player = event.getPlayer();
        Material item = null;
        if (player.getInventory().getItem(event.getSlot()) != null) {
            item = player.getInventory().getItem(event.getSlot()).getType();
        } else {
            return;
        }
        if (scavengerList.containsKey(item) && !finishedList.get(player).contains(scavengerList.get(item))) {
            finishedList.get(player).add(scavengerList.get(item));
            Bukkit.broadcast(Component.text(
                    player.getName() + " has found " + item.name(), NamedTextColor.DARK_RED));
        }

        if (scavengerList.size() == finishedList.get(player).size()) {
            Bukkit.broadcast(Component.text(player.getName() + " has completed the scavenger hunt!", NamedTextColor.DARK_RED));
            scavStarted = false;
            scavengerList.clear();
            finishedList.clear();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!scavStarted) return;
        Player player = event.getPlayer();

        if (finishedList.containsKey(player)) return;

        finishedList.put(player, new HashSet<>());
    }
}
