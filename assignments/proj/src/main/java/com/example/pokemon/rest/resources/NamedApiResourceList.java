package com.example.pokemon.rest.resources;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import com.example.pokemon.rest.utilities.NamedApiResource;
import java.net.URI;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class NamedApiResourceList {
    public int count;
    public String next;
    public String previous;
    public List<NamedApiResource> results;

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasPrevious() {
        return previous != null;
    }

    public int getCurrentPage() {
        if (!hasPrevious()) {
            return 0;
        }
        Pair<Integer, Integer> pair = getOffsetLimitPair(previous);
        return (pair.first / pair.second) + 1;
    }

    @NonNull
    private static Pair<Integer, Integer> getOffsetLimitPair(@NonNull String url) {
        try {
            String query = URI.create(url).getQuery();
            String offset = null;
            String limit = null;
            for (String pair : query.split("&")) {
                int idx = pair.indexOf("=");
                if (idx <= 0) {
                    continue;
                }
                String key = pair.substring(0, idx);
                String value = pair.substring(idx + 1);
                if (key.equals("offset")) {
                    offset = value;
                    continue;
                }
                if (!key.equals("limit")) {
                    continue;
                }
                limit = value;
            }
            if (offset == null || limit == null) {
                throw new IllegalArgumentException();
            }
            return new Pair<>(Integer.parseInt(offset), Integer.parseInt(limit));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid URL format.", e);
        }
    }
}
