package com.move4mobile.lichtstad.util;

import android.content.res.Resources;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;

public class ResourceFloatUtil {

    public static float getFloat(@NonNull Resources resources, @DimenRes int id) {
        TypedValue typedValue = new TypedValue();
        resources.getValue(id, typedValue, true);
        return typedValue.getFloat();
    }

}
