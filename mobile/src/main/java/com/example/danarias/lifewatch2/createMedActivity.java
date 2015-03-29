package com.example.danarias.lifewatch2;

import android.database.Cursor;
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


    NumberPicker numPicker;
    NumberPicker intervalPicker;
    Integer num;
    Integer interval;




    Button saveMedicationButton;
    Button backtoMedicationButton;

    String[] Intervals;

    private DbHelper myDb = new DbHelper(createMedActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_med);

        num =0;
        interval=0;



        numPicker = (NumberPicker) findViewById(R.id.numPicker);
        intervalPicker = (NumberPicker) findViewById(R.id.intervalPicker);




        Intervals=new String[3];
        Intervals[0]="Hour(s)";
        Intervals[1]="Day(s)";
        Intervals[2]="Week(s)";







        numPicker.setMaxValue(20);
        numPicker.setMinValue(0);
        numPicker.setWrapSelectorWheel(false);


        intervalPicker.setMaxValue(Intervals.length-1);
        intervalPicker.setMinValue(0);
        intervalPicker.setDisplayedValues(Intervals);
        intervalPicker.setWrapSelectorWheel(false);






        saveMedicationButton = (Button) findViewById(R.id.saveMedButton);
        saveMedicationButton.setOnClickListener(this);



        backtoMedicationButton = (Button) findViewById(R.id.backtoMedButton);
        backtoMedicationButton.setOnClickListener(this);


        numPicker.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub

                num = newVal;
            }
        });
        intervalPicker.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // TODO Auto-generated method stub

                interval = newVal;
            }
        });
    }

    public void onClick(View v) {

        switch(v.getId()){

            case R.id.saveMedButton:
                medName = (EditText) findViewById(R.id.mednameEditText);
                quantity = (EditText) findViewById(R.id.medquantityEditText);
                medNotes = (EditText) findViewById(R.id.medNotesEditText);

                String medname = medName.getText().toString();
                String medquantity = quantity.getText().toString();
                String mednotes = medNotes.getText().toString();

                String number = num+"";
                String medinterval = Intervals[interval];




                boolean invalid = false;
                if (medname.equals("")){
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Name Missing", Toast.LENGTH_SHORT).show();
                }else if(medquantity.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Quantity Missing", Toast.LENGTH_SHORT).show();
                }
                else if(number.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Weeks Missing", Toast.LENGTH_SHORT).show();
                }
                else if(medinterval.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Days Missing", Toast.LENGTH_SHORT).show();
                }

                if(invalid == false){


                    addMedication(medname, medquantity,mednotes,number,medinterval);
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




    public void addMedication(String medname, String medquantity, String mednotes, String number, String interval ){

        SQLiteDatabase db = myDb.getWritableDatabase();

        Integer count = getMedCount();

        ContentValues values = new ContentValues();
        values.put("med_id", count+1);
        values.put("medname", medname);
        values.put("medquantity", medquantity);
        values.put("mednotes", mednotes);
        values.put("remnumber", number);
        values.put("interval", interval);



        try{
            db.insert(DbHelper.MEDICATION_TABLE_NAME, null, values);

        }catch(Exception e){
            e.printStackTrace();
        }
        db.close();

    }

    public int getMedCount() {
        SQLiteDatabase db = myDb.getWritableDatabase();
        String countQuery = "SELECT  * FROM " + DbHelper.MEDICATION_TABLE_NAME;

        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
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
