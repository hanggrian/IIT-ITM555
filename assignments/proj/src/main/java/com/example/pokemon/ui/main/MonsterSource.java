package com.example.pokemon.ui.main;

import androidx.annotation.NonNull;
import com.example.pokemon.ui.Source;
import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.resources.NamedApiResourceList;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import retrofit2.HttpException;

public class MonsterSource extends Source<NamedApiResource> {
    public static final int LIMIT = 10;

    private final PokeApi api = PokeApi.create();
    private final Executor executor = Executors.newSingleThreadExecutor();

    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, NamedApiResource>> loadFuture(
        @NonNull LoadParams<Integer> loadParams
    ) {
        Integer nextPage = loadParams.getKey();
        if (nextPage == null) {
            nextPage = 0;
        }
        return Futures.catching(
            Futures.catching(
                Futures.transform(
                    api.listPokemons(LIMIT, nextPage * LIMIT),
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

    private LoadResult<Integer, NamedApiResource> toLoadResult(
        @NonNull NamedApiResourceList list
    ) {
        int currentPage = list.getCurrentPage();
        return new LoadResult.Page<>(
            list.results,
            list.hasPrevious() ? currentPage - 1 : null,
            list.hasNext() ? currentPage + 1 : null,
            LoadResult.Page.COUNT_UNDEFINED,
            LoadResult.Page.COUNT_UNDEFINED
        );
    }
}
