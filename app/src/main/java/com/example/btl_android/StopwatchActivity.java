package com.example.btl_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class StopwatchActivity extends AppCompatActivity {

    Button btnReset,btnStart;
    TextView txtTimer;
    boolean timerStarted = false;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_stopwatch);

        ConstraintLayout cl = findViewById(R.id.stopwatchLayout);
        AnimationDrawable ad = (AnimationDrawable) cl.getBackground();
        ad.setEnterFadeDuration(2500);
        ad.setExitFadeDuration(5000);
        ad.start();

        btnStart = (Button)findViewById(R.id.btnStart);
        btnReset = (Button)findViewById(R.id.btnReset);
        txtTimer = (TextView)findViewById(R.id.txtTimer);

        timer = new Timer();
    }

    public void resetTapped(View view){
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        resetAlert.setTitle("Reset Timer");
        resetAlert.setMessage("Are you sure you want to reset the timer?");
        resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(timerTask != null)
                {
                    timerTask.cancel();
                    btnStart.setText("START");
                    time = 0.0;
                    timerStarted = false;
                    txtTimer.setText(formatTime(0,0,0));

                }
            }
        });

        resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //do nothing
            }
        });

        resetAlert.show();


    }

    public void startStopTapped(View view) {
        if(!timerStarted) {
            timerStarted = true;
            btnStart.setText("STOP");

            startTimer();
        } else {
            timerStarted = false;
            btnStart.setText("START");

            timerTask.cancel();
        }
    }

    private void startTimer()
    {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        txtTimer.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }

    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = (rounded % 86400) / 3600;

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }
}

