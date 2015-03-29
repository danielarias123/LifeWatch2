package com.example.danarias.lifewatch2;

import android.app.AlertDialog;
import android.content.DialogInterface;
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


public class ContactInfoActivity extends ActionBarActivity implements OnClickListener {

    Button backtocontacts2Button;
    Button deleteContactButton;

    DbHelper mydatabase = new DbHelper(this);
    RadioGroup onOffRadioGroup;
    RadioButton contactRadioButton;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        RadioButton checkRadioButton;
        checkRadioButton = (RadioButton) findViewById(R.id.onRadioButton);

        RadioButton check2RadioButton;
        check2RadioButton = (RadioButton) findViewById(R.id.offRadioButton);



        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        String contname = mydatabase.getName(position+1);
        String contphone = mydatabase.getPhone(position + 1);
        String contemail = mydatabase.getEmail(position + 1);
        final String ifEmergContact = mydatabase.getIfEmergContact(position+1).toString();

        TextView nameTextView = (TextView) findViewById(R.id.contactInfoTextView);
        nameTextView.setText(contname);

        TextView phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        phoneTextView.setText(contphone);

        TextView emailTextView = (TextView) findViewById(R.id.emailTextView);
        emailTextView.setText(contemail);

        backtocontacts2Button = (Button) findViewById(R.id.backtocontacts2Button);
        backtocontacts2Button.setOnClickListener(this);

        deleteContactButton = (Button) findViewById(R.id.deleteContactButton);
        deleteContactButton.setOnClickListener(this);


        onOffRadioGroup = (RadioGroup) findViewById(R.id.onOffRadioGroup);



        onOffRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 int selected = onOffRadioGroup.getCheckedRadioButtonId();
                contactRadioButton = (RadioButton) findViewById(selected);
                setEmergContact(contactRadioButton.getText().toString(), position);



            }
        });
        if (ifEmergContact.contains("yes")) {
            Toast.makeText(getApplicationContext(), "This Contact will be messaged", Toast.LENGTH_SHORT).show();
            checkRadioButton.setChecked(true);
        }

        if (ifEmergContact.contains("no")) {
            Toast.makeText(getApplicationContext(), "This Contact will not be messaged", Toast.LENGTH_SHORT).show();
            check2RadioButton.setChecked(true);
        }





    }
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.backtocontacts2Button:
                Intent i = new Intent(ContactInfoActivity.this, ContactsActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.deleteContactButton:
                AlertDialog.Builder ad = new AlertDialog.Builder(ContactInfoActivity.this);

                ad.setMessage("Delete this Contact?");
                ad.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Delete of record from Database and List view.
                        deleteContact(position);
                        Toast.makeText(getApplicationContext(),"Contact Deleted",Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(ContactInfoActivity.this, ContactsActivity.class);
                        startActivity(i2);
                        finish();

                    }
                });
                ad.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
                ad.show();


                break;




        }
    }





    public void setEmergContact(String ifEmergContact, Integer position){

        SQLiteDatabase db = mydatabase.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("ifEmergContact",ifEmergContact); //These Fields should be your String values of actual column names
        db.update(DbHelper.CONTACTS_TABLE_NAME, cv, "_id "+"="+(position+1), null);
        db.close();

    }

    public void deleteContact(Integer position){
        SQLiteDatabase db = mydatabase.getWritableDatabase();
        db.delete(DbHelper.CONTACTS_TABLE_NAME, "_id " + "=" + (position+1), null);
        String strSQL = "UPDATE Contacts SET _id = _id-1 WHERE _id > "+ (position+1);

        db.execSQL(strSQL);

        db.close();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_info, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ContactInfoActivity.this, ContactsActivity.class);
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
