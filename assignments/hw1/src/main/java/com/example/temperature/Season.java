package com.example.temperature;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import java.util.Objects;

public enum Season {
    WINTER(R.drawable.winter, R.color.winter, R.string.winter),
    SUMMER(R.drawable.summer, R.color.summer, R.string.summer),
    SPRING(R.drawable.spring, R.color.spring, R.string.spring),
    AUTUMN(R.drawable.autumn, R.color.autumn, R.string.autumn),
    ERROR(R.drawable.error, R.color.error, R.string.error);

    private final int drawableId;
    private final int colorId;
    private final int textId;

    Season(@DrawableRes int drawableId, @ColorRes int colorId, @StringRes int textId) {
        this.drawableId = drawableId;
        this.colorId = colorId;
        this.textId = textId;
    }

    @NonNull
    public Drawable getDrawable(@NonNull Context context) {
        return Objects.requireNonNull(ContextCompat.getDrawable(context, drawableId));
    }

    @ColorInt
    public int getColor(@NonNull Context context) {
        return ContextCompat.getColor(context, colorId);
    }

    @NonNull
    public CharSequence getText(@NonNull Context context) {
        return context.getText(textId);
    }
}
