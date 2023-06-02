package com.example.typeonediary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;
import java.util.UUID;
import java.nio.file.Files;

public class AddEntryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    ReaderWriter rw = new ReaderWriter();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    TextView txtIn_entryTime;

    TextView txtIn_entryDate;
    EditText numIn_bloodGlucose;
    EditText numIn_carbs;
    EditText numIn_insulinTaken;
    EditText txtIn_entryNote;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        /* HOOKS */
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        txtIn_entryDate = findViewById(R.id.txtIn_entryDate);
        txtIn_entryTime = findViewById(R.id.txtIn_entryTime);
        Button btn_timeSelect = (Button) findViewById(R.id.btn_timeSelect);
        Button btn_dateSelect = (Button) findViewById(R.id.btn_dateSelect);
        Button btn_addEntry = (Button) findViewById(R.id.btn_addEntry);

        btn_dateSelect.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

    btn_timeSelect.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v) {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        }
    });

        btn_addEntry.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                addEntry();
            }
        });


        /* TOOLBAR */
        setSupportActionBar(toolbar);

        /* NAV DRAWER MENU */
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_add);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String dateSelected = DateFormat.getDateInstance().format(calendar.getTime());
        txtIn_entryDate = (TextView) findViewById(R.id.txtIn_entryDate);
        txtIn_entryDate.setText(dateSelected);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        txtIn_entryTime.setText(hourOfDay + ":" + minute);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_home:
                intent = new Intent(AddEntryActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_add:
                break;

            case R.id.nav_entries:
                intent = new Intent(AddEntryActivity.this, ViewEntriesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_averages:
                intent = new Intent(AddEntryActivity.this, ViewAveragesActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_settings:
                intent = new Intent(AddEntryActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addEntry(){
        File file = new File(context.getFilesDir().getAbsolutePath() + "/" + "entries.json");
        try {
            //Check to see if file is created
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                //START - GRABBING ALL TEXT FROM INPUT FIELDS
                txtIn_entryDate = (TextView) findViewById(R.id.txtIn_entryDate);
                txtIn_entryTime = (TextView) findViewById(R.id.txtIn_entryTime);
                numIn_bloodGlucose = (EditText) findViewById(R.id.numIn_bloodGlucose);
                numIn_carbs = (EditText) findViewById(R.id.numIn_carbs);
                numIn_insulinTaken = (EditText) findViewById(R.id.numIn_insulinTaken);
                txtIn_entryNote = (EditText) findViewById(R.id.txtIn_entryNote);

                String entryID = UUID.randomUUID().toString();
                String date = txtIn_entryDate.getText().toString();
                String time = txtIn_entryTime.getText().toString();
                Float bg = Float.valueOf(numIn_bloodGlucose.getText().toString());
                Float carbs = Float.valueOf(numIn_carbs.getText().toString());
                Float insulin = Float.valueOf(numIn_insulinTaken.getText().toString());
                String note = txtIn_entryNote.getText().toString();
                //END - GRABBING ALL TEXT FROM INPUT FIELDS

                Entry newEntry = new Entry(entryID, date, time, bg, carbs, insulin, note);

                List<Entry> entries = rw.readEntries(file);

                if (!(entries == null)){
                    entries.add(newEntry);
                    rw.writeEntries(file, entries);
                } else {
                    List<Entry> newList = new ArrayList<>();
                    newList.add(newEntry);
                    rw.writeEntries(file, newList);
                }

                Toast.makeText(AddEntryActivity.this, "Entry has been added.", Toast.LENGTH_LONG).show();

                txtIn_entryDate.setText("_________");
                txtIn_entryTime.setText("_________");
                numIn_bloodGlucose.setText("");
                numIn_carbs.setText("");;
                numIn_insulinTaken.setText("");;
                txtIn_entryNote.setText("");;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}