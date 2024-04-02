package ca.awoo.playerstorage;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerStorage extends JavaPlugin implements Listener{

    public static PlayerStorage getPlayerStorage(){
        return (PlayerStorage)Bukkit.getPluginManager().getPlugin("PlayerStorage");
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        save();
    }

    private final Store store = new FileStore(getDataFolder());

    private final HashMap<String, HashMap<String, PlayerData>> cache = new HashMap<>();

    private void save(){
        for (String playerId : cache.keySet()) {
            HashMap<String, PlayerData> playerData = cache.get(playerId);
            for (String plugin : playerData.keySet()) {
                PlayerData data = playerData.get(plugin);
                if (data.isModified()) {
                    try {
                        store.save(playerId, plugin, data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onWorldSave(WorldSaveEvent event) {
        save();
    }

    public PlayerData getData(Player player, Plugin plugin){
        String playerId = player.getUniqueId().toString();
        String pluginName = plugin.getDescription().getName();
        if (!cache.containsKey(playerId)) {
            cache.put(playerId, new HashMap<>());
        }
        HashMap<String, PlayerData> playerData = cache.get(playerId);
        if (!playerData.containsKey(pluginName)) {
            try {
                playerData.put(pluginName, store.load(playerId, pluginName));
            } catch (Exception e) {
                e.printStackTrace();
                playerData.put(pluginName, new PlayerData());
            }
        }
        return playerData.get(pluginName);
    }
}
