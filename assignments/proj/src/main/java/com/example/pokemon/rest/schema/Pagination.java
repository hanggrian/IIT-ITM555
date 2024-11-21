package com.example.pokemon.rest.schema;

import android.util.Pair;
import androidx.annotation.NonNull;
import java.net.URI;
import java.util.List;

public class Pagination {
    private int count;
    private String next;
    private String previous;
    private List<NamedUrl> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<NamedUrl> getResults() {
        return results;
    }

    public void setResults(List<NamedUrl> results) {
        this.results = results;
    }

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
