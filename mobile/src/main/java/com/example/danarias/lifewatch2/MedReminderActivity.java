package com.example.danarias.lifewatch2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;


public class MedReminderActivity extends ActionBarActivity  implements OnClickListener,OnItemClickListener {


    Button addMedButton;
    Button editMedButton;
    Button backButton;

    ListView list;

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    private DbHelper myDb = new DbHelper(MedReminderActivity.this);

    DbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_reminder);

        mydb = new DbHelper(this);
        mydb.getWritableDatabase();



        addMedButton = (Button) findViewById(R.id.addMedButton);
        addMedButton.setOnClickListener(this);

        list = (ListView) findViewById(R.id.medicationListView);
        list.setOnItemClickListener(this);
        arrayList = new ArrayList<String>();

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1);
        list.setAdapter(adapter);

        List<String> medication = myDb.getAllMedication();
        for (String med_name: medication ) {
            if (!med_name.equals("")) {
                adapter.add(med_name);
            }

        }
    }

    public void onClick(View v) {

        switch(v.getId()){

            case R.id.addMedButton:
                Intent intent = new Intent(MedReminderActivity.this, createMedActivity.class);
                startActivity(intent);
                finish();
                break;


            case R.id.backButton:
                Intent i = new Intent(MedReminderActivity.this, MobileMainActivity.class);
                startActivity(i);
                finish();
                break;


        }
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {

        Intent intent = new Intent(MedReminderActivity.this, MedInfoActivity.class);
        startActivity(intent);
        finish();
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_med_reminder, menu);
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
