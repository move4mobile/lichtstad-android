package com.move4mobile.lichtstad.photo.detail;

import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.Query;
import com.move4mobile.lichtstad.databinding.ListItemPhotoDetailBinding;
import com.move4mobile.lichtstad.model.Photo;
import com.move4mobile.lichtstad.widget.FirebaseViewPagerAdapter;


public class PhotoPagerAdapter extends FirebaseViewPagerAdapter<Photo> {

    private OnMatrixChangedListener onMatrixChangedListener;

    public PhotoPagerAdapter(Query query) {
        super(Photo.class, query);
    }

    @Override
    protected View getView(Photo object, ViewGroup container) {
        ListItemPhotoDetailBinding binding = ListItemPhotoDetailBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
        binding.setPhoto(object);
        final PhotoView photoView = binding.photoView;
        binding.photoView.setOnMatrixChangeListener(new com.github.chrisbanes.photoview.OnMatrixChangedListener() {
            @Override
            public void onMatrixChanged(RectF rect) {
                if (onMatrixChangedListener != null) {
                    onMatrixChangedListener.onMatrixChanged(rect, photoView);
                }
            }
        });
        return binding.getRoot();
    }

    public OnMatrixChangedListener getOnMatrixChangedListener() {
        return onMatrixChangedListener;
    }

    public void setOnMatrixChangedListener(OnMatrixChangedListener onMatrixChangedListener) {
        this.onMatrixChangedListener = onMatrixChangedListener;
    }

    public interface OnMatrixChangedListener {

        void onMatrixChanged(RectF matrix, PhotoView photoView);

    }
}
