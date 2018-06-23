package com.move4mobile.lichtstad.program;


import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;

import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.move4mobile.lichtstad.R;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProgramPagerAdapter extends FragmentStatePagerAdapter implements LifecycleObserver, ChangeEventListener {

    private static final String TAG = ProgramPagerAdapter.class.getSimpleName();
    private Context context;
    private DateFormat format;

    private final ObservableSnapshotArray<Calendar> snapshots;

    @SuppressLint("SimpleDateFormat")
    public ProgramPagerAdapter(Context context, FragmentManager fm, @NonNull FirebaseRecyclerOptions<Calendar> daysOptions) {
        super(fm);
        this.context = context;
        this.snapshots = daysOptions.getSnapshots();

        DateFormatSymbols symbols = DateFormatSymbols.getInstance(context.getResources().getConfiguration().locale);
        symbols.setShortMonths(context.getResources().getStringArray(R.array.abbreviated_months));
        format = new SimpleDateFormat(context.getString(R.string.format_tab_date), symbols);

        if (daysOptions.getOwner() != null) {
            daysOptions.getOwner().getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void startListening() {
        if (!snapshots.isListening(this)) {
            snapshots.addChangeEventListener(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopListening() {
        snapshots.removeChangeEventListener(this);
        notifyDataSetChanged();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void cleanup(LifecycleOwner source) {
        source.getLifecycle().removeObserver(this);
    }

    @Override
    public Fragment getItem(int position) {
        return ProgramDayFragment.newInstance(snapshots.get(position));
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (!(object instanceof ProgramDayFragment)) {
            return POSITION_NONE;
        }

        Calendar day = ((ProgramDayFragment) object).getDay();
        for (int i = 0; i < snapshots.size(); i++) {
            Calendar otherDay = snapshots.get(i);
            if (day.equals(otherDay)) {
                return i;
            }
        }

        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return snapshots.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String formattedDate = format.format(snapshots.get(position).getTime());
        String[] lines = formattedDate.split("\n", 2);
        if (lines.length == 0) {
            throw new IllegalStateException();
        }
        if (lines.length == 1) {
            return formattedDate;
        }

        SpannableString firstLine = new SpannableString(lines[0]);
        firstLine.setSpan(new RelativeSizeSpan(13f / 15), 0, firstLine.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        String date = lines[1].toUpperCase(context.getResources().getConfiguration().locale);
        SpannableString spannedDate = new SpannableString(date);
        spannedDate.setSpan(new StyleSpan(Typeface.BOLD), 0, date.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return new SpannableStringBuilder()
                .append(lines[0])
                .append('\n')
                .append(spannedDate);
    }

    @Override
    public void onChildChanged(@NonNull ChangeEventType type, @NonNull DataSnapshot snapshot, int newIndex, int oldIndex) {
        notifyDataSetChanged();
    }

    @Override
    public void onDataChanged() {
    }

    @Override
    public void onError(@NonNull DatabaseError error) {
        Log.w(TAG, error.toException());
    }
}
