package com.example.pokemon;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;
import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.schema.NamedUrl;

public class MainViewModel extends ViewModel {
    @NonNull
    public final PokeApi api = PokeApi.create();

    @NonNull
    public final LiveData<PagingData<NamedUrl>> monsterData =
        PagingLiveData.cachedIn(
            PagingLiveData.getLiveData(
                new Pager<>(new PagingConfig(MonsterSource.LIMIT), 0, MonsterSource::new)
            ), ViewModelKt.getViewModelScope(this)
        );
}
