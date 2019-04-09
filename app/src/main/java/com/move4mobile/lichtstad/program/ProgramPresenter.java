package com.move4mobile.lichtstad.program;

import androidx.databinding.ObservableMap;
import android.view.View;

import com.move4mobile.lichtstad.model.Program;

public interface ProgramPresenter {

    ObservableMap<String, Boolean> getExpandedMap();
    ObservableMap<String, Boolean> getFavoriteMap();
    void onProgramClick(View view, Program program);
    void onFavoriteClick(Program program);

}
