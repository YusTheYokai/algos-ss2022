package at.technikumwien.hashtable;

public class Hashtable<K, V> {

    static final int P = 31;
    static final int M = 1000000007;

    private KeyValue<K, V>[] keyValues;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    public Hashtable(int size) {
        keyValues = new KeyValue[size];
        for (int i = 0; i < size; i++) {
            keyValues[i] = new KeyValue<>();
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    public void add(K key, V value) {
        int hash = key.hashCode();
        var keyValue = keyValues[Math.abs(hash) % keyValues.length];
        if (keyValue.getKey() != null) {
            keyValue = keyValues[Math.abs(probing(hash)) % keyValues.length];
        }
        keyValue.setKey(key);
        keyValue.setValue(value);
    }

    private int probing(int hash) {
        int k = 1;
        while (keyValues[(hash + k * k) % keyValues.length].getValue() != null) {
            k++;
        }
        return hash + k * k;
    }

    public V get(String key) {
        var keyValue = getKeyValue(key);
        if (keyValue == null) {
            return null;
        }

        return keyValue.getValue();
    }

    public V delete(String key) {
        var keyValue = getKeyValue(key);
        if (keyValue == null) {
            return null;
        }

        V deleted = keyValue.getValue();
        keyValue.setKey(null);
        keyValue.setValue(null);
        return deleted;
    }

    private KeyValue<K, V> getKeyValue(String key) {
        int hash = key.hashCode();
        int k = 0;
        KeyValue<K, V> keyValue;
        while (!key.equals((keyValue = keyValues[Math.abs(hash + k * k) % keyValues.length]).getKey()) 
                && (keyValue.getKey() != null || keyValue.isDeleted()))  {
            k++;
        }
        if (key.equals(keyValue.getKey())) {
            return keyValue;
        }
        return null;
    }
}
