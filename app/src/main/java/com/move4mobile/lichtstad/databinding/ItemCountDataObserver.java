package com.move4mobile.lichtstad.databinding;

import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

public interface ItemCountDataObserver {

    @NonNull
    ObservableInt getCount();

    void cleanup();

}
