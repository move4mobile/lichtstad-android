package com.move4mobile.lichtstad.photo.detail;

import android.graphics.RectF;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.github.chrisbanes.photoview.PhotoView;
import com.move4mobile.lichtstad.databinding.ListItemPhotoDetailBinding;
import com.move4mobile.lichtstad.model.Photo;
import com.move4mobile.lichtstad.widget.FirebaseViewPagerAdapter;


public class PhotoPagerAdapter extends FirebaseViewPagerAdapter<Photo> {

    private OnMatrixChangedListener onMatrixChangedListener;
    private OnDataChangedListener onDataChangedListener;

    public PhotoPagerAdapter(FirebaseRecyclerOptions<Photo> options) {
        super(options);
    }

    @Override
    protected View getView(Photo object, ViewGroup container) {
        ListItemPhotoDetailBinding binding = ListItemPhotoDetailBinding.inflate(LayoutInflater.from(container.getContext()), container, false);
        binding.setPhoto(object);
        final PhotoView photoView = binding.photoView;
        binding.photoView.setOnMatrixChangeListener(rect -> {
            if (onMatrixChangedListener != null) {
                onMatrixChangedListener.onMatrixChanged(rect, photoView);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void stopListening() {
        notifyDataSetChanged();
        super.stopListening();
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        if (onDataChangedListener != null) {
            onDataChangedListener.onDataChanged();
        }
    }

    public ObservableSnapshotArray<Photo> getSnapshots() {
        return mSnapshots;
    }

    public OnMatrixChangedListener getOnMatrixChangedListener() {
        return onMatrixChangedListener;
    }

    public void setOnMatrixChangedListener(OnMatrixChangedListener onMatrixChangedListener) {
        this.onMatrixChangedListener = onMatrixChangedListener;
    }

    public OnDataChangedListener getOnDataChangedListener() {
        return onDataChangedListener;
    }

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        this.onDataChangedListener = onDataChangedListener;
    }

    public interface OnMatrixChangedListener {

        void onMatrixChanged(RectF matrix, PhotoView photoView);

    }

    public interface OnDataChangedListener {

        void onDataChanged();

    }
}
