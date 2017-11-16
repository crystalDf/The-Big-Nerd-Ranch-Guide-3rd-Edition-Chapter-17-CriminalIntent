package com.star.criminalintent.database;


public class CrimeDbSchema {

    public static final class CrimeTable {

        public static final String TABLE_NAME = "crimes";

        public static final class Cols {
            private static final String ID = "_id";
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String REQUIRES_POLICE = "requiresPolice";
            public static final String SUSPECT = "suspect";
        }

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Cols.UUID + ", "
                + Cols.TITLE + ", "
                + Cols.DATE + ", "
                + Cols.SOLVED + ", "
                + Cols.REQUIRES_POLICE
                + ")";

        public static final String ALTER_TABLE = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN "
                + Cols.SUSPECT;
    }

    public static final class SuspectTable {

        public static final String TABLE_NAME = "suspects";

        public static final class Cols {
            private static final String ID = "_id";
            public static final String UUID = "uuid";
            public static final String CONTACT_ID = "contact_id";
            public static final String DISPLAY_NAME = "display_name";
            public static final String PHONE_NUMBER = "phone_number";
            public static final String CRIME_COUNT = "crime_count";
        }

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Cols.UUID + ", "
                + Cols.CONTACT_ID + ", "
                + Cols.DISPLAY_NAME + ", "
                + Cols.PHONE_NUMBER + ", "
                + Cols.CRIME_COUNT
                + ")";
    }
}
