package apripachkin.com.bucketdrops.mainscreen;

import android.support.v4.app.FragmentManager;

import apripachkin.com.bucketdrops.beans.Drop;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by root on 11.04.16.
 */
public interface MainScreenPresenter {
    void buttonClick(FragmentManager manager);
    void onStart();
    void onStop();
    RealmResults<Drop> getData();
    Realm getDB();
}
