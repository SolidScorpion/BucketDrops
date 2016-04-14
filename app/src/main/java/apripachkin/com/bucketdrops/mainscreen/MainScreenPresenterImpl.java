package apripachkin.com.bucketdrops.mainscreen;

import apripachkin.com.bucketdrops.beans.Drop;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by root on 11.04.16.
 */
public class MainScreenPresenterImpl implements MainScreenPresenter {
    public static final String TAG = MainScreenPresenter.class.getName();
    private MainScreenView view;
    private Realm realmDb;
    private RealmResults<Drop> data;
    private RealmChangeListener changeListener = new RealmChangeListener() {
        @Override
        public void onChange() {
            view.showData(data);
        }
    };

    public MainScreenPresenterImpl(MainScreenView view) {
        this.view = view;
        realmDb = getDB();
    }


    @Override
    public void onStart() {
        realmDb.addChangeListener(changeListener);
    }

    @Override
    public void onStop() {
        realmDb.removeChangeListener(changeListener);
    }

    @Override
    public RealmResults<Drop> getData() {
        if (data == null) {
            data = realmDb.where(Drop.class).findAllAsync();
        }
        return data;
    }

    @Override
    public Realm getDB() {
        if (realmDb == null) {
            realmDb = Realm.getDefaultInstance();
        }
        return realmDb;
    }
}
