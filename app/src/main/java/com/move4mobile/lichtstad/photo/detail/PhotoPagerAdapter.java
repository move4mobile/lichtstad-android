package com.move4mobile.lichtstad.photo.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemPhotoDetailBinding;
import com.move4mobile.lichtstad.model.Photo;
import com.move4mobile.lichtstad.widget.FirebaseViewPagerAdapter;


public class PhotoPagerAdapter extends FirebaseViewPagerAdapter<Photo> {

    public PhotoPagerAdapter(Query query) {
        super(Photo.class, query);
    }

    @Override
    protected View getView(Photo object, ViewGroup container) {
        ListItemPhotoDetailBinding binding = ListItemPhotoDetailBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
        binding.setPhoto(object);
        return binding.getRoot();
    }

}
