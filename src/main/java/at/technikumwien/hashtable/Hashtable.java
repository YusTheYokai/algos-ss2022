package at.technikumwien.hashtable;

public class Hashtable<T> {

    final static int P = 31;
    final static int M = 1000000007;

    private Object[] arr;

    // //////////////////////////////////////////////////////////////////////////
    // Init
    // //////////////////////////////////////////////////////////////////////////

    public Hashtable(int size) {
        arr = new Object[size];
    }

    // //////////////////////////////////////////////////////////////////////////
    // Methoden
    // //////////////////////////////////////////////////////////////////////////

    public void add(String key, T t) {
        arr[key.hashCode() % arr.length] = t;
    }

    public T get(String key) {
        return (T) arr[key.hashCode() % arr.length];
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