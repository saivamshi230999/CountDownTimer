package com.example.countdowntimer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.Settings;

import androidx.annotation.Nullable;

public class MyService extends Service {
private MediaPlayer mediaPlayer;
private Vibrator vib;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        vib.vibrate(10000);
        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.setLooping(true);

            mediaPlayer.start();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        vib.cancel();
    }
}
