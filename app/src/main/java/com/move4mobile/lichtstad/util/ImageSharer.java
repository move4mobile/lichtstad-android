package com.move4mobile.lichtstad.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import com.move4mobile.lichtstad.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSharer {

    public static final String AUTHORITY = "nl.gramsbergen.oranjevereniging.lichtstad.ShareFileProvider";
    private final Activity context;

    public ImageSharer(Activity context) {
        this.context = context;
    }

    public void shareImage(@NonNull final String imageUrl) {
        Picasso.with(context)
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        String mimeType = null;
                        String extension = MimeTypeMap.getFileExtensionFromUrl(imageUrl);
                        if (extension != null) {
                            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
                        }
                        if (mimeType == null) {
                            mimeType = "image/*";
                        }

                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                        shareIntent.setType(mimeType);
                        context.startActivity(Intent.createChooser(shareIntent, context.getText(R.string.share_to)));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {}

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {}
                });
    }

    private Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File directory = new File(context.getCacheDir(), "shares");
            if (!directory.exists() && !directory.mkdirs()) {
                return null;
            }
            File file =  new File(directory, "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(context, AUTHORITY, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

}
