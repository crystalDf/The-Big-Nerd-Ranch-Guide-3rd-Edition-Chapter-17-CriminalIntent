package com.star.criminalintent.database;


import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.star.criminalintent.database.CrimeDbSchema.SuspectTable;
import com.star.criminalintent.model.Suspect;

import java.util.UUID;

public class SuspectCursorWrapper extends CursorWrapper{

    private Context mContext;

    public SuspectCursorWrapper(Cursor cursor, Context context) {
        super(cursor);
        mContext = context;
    }

    public Suspect getSuspect() {
        String uuidString = getString(getColumnIndex(SuspectTable.Cols.UUID));
        String contactId = getString(getColumnIndex(SuspectTable.Cols.CONTACT_ID));
        String displayName = getString(getColumnIndex(SuspectTable.Cols.DISPLAY_NAME));
        String phoneNumber = getString(getColumnIndex(SuspectTable.Cols.PHONE_NUMBER));
        int crimeCount = getInt(getColumnIndex(SuspectTable.Cols.CRIME_COUNT));

        Suspect suspect = new Suspect(UUID.fromString(uuidString));
        suspect.setContactId(contactId);
        suspect.setDisplayName(displayName);
        suspect.setPhoneNumber(phoneNumber);
        suspect.setCrimeCount(crimeCount);

        return suspect;
    }
}
