package com.example.pokemon;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    public static void sendViewIntent(@NonNull Context context, @NonNull String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static void sendShareIntent(
        @NonNull Context context,
        @NonNull String message,
        @NonNull String url
    ) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, message);
        intent.putExtra(Intent.EXTRA_TEXT, url);
        context.startActivity(Intent.createChooser(intent, "Share Move"));
    }
}
