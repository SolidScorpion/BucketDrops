package apripachkin.com.bucketdrops.fragments.AddDataDialogFragment;

import android.text.TextUtils;
import android.widget.DatePicker;

import java.util.Calendar;

import apripachkin.com.bucketdrops.beans.Drop;
import io.realm.Realm;

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
    public void addItem(String text, DatePicker date) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonth());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Drop drop = new Drop(text, System.currentTimeMillis(), calendar.getTimeInMillis(), false);
        Realm defaultInstance = Realm.getDefaultInstance();
        defaultInstance.beginTransaction();
        defaultInstance.copyToRealm(drop);
        defaultInstance.commitTransaction();
        view.closeDialog();
    }
}
