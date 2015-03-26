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



public class createContactActivity extends ActionBarActivity implements OnClickListener{

    EditText contactName;
    EditText contactNumber;
    EditText contactEmail;




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
                contactEmail = (EditText) findViewById(R.id.emailEditText);

                String name = contactName.getText().toString();
                String phone = contactNumber.getText().toString();
                String email = contactEmail.getText().toString();


                boolean invalid = false;
                if (name.equals("")){
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Name Missing", Toast.LENGTH_SHORT).show();
                }else if(phone.equals("")) {
                    invalid = true;
                    Toast.makeText(getApplicationContext(), "Number Missing", Toast.LENGTH_SHORT).show();
                }
                if(invalid == false){


                    addEntry(name, phone, email);
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


    public void addEntry(String name, String phone, String email){

        SQLiteDatabase db = myDb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        values.put("email", email);
        values.put("ifEmergContact","no");

        try{
            db.insert(DbHelper.CONTACTS_TABLE_NAME, null, values);
            Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }
        db.close();

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
