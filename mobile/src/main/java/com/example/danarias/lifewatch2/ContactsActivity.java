package com.example.danarias.lifewatch2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.content.ContentValues;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;


public class ContactsActivity extends ActionBarActivity implements OnClickListener, OnItemClickListener {

    Button addContactButton;
    Button backButton;

    ListView list;


    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    private DbHelper myDb = new DbHelper(ContactsActivity.this);

    DbHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mydb = new DbHelper(this);
        mydb.getWritableDatabase();



        addContactButton = (Button) findViewById(R.id.createContactButton);
        addContactButton.setOnClickListener(this);

        list = (ListView) findViewById(R.id.contactsListView);
        list.setOnItemClickListener(this);
        arrayList = new ArrayList<String>();

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1);
        list.setAdapter(adapter);
        //String[] columns = {"name"};
        //retrieveEntries(columns,null,null);

        List<String> contacts = myDb.getAllContacts();
        for (String name: contacts ) {
            if (!name.equals("")) {
                adapter.add(name);
            }

        }




    }



    public void onClick(View v) {

        switch(v.getId()){

            case R.id.createContactButton:
                Intent intent = new Intent(ContactsActivity.this, createContactActivity.class);
                startActivity(intent);
                finish();
                break;


            case R.id.backButton:
                Intent i = new Intent(ContactsActivity.this, MobileMainActivity.class);
                startActivity(i);
                finish();
                break;


        }
    }
    @Override
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {

        Intent intent = new Intent(ContactsActivity.this, ContactInfoActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
        finish();
        ;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ContactsActivity.this, MobileMainActivity.class);
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
