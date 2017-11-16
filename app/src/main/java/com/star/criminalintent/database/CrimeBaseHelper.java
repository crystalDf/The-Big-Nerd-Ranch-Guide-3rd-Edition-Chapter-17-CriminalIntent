package com.star.criminalintent.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.star.criminalintent.database.CrimeDbSchema.CrimeTable;
import com.star.criminalintent.database.CrimeDbSchema.SuspectTable;

public class CrimeBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 3;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CrimeTable.CREATE_TABLE);
        db.execSQL(CrimeTable.ALTER_TABLE);
        db.execSQL(SuspectTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(CrimeTable.ALTER_TABLE);
            case 2:
                db.execSQL(SuspectTable.CREATE_TABLE);
            default:
        }
    }
}
