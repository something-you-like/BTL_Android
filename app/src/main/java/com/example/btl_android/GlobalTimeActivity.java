package com.example.btl_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class GlobalTimeActivity extends AppCompatActivity {
    Button AddBtn, DelAllBTN;
    Dialog dialog;
    Spinner spinner;
    TextClock textClock;
    RecyclerView recyclerView;
    GTRecyclerViewAdapter gtRecyclerViewAdapter;
    List<GTModel> list;
    GTDatabaseClass gtDatabaseClass;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ---------------- TẠO NỀN ĐA SẮC ----------------
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_global_time);

        ConstraintLayout cl = findViewById(R.id.globalTimeLayout);
        AnimationDrawable ad = (AnimationDrawable) cl.getBackground();
        ad.setEnterFadeDuration(2500);
        ad.setExitFadeDuration(5000);
        ad.start();
        // ------------------------------------------------

        AddBtn = findViewById(R.id.AddBTN);
        DelAllBTN = findViewById(R.id.deleteAllBTN);

        dialog = new Dialog(this);
        recyclerView = findViewById(R.id.timezoneRecyclerView);
        constraintLayout = findViewById(R.id.globalTimeLayout);

        list = new ArrayList<>();
        gtDatabaseClass = new GTDatabaseClass(this);
        getAllTimezoneFromDatabase();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gtRecyclerViewAdapter = new GTRecyclerViewAdapter(this, GlobalTimeActivity.this, list);
        recyclerView.setAdapter(gtRecyclerViewAdapter);

        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddNewGlobalTimeDialog();
            }
        });

        DelAllBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GTDatabaseClass gtDatabaseClass = new GTDatabaseClass(GlobalTimeActivity.this);
                gtDatabaseClass.deleteAllTimezone();

                recreate();
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    // ----------- XÓA MỘT MÚI GIỜ ------------
    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            GTModel item = gtRecyclerViewAdapter.getGtlist().get(position);

            gtRecyclerViewAdapter.removeTimezone(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar.make(constraintLayout, "Đã Xóa", Snackbar.LENGTH_LONG)
                    .setAction("Hoàn Tác", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gtRecyclerViewAdapter.restoreTimezone(item, position);
                    recyclerView.scrollToPosition(position);
                }
            }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);

                    if (!(event == DISMISS_EVENT_ACTION)) {
                        GTDatabaseClass db = new GTDatabaseClass(GlobalTimeActivity.this);
                        db.deleteSingleTimezone(item.getId());
                    }
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    };
    // ----------------------------------------

    protected void getAllTimezoneFromDatabase() { // LẤY TOÀN BỘ DỮ LIỆU TỪ DATABASE
        Cursor cursor = gtDatabaseClass.readAllData();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "Dữ Liệu Trống", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext()) {
                list.add(new GTModel(cursor.getString(0), cursor.getString(0)));
            }
        }
    }

    private void openAddNewGlobalTimeDialog() {
        // --------- HIỆN DIALOG -----------
        dialog.setContentView(R.layout.global_time_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // ---------------------------------

        Button exitBTN = dialog.findViewById(R.id.exitBTN);
        Button saveBTN = dialog.findViewById(R.id.saveBTN);
        spinner = (Spinner) dialog.findViewById(R.id.timezoneSpinner);

        // ---------------- THÊM MÚI GIỜ VÀO SPINNER ----------------
        String[] timezoneList = TimeZone.getAvailableIDs();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < timezoneList.length; i++) {
            list.add(timezoneList[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_selected, list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);

        spinner.setAdapter(adapter);
        // -----------------------------------------------------------

        exitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GTDatabaseClass gtdb = new GTDatabaseClass(GlobalTimeActivity.this);
                gtdb.addTimezone(spinner.getSelectedItem().toString());

                recreate();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}