package com.example.typeonediary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.navigation.NavigationView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

public class AddEntryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    TextView txtIn_entryDate;
    EditText numIn_bloodGlucose;
    EditText numIn_carbs;
    EditText numIn_insulinTaken;
    EditText txtIn_entryNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        /* HOOKS */
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        Button btn_dateSelect = (Button) findViewById(R.id.btn_dateSelect);
        Button btn_addEntry = (Button) findViewById(R.id.btn_addEntry);



        btn_dateSelect.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
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

        txtIn_entryDate = (TextView) findViewById(R.id.txtIn_entryDate);
        numIn_bloodGlucose = (EditText) findViewById(R.id.numIn_bloodGlucose);
        numIn_carbs = (EditText) findViewById(R.id.numIn_carbs);
        numIn_insulinTaken = (EditText) findViewById(R.id.numIn_insulinTaken);
        txtIn_entryNote = (EditText) findViewById(R.id.txtIn_entryNote);

        String entryID = UUID.randomUUID().toString();
        String date = txtIn_entryDate.getText().toString();
        Float bg = Float.valueOf(numIn_bloodGlucose.getText().toString());
        Float carbs = Float.valueOf(numIn_carbs.getText().toString());
        Float insulin = Float.valueOf(numIn_insulinTaken.getText().toString());
        String note = txtIn_entryNote.getText().toString();

        Entry newEntry = new Entry(entryID, date, bg, carbs, insulin, note);
        System.out.println("ID: " + newEntry.id);
        System.out.println("DATE: " + newEntry.date);
        System.out.println("BLOOD GLUCOSE: " + newEntry.bloodGlucose);
        System.out.println("CARBS: " + newEntry.carbs);
        System.out.println("INSULIN: " + newEntry.insulin);
        System.out.println("NOTE: " + newEntry.note);

        Toast.makeText(AddEntryActivity.this, "Successfully Saved Entry", Toast.LENGTH_SHORT).show();
    }
}