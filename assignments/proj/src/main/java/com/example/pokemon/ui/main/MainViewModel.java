package com.example.pokemon.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;
import androidx.paging.PagingSource;
import com.example.pokemon.db.schema.Member;
import com.example.pokemon.rest.utilities.NamedApiResource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MainViewModel extends ViewModel {
    @NonNull
    public final LiveData<PagingData<NamedApiResource>> monsterData =
        indexedCachedLiveData(this, MonsterSource.LIMIT, MonsterSource::new);

    @NonNull
    public final LiveData<PagingData<NamedApiResource>> moveData =
        indexedCachedLiveData(this, MoveSource.LIMIT, MoveSource::new);

    @NonNull
    public final List<Member> memberList = new ArrayList<>();

    private static <T> LiveData<PagingData<T>> indexedCachedLiveData(
        @NonNull ViewModel viewModel,
        int limit,
        @NonNull Supplier<PagingSource<Integer, T>> sourceSupplier
    ) {
        return PagingLiveData.cachedIn(
            PagingLiveData.getLiveData(
                new Pager<>(new PagingConfig(limit), 0, sourceSupplier::get)
            ), ViewModelKt.getViewModelScope(viewModel)
        );
    }
}
