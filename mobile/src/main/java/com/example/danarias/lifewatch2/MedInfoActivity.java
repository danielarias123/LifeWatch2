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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;


public class MedInfoActivity extends ActionBarActivity implements OnClickListener {

    Button backtomedication2Button;
    DbHelper mydatabase = new DbHelper(this);

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_info);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        String medname = mydatabase.getMedName(position+1);
        String medquantity = mydatabase.getMedQuantity(position + 1);
        String mednotes = mydatabase.getMedNotes(position + 1);
        String weeks = mydatabase.getIntervalWeek(position + 1);
        String days = mydatabase.getIntervalDay(position + 1);
        String hours = mydatabase.getIntervalHour(position + 1);


        backtomedication2Button = (Button) findViewById(R.id.backtomed2Button);
        backtomedication2Button.setOnClickListener(this);

        TextView mednameTextView = (TextView) findViewById(R.id.medInfoTextView);
        mednameTextView.setText(medname);

        TextView medquantTextView = (TextView) findViewById(R.id.quantTextView);
        medquantTextView.setText(medquantity);

        TextView mednotesTextView = (TextView) findViewById(R.id.mednotesTextView);
        mednotesTextView.setText(mednotes);

        TextView intervalTextView = (TextView) findViewById(R.id.intervalTextView);

        intervalTextView.setText(weeks +" weeks "+ days + " days " + hours + " hours " );


    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.backtomed2Button:
                Intent i = new Intent(MedInfoActivity.this, MedReminderActivity.class);
                startActivity(i);
                finish();
                break;




        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_med_info, menu);
        return true;
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
