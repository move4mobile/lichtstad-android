package com.move4mobile.lichtstad.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProgramViewModel extends ViewModel {

    public final MutableLiveData<Boolean> showFavorites = new MutableLiveData<>();
    {
        showFavorites.setValue(false);
    }

}
