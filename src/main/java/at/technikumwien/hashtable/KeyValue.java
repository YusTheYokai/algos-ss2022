package at.technikumwien.hashtable;

public class KeyValue<K, V> {

    private K key;
    private V value;
    private boolean deleted;

    // //////////////////////////////////////////////////////////////////////////
    // Getter und Setter
    // //////////////////////////////////////////////////////////////////////////

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }
    
    public V getValue() {
        return value;
    }

    public void setValue(V v) {
        this.value = v;
        deleted = v == null;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
