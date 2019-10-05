package com.example.countdowntimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import static android.widget.Toast.LENGTH_LONG;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Done with the counter", LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(10000);
      //  Notification noti = new Notification.Builder(context).setContentTitle("CountDown").setContentText("Started").setSmallIcon(R.mipmap.ic_launcher).build();
       // NotificationManager manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        //noti.flags |= Notification.FLAG_AUTO_CANCEL;
        //manager.notify(0,noti);


        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r = RingtoneManager.getRingtone(context,notification);
        r.play();

    }

}
