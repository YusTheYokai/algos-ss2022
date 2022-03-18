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
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    public void add(String key, T t) {
        int hash = key.hashCode();
        KeyValue value = values[hash % values.length];
        if (value.getValue() == null) {
            value.setValue(t);
        } else {
            values[probing(hash) % values.length].setValue(t);
        }
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
        return (T) getKeyValue(key).getValue();
    }

    @SuppressWarnings("unchecked")
    public T delete(String key) {
        KeyValue keyValue = getKeyValue(key);
        T deleted = (T) keyValue.getValue();
        keyValue.setValue(null);
        return deleted;
    }

    private KeyValue getKeyValue(String key) {
        int hash = key.hashCode();
        int k = 0;
        KeyValue keyValue;
        while ((keyValue = values[(hash + k * k) % values.length]) != null && 
                !keyValue.getKey().equals(key)) {
            k++;
        }
        return keyValue;
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
