package com.example.quotes;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A group of Steve Jobs' picture and quote resource IDs in array lists. This class is serializable
 * because it is passed to {@link QuoteDetailActivity} using
 * {@link android.content.Intent#getSerializableExtra}.
 */
public final class DataSource implements Serializable {
    private final List<Integer> quotes = new ArrayList<>();
    private final List<Integer> photos = new ArrayList<>();
    private final List<Integer> photoThumbnails = new ArrayList<>();

    /**
     * The default constructor, initialize array lists upon creation.
     */
    public DataSource() {
        setupQuotes();
        setupPhotos();
        setupPhotoThumbnails();
    }

    /**
     * Returns collection of quote resource IDs.
     */
    @NonNull
    public List<Integer> getQuotes() {
        return quotes;
    }

    /**
     * Returns collection of high-quality photos resource IDs.
     */
    @NonNull
    public List<Integer> getPhotos() {
        return photos;
    }

    /**
     * Returns collection of thumbnail photos resource IDs.
     */
    @NonNull
    public List<Integer> getPhotoThumbnails() {
        return photoThumbnails;
    }

    /**
     * Returns the current size.
     */
    public int getLength() {
        return photos.size();
    }

    private void setupQuotes() {
        quotes.add(R.string.quote1);
        quotes.add(R.string.quote2);
        quotes.add(R.string.quote3);
        quotes.add(R.string.quote4);
        quotes.add(R.string.quote5);
        quotes.add(R.string.quote6);
        quotes.add(R.string.quote7);
        quotes.add(R.string.quote8);
        quotes.add(R.string.quote9);
        quotes.add(R.string.quote10);
    }

    private void setupPhotos() {
        photos.add(R.drawable.steve1);
        photos.add(R.drawable.steve2);
        photos.add(R.drawable.steve3);
        photos.add(R.drawable.steve4);
        photos.add(R.drawable.steve5);
        photos.add(R.drawable.steve6);
        photos.add(R.drawable.steve7);
        photos.add(R.drawable.steve8);
        photos.add(R.drawable.steve9);
        photos.add(R.drawable.apple);
    }

    private void setupPhotoThumbnails() {
        photoThumbnails.add(R.drawable.steve1_thumb);
        photoThumbnails.add(R.drawable.steve2_thumb);
        photoThumbnails.add(R.drawable.steve3_thumb);
        photoThumbnails.add(R.drawable.steve4_thumb);
        photoThumbnails.add(R.drawable.steve5_thumb);
        photoThumbnails.add(R.drawable.steve6_thumb);
        photoThumbnails.add(R.drawable.steve7_thumb);
        photoThumbnails.add(R.drawable.steve8_thumb);
        photoThumbnails.add(R.drawable.steve9_thumb);
        photoThumbnails.add(R.drawable.steve10_thumb);
    }
}
