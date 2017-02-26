package com.move4mobile.lichtstad.presenter;

import android.view.View;

import com.move4mobile.lichtstad.model.Program;

public class ProgramPresenter {

    public void onProgramClick(View view, Program program) {
        program.expanded.set(!program.expanded.get());
    }

}
