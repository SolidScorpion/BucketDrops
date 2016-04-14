package apripachkin.com.bucketdrops.utils;

import android.view.View;

import java.util.List;

/**
 * Created by root on 13.04.16.
 */
public class Util {
    public static void showViews(List<View> viewList) {
        for (View view : viewList) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViews(List<View> hideList) {
        for (View view : hideList) {
            view.setVisibility(View.GONE);
        }
    }
}
