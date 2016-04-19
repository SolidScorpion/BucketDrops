package apripachkin.com.bucketdrops.mainscreen;

import android.content.Context;

import apripachkin.com.bucketdrops.Filter;
import apripachkin.com.bucketdrops.beans.Drop;
import apripachkin.com.bucketdrops.utils.SharedPreferencesUtil;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by root on 11.04.16.
 */
public class MainScreenPresenterImpl implements MainScreenPresenter {
    private MainScreenView view;
    private SharedPreferencesUtil sharedPreferencesUtil;
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
        sharedPreferencesUtil = new SharedPreferencesUtil((Context) view);
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
        load();
        return data;
    }

    @Override
    public Realm getDB() {
        if (realmDb == null) {
            realmDb = Realm.getDefaultInstance();
        }
        return realmDb;
    }

    public void sort(int state) {
        switch (state) {
            case Filter.NONE:
                sharedPreferencesUtil.save(Filter.NONE);
                data = realmDb.where(Drop.class).findAllAsync();
                break;
            case Filter.LEAST_TIME_LEFT:
                sharedPreferencesUtil.save(Filter.LEAST_TIME_LEFT);
                data = realmDb.where(Drop.class).findAllSortedAsync("when");
                break;
            case Filter.MOST_TIME_LEFT:
                sharedPreferencesUtil.save(Filter.MOST_TIME_LEFT);
                data = realmDb.where(Drop.class).findAllSortedAsync("when", Sort.DESCENDING);
                break;
            case Filter.COMPLETED:
                sharedPreferencesUtil.save(Filter.COMPLETED);
                data = realmDb.where(Drop.class).equalTo("isCompleted", true).findAllAsync();
                break;
            case Filter.INCOMPLETE:
                sharedPreferencesUtil.save(Filter.INCOMPLETE);
                data = realmDb.where(Drop.class).equalTo("isCompleted", false).findAllAsync();
                break;
        }
        data.addChangeListener(changeListener);
    }

    private void load() {
        sort(sharedPreferencesUtil.load());
    }

}
