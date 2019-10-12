package com.example.countdowntimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.example.countdowntimer";
    @Override
    public void onReceive(Context context, Intent intent)
    {

        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(10000);
        //  Notification noti = new Notification.Builder(context).setContentTitle("CountDown").setContentText("Started").setSmallIcon(R.mipmap.ic_launcher).build();
        // NotificationManager manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        //noti.flags |= Notification.FLAG_AUTO_CANCEL;
        //manager.notify(0,noti);
//        Log.d("ACTION", "TestBroadcastReceiver");

        Toast.makeText(context, "Timer Up! Wake up! Wake up!", Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
    }
}
