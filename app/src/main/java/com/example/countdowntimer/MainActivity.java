package com.example.countdowntimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private EditText mEditTextInput;
    private TextView mTextViewCountDown,tdate,tdate1;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Vibrator vib;
    private CountDownTimer mCountDownTimer;
    private MediaPlayer mediaPlayer,mp;
    private boolean mTimerRunning;
    private long firstvalue;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis,leftout;

    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    private long mEndTime;
    private Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
       t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               tdate = findViewById(R.id.date);
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                                String dateString = sdf.format(calendar.getTime());
                               // tdate.setText(dateString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonSet = findViewById(R.id.button_set);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(MainActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                mEditTextInput.setText("");
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
     //   mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
mEndTime = firstvalue+mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                tdate1 = findViewById(R.id.date1);
                alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

               // SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                //String dateString = "Current IST: " + sdf.format(calendar.getTime());
                TimeZone.setDefault(TimeZone.getTimeZone("IST"));
                SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
              //  System.out.println(f.format(new Date()));
                String dateString = "Current IST: " + f.format(new Date());
                tdate1.setText(dateString);



                long time;



/*

                    Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();
                int sec,min,hour;
                sec = (int) mEndTime/1000;
                min = sec/60;
                hour = min/60;
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, min);
                calendar.set(Calendar.SECOND,sec );
                    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

                    time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
                    if(System.currentTimeMillis()>time)
                    {
                        if (calendar.AM_PM == 0)
                            time = time + (1000*60*60*12);
                        else
                            time = time + (1000*60*60*24);
                    }
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);


*/


                    Toast.makeText(MainActivity.this, "Timer ON", Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();
              /*      //Calendar calendar1 = Calendar.getInstance();
                        int sec,min,hour;
                        sec = (int) mEndTime/1000;
                        min = sec/60;
                        hour = min/60;
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, min);
                    calendar.set(Calendar.SECOND,sec );
                        //calendar.setTimeInMillis(mEndTime);
                      //  time = (calendar.getTimeInMillis() - (calendar.setTimeInMillis()%6000)) ;*/
                int hours1 = (int) (mEndTime / 1000) / 3600;
                int minutes1 = (int) ((mEndTime / 1000) % 3600) / 60;
                int seconds1 = (int) (mEndTime / 1000) % 60;
                calendar.set(Calendar.HOUR_OF_DAY, hours1);
                calendar.set(Calendar.MINUTE, minutes1);
                calendar.set(Calendar.SECOND,seconds1 );
                Intent intent = new Intent(MainActivity.this,AlarmReceiver.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 pendingIntent = PendingIntent.getBroadcast(MainActivity.this,AlarmReceiver.REQUEST_CODE,intent,0);
             //   time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
             // time = calendar.getTimeInMillis();
                //  mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

                long x  = mEndTime - 60000 ;
                alarmManager.set(AlarmManager.RTC_WAKEUP,mEndTime, pendingIntent);
              //  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,x, 60000,pendingIntent);
              //  Toast.makeText(MainActivity.this, "Timer ON", Toast.LENGTH_SHORT).show();

                tdate.setVisibility(View.VISIBLE);
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
       // stopService(new Intent(MainActivity.this,MyService.class));
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);


    }

    private void updateWatchInterface() {

        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.INVISIBLE);

            mButtonStartPause.setText("Pause");
        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("Start");

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);

            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);

            }

            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //stopService(new Intent(MainActivity.this,MyService.class));

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        firstvalue = System.currentTimeMillis();
        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
             leftout   = mTimeLeftInMillis;
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;

                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();

            } else {
                startTimer();
            }
        }
    }
}