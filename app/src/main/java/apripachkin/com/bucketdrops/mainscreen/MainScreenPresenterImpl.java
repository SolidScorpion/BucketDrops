package apripachkin.com.bucketdrops.mainscreen;

import apripachkin.com.bucketdrops.beans.Drop;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by root on 11.04.16.
 */
public class MainScreenPresenterImpl implements MainScreenPresenter {
    public static final String TAG = MainScreenPresenter.class.getName();
    private static final int ASCENDING = 0;
    private static final int DESCENDING = 1;
    private static final int COMPLETED = 2;
    private static final int INCOMPLETED = 3;
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
        data.addChangeListener(changeListener);
    }

    @Override
    public void onStop() {
        data.removeChangeListener(changeListener);
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

    private void sort(int state) {
        switch (state) {
            case ASCENDING:
                data = realmDb.where(Drop.class).findAllSortedAsync("when");
                break;
            case DESCENDING:
                data = realmDb.where(Drop.class).findAllSortedAsync("when", Sort.DESCENDING);
                break;
            case COMPLETED:
                data = realmDb.where(Drop.class).equalTo("completed", true).findAllAsync();
                break;
            case INCOMPLETED:
                data = realmDb.where(Drop.class).equalTo("completed", false).findAllAsync();
                break;
        }
        data.addChangeListener(changeListener);
    }

    @Override
    public void sortAscending() {
        sort(ASCENDING);
    }

    @Override
    public void sortDescending() {
        sort(DESCENDING);
    }

    @Override
    public void sortComplete() {
        sort(COMPLETED);
    }

    @Override
    public void sortIncomplete() {
        sort(INCOMPLETED);
    }
}
