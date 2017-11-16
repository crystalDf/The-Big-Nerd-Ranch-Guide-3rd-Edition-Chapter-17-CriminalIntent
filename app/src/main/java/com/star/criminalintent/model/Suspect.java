package com.star.criminalintent.model;


import java.util.UUID;

public class Suspect {

    private UUID mId;
    private String mContactId;
    private String mDisplayName;
    private String mPhoneNumber;
    private int mCrimeCount;

    public Suspect() {
        this(UUID.randomUUID());
    }

    public Suspect(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getContactId() {
        return mContactId;
    }

    public void setContactId(String contactId) {
        mContactId = contactId;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public int getCrimeCount() {
        return mCrimeCount;
    }

    public void setCrimeCount(int crimeCount) {
        mCrimeCount = crimeCount;
    }
}
