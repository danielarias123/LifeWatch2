package com.example.danarias.lifewatch2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;


public class createMedActivity extends ActionBarActivity implements OnClickListener {

    EditText medName;
    EditText quantity;
    EditText medNotes;
    EditText intervalWeek;
    EditText intervalDay;
    EditText intervalHour;
    NumberPicker weekPicker;
    NumberPicker dayPicker;
    NumberPicker hourPicker;
    Integer week;



    Button saveMedicationButton;
    Button backtoMedicationButton;

    private DbHelper myDb = new DbHelper(createMedActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_med);

        weekPicker = (NumberPicker) findViewById(R.id.weekPicker);
        dayPicker = (NumberPicker) findViewById(R.id.dayPicker);
        hourPicker = (NumberPicker) findViewById(R.id.hourPicker);

        dayPicker.setMaxValue(7);
        dayPicker.setMinValue(0);

        hourPicker.setMaxValue(24);
        hourPicker.setMinValue(0);

        saveMedicationButton = (Button) findViewById(R.id.saveMedButton);
        saveMedicationButton.setOnClickListener(this);


        backtoMedicationButton = (Button) findViewById(R.id.backtoMedButton);
        backtoMedicationButton.setOnClickListener(this);

        weekPicker.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub

                week = newVal;
            }
        });
    }

    public void onClick(View v) {

        switch(v.getId()){

            case R.id.saveMedButton:
                medName = (EditText) findViewById(R.id.mednameEditText);
                quantity = (EditText) findViewById(R.id.medquantityEditText);
                medNotes = (EditText) findViewById(R.id.medNotesEditText);
                intervalWeek = (EditText) findViewById(R.id.weekEditText);
                intervalDay = (EditText) findViewById(R.id.dayEditText);
                intervalHour = (EditText) findViewById(R.id.hourEditText);

                String medname = medName.getText().toString();
                String medquantity = quantity.getText().toString();
                String mednotes = medNotes.getText().toString();
                String medweek = intervalWeek.getText().toString();
                String medday = intervalDay.getText().toString();
                String medhour = intervalDay.getText().toString();


                Toast.makeText(getApplicationContext(),"Medication Created",Toast.LENGTH_SHORT).show();

                boolean invalid = false;
                if (medname.equals("")){
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Name Missing", Toast.LENGTH_SHORT).show();
                }else if(medquantity.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Quantity Missing", Toast.LENGTH_SHORT).show();
                }
                else if(medweek.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Weeks Missing", Toast.LENGTH_SHORT).show();
                }
                else if(medday.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Days Missing", Toast.LENGTH_SHORT).show();
                }
                else if(medhour.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Hours Missing", Toast.LENGTH_SHORT).show();
                }
                if(invalid == false){


                    addMedication(medname, medquantity,mednotes,medweek,medday,medhour);
                    Toast.makeText(getApplicationContext(),"Medication Created",Toast.LENGTH_SHORT).show();
                    Intent i_register = new Intent(createMedActivity.this, MedReminderActivity.class);
                    startActivity(i_register);
                    finish();
                }
                break;


            case R.id.backtoMedButton:
                Intent i = new Intent(createMedActivity.this, MedReminderActivity.class);
                startActivity(i);
                finish();
                break;


        }
    }




    public void addMedication(String medname, String medquantity, String mednotes, String medweek, String medday, String medhour ){

        SQLiteDatabase db = myDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("medname", medname);
        values.put("medquantity", medquantity);
        values.put("mednotes", mednotes);
        values.put("medweek", medweek);
        values.put("medday", medday);
        values.put("medhour", medhour);


        try{
            db.insert(DbHelper.MEDICATION_TABLE_NAME, null, values);
            Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }
        db.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_med, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(createMedActivity.this, MedReminderActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
