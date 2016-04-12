package apripachkin.com.bucketdrops.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 12.04.16.
 */
public class Drop extends RealmObject {
    private String what;
    @PrimaryKey
    private long added;
    private long when;
    private boolean isCompleted;

    public Drop() {
    }

    public Drop(String what, long added, long when, boolean isCompleted) {
        this.what = what;
        this.added = added;
        this.when = when;
        this.isCompleted = isCompleted;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public long getAdded() {
        return added;
    }

    public void setAdded(long added) {
        this.added = added;
    }

    public long getWhen() {
        return when;
    }

    public void setWhen(long when) {
        this.when = when;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
