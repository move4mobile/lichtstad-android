package com.move4mobile.lichtstad.util;

import android.content.res.Resources;
import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import android.util.TypedValue;

public class ResourceFloatUtil {

    public static float getFloat(@NonNull Resources resources, @DimenRes int id) {
        TypedValue typedValue = new TypedValue();
        resources.getValue(id, typedValue, true);
        return typedValue.getFloat();
    }

}
