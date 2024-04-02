package ca.awoo.playerstorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileStore implements Store {

    private final File dataFolder;

    public FileStore(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    @Override
    public PlayerData load(String playerId, String plugin) throws Exception {
        File playerFolder = new File(dataFolder, playerId);
        if (!playerFolder.exists()) {
            return new PlayerData();
        }
        File pluginFile = new File(playerFolder, plugin + ".dat");
        if (!pluginFile.exists()) {
            return new PlayerData();
        }
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(pluginFile));
        PlayerData data = (PlayerData) ois.readObject();
        ois.close();
        return data;
    }

    @Override
    public void save(String playerId, String plugin, PlayerData data) throws Exception {
        File playerFolder = new File(dataFolder, playerId);
        if (!playerFolder.exists()) {
            playerFolder.mkdirs();
        }
        File pluginFile = new File(playerFolder, plugin + ".dat");
        data.setLastSave(System.currentTimeMillis());
        data.setModified(false);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pluginFile));
        oos.writeObject(data);
        oos.close();
    }

    
}
