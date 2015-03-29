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
import android.database.Cursor;



public class createContactActivity extends ActionBarActivity implements OnClickListener{

    EditText contactName;
    EditText contactNumber;





    Button saveContactButton;
    Button backtocontactsButton;

    private DbHelper myDb = new DbHelper(createContactActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);





        saveContactButton = (Button) findViewById(R.id.saveContactButton);
        saveContactButton.setOnClickListener(this);



        backtocontactsButton = (Button) findViewById(R.id.backtocontactsButton);
        backtocontactsButton.setOnClickListener(this);



    }

    public void onClick(View v) {

        switch(v.getId()){

            case R.id.saveContactButton:
                contactName = (EditText) findViewById(R.id.nameEditText);
                contactNumber = (EditText) findViewById(R.id.phoneEditText);

                String name = contactName.getText().toString();
                String phone = contactNumber.getText().toString();



                boolean invalid = false;
                if (name.equals("")){
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Name Missing", Toast.LENGTH_SHORT).show();
                }else if(phone.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Number Missing", Toast.LENGTH_SHORT).show();
                }
                if(invalid == false){


                    addEntry(name, phone);
                    Toast.makeText(getApplicationContext(),"Contact Created",Toast.LENGTH_SHORT).show();
                    Intent i_register = new Intent(createContactActivity.this, ContactsActivity.class);
                    startActivity(i_register);
                    finish();
                }
                break;


            case R.id.backtocontactsButton:
                Intent i = new Intent(createContactActivity.this, ContactsActivity.class);
                startActivity(i);
                finish();
                break;


        }
    }


    public class TextListener implements TextWatcher{

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub
            if(contactName.getText().length()==0 | contactNumber.getText().length()==0){
                saveContactButton.setEnabled(false);

            }
            else if(contactName.getText().length()>0 & contactNumber.getText().length()>0){
                saveContactButton.setEnabled(true);

            }
        }
    }


    public void addEntry(String name, String phone){

        SQLiteDatabase db = myDb.getWritableDatabase();

        Integer count = getContactCount();

        ContentValues values = new ContentValues();
        values.put("_id", count+1);
        values.put("name", name);
        values.put("phone", phone);
        values.put("ifEmergContact","no");

        try{
            db.insert(DbHelper.CONTACTS_TABLE_NAME, null, values);

        }catch(Exception e){
            e.printStackTrace();
        }
        db.close();

    }

    public int getContactCount() {
        SQLiteDatabase db = myDb.getWritableDatabase();
        String countQuery = "SELECT  * FROM " + DbHelper.CONTACTS_TABLE_NAME;

        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_contact, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(createContactActivity.this, ContactsActivity.class);
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
