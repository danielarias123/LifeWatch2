package com.example.danarias.lifewatch2;

/**
 * Created by danarias on 15-02-22.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "lifewatch.db";
    private static final int DATABASE_VERSION = 16;
    public static final String CONTACTS_TABLE_NAME = "Contacts";
    public static final String MEDICATION_TABLE_NAME = "Medication";

    public static final String USER_ID = "_id";
    public static final String NAME_CONTACT = "name";
    public static final String PHONE_CONTACT = "phone";
    public static final String EMAIL_CONTACT = "email";
    public static final String IF_EMERG_CONTACT = "ifEmergContact";

    public static final String MED_ID = "med_id";
    public static final String NAME_MED = "medname";
    public static final String QUANT_MED = "medquantity";
    public static final String NOTES_MED = "mednotes";
    public static final String INTERVAL_NUM = "remnumber";
    public static final String INTERVAL = "interval";


    public static final String[] ALL_KEYS = new String[] {USER_ID, NAME_CONTACT, PHONE_CONTACT, EMAIL_CONTACT, IF_EMERG_CONTACT};

    private static final String LIFEWATCH_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + CONTACTS_TABLE_NAME + "(" +USER_ID
                    +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    NAME_CONTACT + " TEXT NOT NULL, "+PHONE_CONTACT+ " TEXT NOT NULL, "+EMAIL_CONTACT+ " TEXT, "+ IF_EMERG_CONTACT+" TEXT);";

    private static final String MEDICATION_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + MEDICATION_TABLE_NAME + "(" +MED_ID
                    +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    NAME_MED + " TEXT NOT NULL, "+QUANT_MED+ " TEXT NOT NULL, "+NOTES_MED+ " TEXT, "+INTERVAL_NUM+ " TEXT NOT NULL, "+INTERVAL+ " TEXT NOT NULL);";

    private SQLiteDatabase db;

    public DbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("In constructor");

    }

    /* (non-Javadoc)
     * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            //Create Database
            db.execSQL(LIFEWATCH_TABLE_CREATE);

            db.execSQL(MEDICATION_TABLE_CREATE);


            System.out.println("In onCreate");
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public List<String> getAllContacts(){
        List<String> contacts = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + CONTACTS_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                contacts.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();


        return contacts;
    }

    public List<String> getAllMedication(){
        List<String> medication = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + MEDICATION_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                medication.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();


        return medication;
    }

    public String getName(Integer contact_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_CONTACT,PHONE_CONTACT,EMAIL_CONTACT};
        String clause = USER_ID +" = '"+contact_id+"'";
        Cursor c = mydb.query(CONTACTS_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";
        int irow = c.getColumnIndex(USER_ID);
        int iname = c.getColumnIndex(NAME_CONTACT);
        int iphone = c.getColumnIndex(PHONE_CONTACT);
        int iemail = c.getColumnIndex(EMAIL_CONTACT);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(iname)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }

    public String getPhone(Integer contact_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_CONTACT,PHONE_CONTACT,EMAIL_CONTACT};
        String clause = USER_ID +" = '"+contact_id+"'";
        Cursor c = mydb.query(CONTACTS_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";
        int irow = c.getColumnIndex(USER_ID);
        int iname = c.getColumnIndex(NAME_CONTACT);
        int iphone = c.getColumnIndex(PHONE_CONTACT);
        int iemail = c.getColumnIndex(EMAIL_CONTACT);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(iphone)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }

    public String getEmail(Integer contact_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_CONTACT,PHONE_CONTACT,EMAIL_CONTACT};
        String clause = USER_ID +" = '"+contact_id+"'";
        Cursor c = mydb.query(CONTACTS_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";
        int irow = c.getColumnIndex(USER_ID);
        int iname = c.getColumnIndex(NAME_CONTACT);
        int iphone = c.getColumnIndex(PHONE_CONTACT);
        int iemail = c.getColumnIndex(EMAIL_CONTACT);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(iemail)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }

    public String getIfEmergContact(Integer contact_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_CONTACT,PHONE_CONTACT,EMAIL_CONTACT, IF_EMERG_CONTACT};
        String clause = USER_ID +" = '"+contact_id+"'";
        Cursor c = mydb.query(CONTACTS_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="no";
        int irow = c.getColumnIndex(USER_ID);
        int iname = c.getColumnIndex(NAME_CONTACT);
        int iphone = c.getColumnIndex(PHONE_CONTACT);
        int iemail = c.getColumnIndex(EMAIL_CONTACT);
        int iEmergContact = c.getColumnIndex(IF_EMERG_CONTACT);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(iEmergContact)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }

    public String getMedName(Integer med_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_MED, QUANT_MED, NOTES_MED, INTERVAL_NUM,INTERVAL};
        String clause = MED_ID +" = '"+med_id+"'";
        Cursor c = mydb.query(MEDICATION_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";

        int imedname = c.getColumnIndex(NAME_MED);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(imedname)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }
    public String getMedQuantity(Integer med_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_MED, QUANT_MED, NOTES_MED, INTERVAL_NUM, INTERVAL};
        String clause = MED_ID +" = '"+med_id+"'";
        Cursor c = mydb.query(MEDICATION_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";

        int imedquant = c.getColumnIndex(QUANT_MED);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(imedquant)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }
    public String getMedNotes(Integer med_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_MED, QUANT_MED, NOTES_MED, INTERVAL_NUM, INTERVAL};
        String clause = MED_ID +" = '"+med_id+"'";
        Cursor c = mydb.query(MEDICATION_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";

        int imednotes = c.getColumnIndex(NOTES_MED);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(imednotes)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }
    public String getIntervalNum(Integer med_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_MED, QUANT_MED, NOTES_MED, INTERVAL_NUM,INTERVAL};
        String clause = MED_ID +" = '"+med_id+"'";
        Cursor c = mydb.query(MEDICATION_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";

        int intervalweek = c.getColumnIndex(INTERVAL_NUM);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(intervalweek)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }
    public String getInterval(Integer med_id){
        SQLiteDatabase mydb = this.getWritableDatabase();
        String[] columns = new String[]{ NAME_MED, QUANT_MED, NOTES_MED, INTERVAL_NUM, INTERVAL};
        String clause = MED_ID +" = '"+med_id+"'";
        Cursor c = mydb.query(MEDICATION_TABLE_NAME, columns,clause, null, null, null, null, null);

        String result="";

        int intervalday = c.getColumnIndex(INTERVAL);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result = c.getString(intervalday)+"\n";
        }

        mydb.close();
        c.close();
        return result;
    }




    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(CONTACTS_TABLE_NAME, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(DbHelper.class.getName(),"Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS "+ CONTACTS_TABLE_NAME);
        onCreate(db);

        Log.w(DbHelper.class.getName(),"Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS "+ MEDICATION_TABLE_NAME);
        onCreate(db);


    }



}
