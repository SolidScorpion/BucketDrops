package apripachkin.com.bucketdrops.service;

import android.app.IntentService;
import android.content.Intent;

import apripachkin.com.bucketdrops.beans.Drop;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class NotificationService extends IntentService {

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Drop> completed = realm.where(Drop.class).equalTo("completed", false).findAll();
            for (Drop drop : completed) {
                if (isNotificationNeeded(drop.getAdded(), drop.getWhen())) {

                }
            }
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    private boolean isNotificationNeeded(long added, long when) {
        long currentTime = System.currentTimeMillis();
        if (currentTime > when) {
            return false;
        }
        long difference = (long) (0.9 * (when - added));
        return currentTime > (added + difference);
    }

}
