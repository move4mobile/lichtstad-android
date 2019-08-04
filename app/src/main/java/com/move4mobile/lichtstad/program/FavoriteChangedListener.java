package com.move4mobile.lichtstad.program;

import com.move4mobile.lichtstad.model.Program;

public interface FavoriteChangedListener {
    void onFavoriteChanged(Program program, boolean isFavorite);
}
