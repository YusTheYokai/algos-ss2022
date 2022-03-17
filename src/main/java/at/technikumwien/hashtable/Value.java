package at.technikumwien.hashtable;

public class Value {

    private Object v;
    private boolean deleted;

    // //////////////////////////////////////////////////////////////////////////
    // Getter und Setter
    // //////////////////////////////////////////////////////////////////////////

    public Object getV() {
        return v;
    }

    public void setV(Object v) {
        this.v = v;
        deleted = v == null;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
