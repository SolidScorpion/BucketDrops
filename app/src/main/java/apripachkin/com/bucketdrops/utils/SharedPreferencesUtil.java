package apripachkin.com.bucketdrops.utils;

import android.content.Context;
import android.content.SharedPreferences;

import apripachkin.com.bucketdrops.Filter;

/**
 * Created by root on 18.04.16.
 */
public class SharedPreferencesUtil {
    public static final String FILTER = "FILTER";
    private SharedPreferences preferences;

    public SharedPreferencesUtil(Context context) {
        preferences = context.getSharedPreferences(FILTER, context.MODE_PRIVATE);
    }

    public void save(int option) {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(FILTER, option);
        edit.apply();
    }

    public int load() {
        return preferences.getInt(FILTER, Filter.NONE);
    }
}
