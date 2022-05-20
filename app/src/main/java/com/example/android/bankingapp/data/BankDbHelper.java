package com.example.android.bankingapp.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BankDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "bankingSystem.db";
    public static final int DATABASE_VERSION = 1;
    private final String SQL_CREATE_BANK_TABLE = " CREATE TABLE if not exists " + BankContract.BankEntry.TABLE_NAME + " (" +
            BankContract.BankEntry._Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME + " TEXT NOT NULL, " +
            BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER + " INTEGER NOT NULL DEFAULT 0 , "+
            BankContract.BankEntry.COLUMN_IFSC_NUMBER + " INTEGER NOT NULL DEFAULT 0 , "+
            BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL + " TEXT NOT NULL, " +
            BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER + " TEXT NOT NULL, " +
       //     BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER + " INTEGER NOT NULL , " +
            BankContract.BankEntry.COLUMN_TOTAL_BALANCE + " INTEGER NOT NULL DEFAULT 0 );";

    //Record table for transfer
    private final String SQL_CREATE_TRANSFER_MONEY_TABLE = " CREATE TABLE if not exists " + BankContract.BankEntry.TRANSFER_TABLE_NAME + " (" +
            BankContract.BankEntry._2Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BankContract.BankEntry.COLUMN_FROM + " INTEGER NOT NULL DEFAULT 0 , "+
            BankContract.BankEntry.COLUMN_TO + " INTEGER NOT NULL DEFAULT 0 , "+
            BankContract.BankEntry.COLUMN_TRANSFER_MONEY + " INTEGER NOT NULL );";


    /**
     * Constructs a new instance of {@link BankDbHelper}.
     *
     * @param context of the app
     */
    public BankDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_BANK_TABLE);
        //for transfer money
        Log.e("BankDbHelper","hello1");
        sqLiteDatabase.execSQL(SQL_CREATE_TRANSFER_MONEY_TABLE);
        Log.e("BankDbHelper","hello2");

    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
// The database is still at version 1, so there's nothing to do be done here.
    }
}
