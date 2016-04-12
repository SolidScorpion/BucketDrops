package apripachkin.com.bucketdrops.mainscreen;

import android.support.v4.app.FragmentManager;

import apripachkin.com.bucketdrops.fragments.AddDataDialogFragment.DialogAdd;

/**
 * Created by root on 11.04.16.
 */
public class MainScreenPresenterImpl implements MainScreenPresenter {
    private MainScreenView view;

    public MainScreenPresenterImpl(MainScreenView view) {
        this.view = view;
    }

    @Override
    public void buttonClick(FragmentManager manager) {
        DialogAdd dialogAdd = new DialogAdd();
        dialogAdd.show(manager, "AddFragment");
    }
}
