package apripachkin.com.bucketdrops.fragments.AddDataDialogFragment;

import android.content.Context;
import android.text.TextUtils;

import apripachkin.com.bucketdrops.beans.Drop;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by root on 12.04.16.
 */
public class AddDialogPresenterImpl implements AddDialogPresenter {
    private AddDialogView view;

    public AddDialogPresenterImpl(AddDialogView view) {
        this.view = view;
    }

    @Override
    public void closeDialog() {
        view.closeDialog();
    }

    //TODO: Process date
    @Override
    public void addItem(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Drop drop = new Drop(text, System.currentTimeMillis(), 0, false);
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder((Context) view);
        Realm.setDefaultConfiguration(builder.build());
        Realm defaultInstance = Realm.getDefaultInstance();
        defaultInstance.beginTransaction();
        defaultInstance.copyFromRealm(drop);
        defaultInstance.commitTransaction();
    }
}
