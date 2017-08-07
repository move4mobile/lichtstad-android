package com.move4mobile.lichtstad.program;

import android.databinding.ObservableMap;
import android.view.View;

import com.move4mobile.lichtstad.model.Program;

public interface ProgramPresenter {

    ObservableMap<String, Boolean> getExpandedMap();
    void onProgramClick(View view, Program program);

}
