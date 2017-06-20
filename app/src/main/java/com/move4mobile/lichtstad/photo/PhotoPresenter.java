package com.move4mobile.lichtstad.photo;

import android.view.View;

import com.move4mobile.lichtstad.model.Photo;


public interface PhotoPresenter {

    void onPhotoClick(View view, Photo photo);

}
