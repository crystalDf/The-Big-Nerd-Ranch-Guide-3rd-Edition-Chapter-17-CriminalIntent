package com.star.criminalintent.database;


import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.star.criminalintent.CrimeLab;
import com.star.criminalintent.database.CrimeDbSchema.CrimeTable;
import com.star.criminalintent.model.Crime;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper{

    private Context mContext;

    public CrimeCursorWrapper(Cursor cursor, Context context) {
        super(cursor);
        mContext = context;
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int solved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        int requiresPolice = getInt(getColumnIndex(CrimeTable.Cols.REQUIRES_POLICE));
        String contactId = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(solved != 0);
        crime.setRequiresPolice(requiresPolice != 0);
        crime.setSuspect(CrimeLab.getInstance(mContext).getSuspect(contactId));

        return crime;
    }
}
