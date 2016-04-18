package apripachkin.com.bucketdrops.mainscreen;

import apripachkin.com.bucketdrops.beans.Drop;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by root on 11.04.16.
 */
public interface MainScreenPresenter {
    void onStart();
    void onStop();
    RealmResults<Drop> getData();
    Realm getDB();

    void sort(int filterOption);
}
