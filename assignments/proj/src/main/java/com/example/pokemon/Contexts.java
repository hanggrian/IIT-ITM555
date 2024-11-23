package com.example.pokemon;

import android.content.Context;
import android.util.TypedValue;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public final class Contexts {
    private Contexts() {}

    @DrawableRes
    public static int getAttr(@NonNull Context context, int attrId) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attrId, value, true);
        return value.resourceId;
    }
}
