package apripachkin.com.bucketdrops.mainscreen;

import apripachkin.com.bucketdrops.beans.Drop;
import io.realm.RealmResults;

/**
 * Created by root on 11.04.16.
 */
public interface MainScreenView {
    void showData(RealmResults<Drop> data);
}
