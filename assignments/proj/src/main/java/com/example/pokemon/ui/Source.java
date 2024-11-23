package com.example.pokemon.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingState;

public abstract class Source<T> extends ListenableFuturePagingSource<Integer, T> {
    @Nullable
    @Override
    public final Integer getRefreshKey(@NonNull PagingState<Integer, T> pagingState) {
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }
        LoadResult.Page<Integer, T> anchorPage =
            pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }
        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }
        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }
        return null;
    }
}
