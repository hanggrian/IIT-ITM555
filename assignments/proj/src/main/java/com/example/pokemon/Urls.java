package com.example.pokemon;

import androidx.annotation.NonNull;
import org.apache.commons.text.WordUtils;

public final class Urls {
    private Urls() {}

    @NonNull
    public static String getDisplay(@NonNull String urlForm) {
        return WordUtils.capitalizeFully(urlForm.replace('-', ' '));
    }

    public static int getId(@NonNull String urlForm) {
        if (urlForm.endsWith("/")) {
            urlForm = urlForm.substring(0, urlForm.length() - 1);
        }
        return Integer.parseInt(urlForm.substring(urlForm.lastIndexOf('/') + 1));
    }
}
