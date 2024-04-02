package ca.awoo.playerstorage;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerData implements Serializable, Map<String, Serializable> {
    private final HashMap<String, Serializable> data = new HashMap<>();
    private boolean modified = false;
    private long lastSave = 0;

    public long getLastSave() {
        return lastSave;
    }

    public void setLastSave(long lastSave) {
        this.lastSave = lastSave;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public Serializable get(Object key) {
        return data.get(key);
    }

    @Override
    public Serializable put(String key, Serializable value) {
        modified = true;
        return data.put(key, value);
    }

    @Override
    public Serializable remove(Object key) {
        modified = true;
        return data.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Serializable> m) {
        data.putAll(m);
        modified = true;
    }

    @Override
    public Set<String> keySet() {
        return data.keySet();
    }

    @Override
    public Collection<Serializable> values() {
        return data.values();
    }

    @Override
    public Set<Entry<String, Serializable>> entrySet() {
        return data.entrySet();
    }

    @Override
    public void clear() {
        data.clear();
        modified = true;
    }
}
