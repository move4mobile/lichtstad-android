package com.move4mobile.lichtstad.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

public class YoutubePresenter {

    public static void watchYoutubeVideo(@NonNull Context context, @NonNull String id) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id)));
    }

}
