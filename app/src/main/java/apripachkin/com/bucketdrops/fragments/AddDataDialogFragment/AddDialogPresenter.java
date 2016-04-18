package apripachkin.com.bucketdrops.fragments.AddDataDialogFragment;

import android.widget.DatePicker;

/**
 * Created by root on 12.04.16.
 */
public interface AddDialogPresenter {
    void closeDialog();

    void addItem(String text, DatePicker date);
}
