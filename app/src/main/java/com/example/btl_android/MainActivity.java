package com.example.btl_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainRecyclerViewInterface {

    ArrayList<MainCardModel> models = new ArrayList<>();

    int[] icons = {R.drawable.globe, R.drawable.stopwatch, R.drawable.countdown};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        ConstraintLayout cl = findViewById(R.id.mainLayout);
        AnimationDrawable ad = (AnimationDrawable) cl.getBackground();
        ad.setEnterFadeDuration(2500);
        ad.setExitFadeDuration(5000);
        ad.start();

        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);

        setupModel();

        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(this, models, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setupModel() {
        String[] functions = getResources().getStringArray(R.array.functions);

        for (int i = 0; i < functions.length; i++)
        {
            models.add(new MainCardModel(icons[i], functions[i]));
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent i;
        String func = models.get(position).getFunctions();
        
        if (func.equals("Giờ Thế Giới")) {
            i = new Intent(MainActivity.this, GlobalTimeActivity.class);
            startActivity(i);
        }
        else if (func.equals("Bấm Giờ")) {
            i = new Intent(MainActivity.this, StopwatchActivity.class);
            startActivity(i);
        }
        else if (func.equals("Đếm Ngược")) {
            i = new Intent(MainActivity.this, CountdownActivity.class);
            startActivity(i);
        }
    }
}