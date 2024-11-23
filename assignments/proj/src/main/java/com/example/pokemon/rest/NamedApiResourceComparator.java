package com.example.pokemon.rest;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import com.example.pokemon.rest.utilities.NamedApiResource;
import java.util.Objects;

public class NamedApiResourceComparator extends DiffUtil.ItemCallback<NamedApiResource> {
    @Override
    public boolean areItemsTheSame(
        @NonNull NamedApiResource oldItem,
        @NonNull NamedApiResource newItem
    ) {
        return oldItem.name.equals(newItem.name);
    }

    @Override
    public boolean areContentsTheSame(
        @NonNull NamedApiResource oldItem,
        @NonNull NamedApiResource newItem
    ) {
        return Objects.equals(oldItem.name, newItem.name)
            && Objects.equals(oldItem.url, newItem.url);
    }
}
