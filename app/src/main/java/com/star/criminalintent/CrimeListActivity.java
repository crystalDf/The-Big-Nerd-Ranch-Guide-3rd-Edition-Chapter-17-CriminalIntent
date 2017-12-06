package com.star.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.star.criminalintent.model.Crime;

import java.util.List;
import java.util.UUID;

public class CrimeListActivity extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {

    private static final int REQUEST_CRIME = 0;

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_master_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onCrimeInitialized();
    }

    public void onCrimeInitialized() {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(
                    R.id.detail_fragment_container);
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commit();
            }

            return;
        }

        List<Crime> crimes = CrimeLab.getInstance(this).getCrimes();

        if (!crimes.isEmpty()) {
            Fragment newDetail = CrimeFragment.newInstance(crimes.get(0).getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        } else {
            ConstraintLayout detailsLayout =
                    findViewById(R.id.crime_details_constraint_layout);
            if (detailsLayout != null) {
                detailsLayout.setVisibility(View.GONE);
            }

            Fragment fragment = getSupportFragmentManager().findFragmentById(
                    R.id.detail_fragment_container);
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commit();
            }
        }
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivityForResult(intent, REQUEST_CRIME);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    public void onCrimeUpdated() {
        CrimeListFragment crimeListFragment = (CrimeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        crimeListFragment.updateUI();
    }

    @Override
    public void onCrimeDeleted() {
        onCrimeInitialized();
        onCrimeUpdated();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CRIME && resultCode == RESULT_OK
                && findViewById(R.id.detail_fragment_container) != null) {
            Crime crime = CrimeLab.getInstance(this).getCrime(
                    (UUID) data.getSerializableExtra(CrimePagerActivity.EXTRA_CRIME_ID));
            if (crime != null) {
                Fragment newDetail = CrimeFragment.newInstance(crime.getId());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, newDetail)
                        .commit();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
