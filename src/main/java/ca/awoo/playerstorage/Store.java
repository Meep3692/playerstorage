package ca.awoo.playerstorage;

public interface Store {
    public PlayerData load(String playerId, String plugin) throws Exception;
    public void save(String playerId, String plugin, PlayerData data) throws Exception;
}
