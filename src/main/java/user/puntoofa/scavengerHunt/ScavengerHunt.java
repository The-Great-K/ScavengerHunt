package user.puntoofa.scavengerHunt;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import user.puntoofa.scavengerHunt.commands.*;
import user.puntoofa.scavengerHunt.gui.ScavGuiListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"DataFlowIssue", "ConstantValue"})
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

        getCommand("sstart").setExecutor(new StartCommand(this));
        getCommand("spause").setExecutor(new PauseCommand(this));
        getCommand("srestart").setExecutor(new RestartCommand(this));
        getCommand("scav").setExecutor(new ScavGuiCommand(this));
        getCommand("additem").setExecutor(new AddItemCommand(this));
        getCommand("removeitem").setExecutor(new RemoveItemCommand(this));

        getLogger().info("Scavenger Hunt plugin successfully loaded!");
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        finishedList.get(player).clear();
    }

    @EventHandler
    public void onPlayerInventoryChange(PlayerInventorySlotChangeEvent event) {
        if (!scavStarted) return;

        Player player = event.getPlayer();
        Material item = null;
        if (event.getNewItemStack() != null) {
            item = event.getNewItemStack().getType();
        } else {
            return;
        }

        if (scavengerList.containsKey(event.getOldItemStack().getType())) {
            finishedList.get(player).remove(scavengerList.get(event.getOldItemStack().getType()));
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
