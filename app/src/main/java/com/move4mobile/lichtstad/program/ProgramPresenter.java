package com.move4mobile.lichtstad.program;

import android.databinding.ObservableArrayMap;
import android.databinding.ObservableMap;
import android.view.View;

import com.move4mobile.lichtstad.model.Program;

public class ProgramPresenter {

    public ObservableMap<String, Boolean> expandedMap = new ObservableArrayMap<>();

    public void onProgramClick(View view, Program program) {
        Boolean wasExpanded = expandedMap.get(program.key);
        wasExpanded = wasExpanded == null ? false : wasExpanded;
        expandedMap.put(program.key, !wasExpanded);
    }

    void onProgramRemoved(Program program) {
        expandedMap.remove(program.key);
    }

}
