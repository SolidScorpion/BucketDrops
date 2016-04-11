package apripachkin.com.bucketdrops.mainscreen;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by root on 11.04.16.
 */
public class MainScreenPresenterImpl implements MainScreenPresenter {
    private MainScreenView view;

    public MainScreenPresenterImpl(MainScreenView view) {
        this.view = view;
    }

    @Override
    public void buttonClick() {
        Toast.makeText((Context) view, "button was clicked!", Toast.LENGTH_SHORT).show();
    }
}
