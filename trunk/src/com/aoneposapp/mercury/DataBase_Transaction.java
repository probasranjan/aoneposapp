package com.aoneposapp.mercury;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase_Transaction extends SQLiteOpenHelper {
	 
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "contactsManager";
 
    // Table Names
    private static final String TABLE_TODO = "todos";
 
    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
 
    // NOTES Table - column nmaes
    public static final String KEY_PRIMARY = "primaryurl";
    public static final String KEY_SECONDARY = "secondaryurl";
    public static final String KEY_MERCHANT_ID = "merchantId";
    public static final String KEY_TIMEOUT = "timeout";
 
 
 
    // Table Create Statements
    // Todo table create statement
//    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
//            + TABLE_TODO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TODO
//            + " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED_AT
//            + " DATETIME" + ")";
    
    String CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_TODO + "("
    		+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_PRIMARY + " TEXT,"
    		+ KEY_SECONDARY + " TEXT,"+ KEY_MERCHANT_ID + " TEXT," + KEY_TIMEOUT + " TEXT" + ")";
 
    public DataBase_Transaction(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_TODO);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);        
 
        // create new tables
        onCreate(db);
    }
    
    
    public void Add_Contact(ContentValues contact) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	// Inserting Row
    	db.insert(TABLE_TODO, null, contact);
    	db.close(); // Closing database connection
    	}
    
    
 // Getting single contact
    public String Get_Contact(int id) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.query(TABLE_TODO, new String[] { KEY_ID,
    KEY_PRIMARY, KEY_SECONDARY, KEY_MERCHANT_ID }, KEY_ID + "=?",
    new String[] { String.valueOf(id) }, null, null, null, null);
    if (cursor != null)
    cursor.moveToFirst();

//    HashMap<String, String>
    
    String primaryUrl  = cursor.getString(1);    
    String secURL =  cursor.getString(2);
    String merchantid = cursor.getString(3);
    
    cursor.close();
    db.close();
    
    return primaryUrl;
    
    }
}