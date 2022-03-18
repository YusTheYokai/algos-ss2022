package at.technikumwien.hashtable;

public class KeyValue {

    private String key;
    private Object value;
    private boolean deleted;

    // //////////////////////////////////////////////////////////////////////////
    // Getter und Setter
    // //////////////////////////////////////////////////////////////////////////

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
    public Object getValue() {
        return value;
    }

    public void setValue(Object v) {
        this.value = v;
        deleted = v == null;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
