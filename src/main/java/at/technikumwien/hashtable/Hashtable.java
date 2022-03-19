package at.technikumwien.hashtable;

import javax.annotation.Nullable;

public class Hashtable<T> {

    static final int P = 31;
    static final int M = 1000000007;

    private KeyValue[] values;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Hashtable(int size) {
        values = new KeyValue[size];
        for (int i = 0; i < size; i++) {
            values[i] = new KeyValue();
        } 
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    public void add(String key, T t) {
        int hash = key.hashCode();
        KeyValue keyValue = values[hash % values.length];
        if (keyValue.getKey() != null) {
            keyValue = values[probing(hash) % values.length];
        }
        keyValue.setKey(key);
        keyValue.setValue(t);
    }

    private int probing(int hash) {
        int k = 1;
        while (values[(hash + k * k) % values.length].getValue() != null) {
            k++;
        }
        return hash + k * k;
    }

    @SuppressWarnings("unchecked")
    public T get(String key) {
        KeyValue keyValue = getKeyValue(key);
        if (keyValue == null) {
            return null;
        }

        return (T) keyValue.getValue();
    }

    @SuppressWarnings("unchecked")
    public T delete(String key) {
        KeyValue keyValue = getKeyValue(key);
        if (keyValue == null) {
            return null;
        }

        T deleted = (T) keyValue.getValue();
        keyValue.setKey(null);
        keyValue.setValue(null);
        return deleted;
    }

    private KeyValue getKeyValue(String key) {
        int hash = key.hashCode();
        int k = 0;
        KeyValue keyValue;
        while (!key.equals((keyValue = values[(hash + k * k) % values.length]).getKey()) 
                && (keyValue.getKey() != null || keyValue.isDeleted()))  {
            k++;
        }
        if (key.equals(keyValue.getKey())) {
            return keyValue;
        }
        return null;
    }

    private int hash(String key) {
        int h = 0;
        final char[] chars = key.toCharArray();
        long exp = 1;
        final int n = chars.length;
        for (int i = 0; i < n; i++) {
            h = (int)((h + (chars[i] - 'a' + 1) * exp) % M);
            exp = (exp * P) % M; 
        }
        return h;
    }
}
