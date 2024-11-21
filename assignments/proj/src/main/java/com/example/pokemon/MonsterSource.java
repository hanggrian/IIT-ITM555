package com.example.pokemon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingState;
import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.schema.NamedUrl;
import com.example.pokemon.rest.schema.Pagination;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import retrofit2.HttpException;

public class MonsterSource extends ListenableFuturePagingSource<Integer, NamedUrl> {
    public static final int LIMIT = 10;

    private final PokeApi api = PokeApi.create();
    private final Executor executor = Executors.newSingleThreadExecutor();

    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, NamedUrl>> loadFuture(
        @NonNull LoadParams<Integer> loadParams
    ) {
        Integer nextPage = loadParams.getKey();
        if (nextPage == null) {
            nextPage = 0;
        }
        return Futures.catching(
            Futures.catching(
                Futures.transform(
                    api.listPokemon(LIMIT, nextPage * LIMIT),
                    this::toLoadResult,
                    executor
                ), HttpException.class,
                LoadResult.Error::new,
                executor
            ), IOException.class,
            LoadResult.Error::new,
            executor
        );
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, NamedUrl> pagingState) {
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }
        LoadResult.Page<Integer, NamedUrl> anchorPage =
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

    private LoadResult<Integer, NamedUrl> toLoadResult(@NonNull Pagination pagination) {
        int currentPage = pagination.getCurrentPage();
        return new LoadResult.Page<>(
            pagination.getResults(),
            pagination.hasPrevious() ? currentPage - 1 : null,
            pagination.hasNext() ? currentPage + 1 : null,
            LoadResult.Page.COUNT_UNDEFINED,
            LoadResult.Page.COUNT_UNDEFINED
        );
    }
}
