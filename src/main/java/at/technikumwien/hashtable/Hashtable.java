package at.technikumwien.hashtable;

public class Hashtable<T> {

    static final int P = 31;
    static final int M = 1000000007;

    private Value[] values;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Hashtable(int size) {
        values = new Value[size];
        for (int i = 0; i < size; i++) {
            values[i] = new Value();
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    public void add(String key, T t) {
        values[key.hashCode() % values.length].setV(t);
    }

    @SuppressWarnings("unchecked")
    public T get(String key) {
        return (T) getValue(key).getV();
    }

    public T delete(String key) {
        T deleted = get(key);
        Value value = getValue(key);
        value.setV(null);
        return deleted;
    }

    private Value getValue(String key) {
        return values[key.hashCode() % values.length];
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
