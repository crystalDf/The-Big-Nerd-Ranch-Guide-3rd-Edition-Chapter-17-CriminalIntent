package com.star.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.star.criminalintent.model.Crime;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    public static final int REQUIRES_POLICE = 1;
    public static final int NOT_REQUIRES_POLICE = 0;

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    private ConstraintLayout mEmptyViewConstraintLayout;
    private Button mNewCrimeButton;

    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mEmptyViewConstraintLayout = view.findViewById(R.id.empty_view);
        mNewCrimeButton = view.findViewById(R.id.new_crime_button);
        mNewCrimeButton.setOnClickListener(v -> newCrime());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.setCrimes(crimes);
            mCrimeAdapter.notifyDataSetChanged();
        }

        updateSubtitle();

        int crimeCount = CrimeLab.getInstance(getActivity()).getCrimes().size();
        mEmptyViewConstraintLayout.setVisibility(crimeCount == 0 ? View.VISIBLE : View.GONE);
    }

    private abstract class CrimeHolder extends RecyclerView.ViewHolder {

        protected Crime mCrime;

        protected TextView mTitleTextView;
        protected TextView mDateTextView;
        protected ImageView mSolvedImageView;

        public CrimeHolder(LayoutInflater inflater, ViewGroup container, @LayoutRes int resource) {
            super(inflater.inflate(resource, container, false));

            itemView.setOnClickListener(v -> {
                Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
                startActivity(intent);
            });

            mTitleTextView = itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedImageView = itemView.findViewById(R.id.list_item_crime_solved_image_view);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getFormattedDate());
            mSolvedImageView.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);
        }
    }

    private class RegularCrimeHolder extends CrimeHolder {

        public RegularCrimeHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater, container, R.layout.list_item_crime);
        }
    }

    private class SeriousCrimeHolder extends CrimeHolder {

        private Button mRequiresPoliceButton;

        public SeriousCrimeHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater, container, R.layout.list_item_crime_requires_police);

            mRequiresPoliceButton = itemView.findViewById(R.id.list_item_crime_requires_police_button);

            mRequiresPoliceButton.setOnClickListener(v -> Toast.makeText(getActivity(),
                    mCrime.getTitle() + " requires police!", Toast.LENGTH_LONG).show());
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            switch (viewType) {
                case REQUIRES_POLICE:
                    return new SeriousCrimeHolder(layoutInflater, parent);
                case NOT_REQUIRES_POLICE:
                    return new RegularCrimeHolder(layoutInflater, parent);
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            Crime crime = mCrimes.get(position);

            return crime.isRequiresPolice() ? REQUIRES_POLICE : NOT_REQUIRES_POLICE;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_show_subtitle);

        if (mSubtitleVisible) {
            menuItem.setTitle(R.string.hide_subtitle);
        } else {
            menuItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                newCrime();
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newCrime() {
        Crime crime = new Crime();
        CrimeLab.getInstance(getActivity()).addCrime(crime);
        Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
        startActivity(intent);
    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        int crimeCount = crimeLab.getCrimes().size();

        String subtitle = (crimeCount == 1)
                ? getString(R.string.subtitle_singular_format)
                : getString(R.string.subtitle_plural_format, crimeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }
    }
}
