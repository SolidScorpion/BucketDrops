package apripachkin.com.bucketdrops.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import apripachkin.com.bucketdrops.extras.Util;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Util.scheduleAlarm(context);
    }
}
