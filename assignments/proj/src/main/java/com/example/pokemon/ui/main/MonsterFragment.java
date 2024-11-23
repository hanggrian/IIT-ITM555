package com.example.pokemon.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.pokemon.R;
import com.example.pokemon.Tasks;
import com.example.pokemon.Urls;
import com.example.pokemon.databinding.FragmentMonsterBinding;
import com.example.pokemon.rest.NamedApiResourceComparator;
import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.example.pokemon.ui.LoadAdapter;
import com.example.pokemon.ui.PageAdapter;
import com.squareup.picasso.Picasso;
import java.util.Objects;

public class MonsterFragment extends MainFragment {
    SwipeRefreshLayout refreshLayout;
    RecyclerView recycler;

    MainObserver observer;
    MonsterPageAdapter adapter;

    @Override
    protected int getMenuRes() {
        return R.menu.fragment_monster;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observer = new MainObserver((MainActivity) requireActivity());
        getLifecycle().addObserver(observer);
    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        return FragmentMonsterBinding.inflate(inflater).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recycler = view.findViewById(R.id.recycler);

        getViewModel().monsterSpan.observe(
            getViewLifecycleOwner(),
            span ->
                Objects
                    .requireNonNull((GridLayoutManager) recycler.getLayoutManager())
                    .setSpanCount(span)
        );

        adapter =
            new MonsterPageAdapter(requireContext(), new NamedApiResourceComparator(), observer);
        recycler.setAdapter(
            adapter.withLoadStateFooter(new MonsterLoadAdapter(() -> adapter.retry()))
        );
        refreshLayout.setOnRefreshListener(this::refresh);

        refresh();
        Objects.requireNonNull(getViewModel().monsterData).observe(
            requireActivity(),
            data -> adapter.submitData(getViewLifecycleOwner().getLifecycle(), data)
        );
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() != R.id.largeLayoutItem
            && item.getItemId() != R.id.mediumLayoutItem
            && item.getItemId() != R.id.smallLayoutItem
        ) {
            return false;
        }
        if (item.getItemId() == R.id.largeLayoutItem) {
            getViewModel().monsterSpan.setValue(2);
        } else if (item.getItemId() == R.id.mediumLayoutItem) {
            getViewModel().monsterSpan.setValue(3);
        } else if (item.getItemId() == R.id.smallLayoutItem) {
            getViewModel().monsterSpan.setValue(4);
        }
        item.setChecked(true);
        return true;
    }

    private void refresh() {
        getViewModel().refreshMonster();
        adapter.refresh();
        refreshLayout.setRefreshing(false);
    }

    public static class MonsterLoadAdapter extends LoadAdapter<MonsterLoadAdapter.ViewHolder> {
        public MonsterLoadAdapter(@NonNull Runnable retryAction) {
            super(retryAction);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @NonNull LoadState loadState) {
            if (!(loadState instanceof LoadState.Error)) {
                holder.progress.setVisibility(View.VISIBLE);
                holder.button.setVisibility(View.GONE);
                return;
            }
            holder.progress.setVisibility(View.GONE);
            holder.button.setVisibility(View.VISIBLE);
            holder.button.setOnClickListener(v -> retryAction.run());
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            @NonNull LoadState loadState
        ) {
            return new ViewHolder(
                LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_monster_load, parent, false)
            );
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ProgressBar progress;
            Button button;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                progress = itemView.findViewById(R.id.progress);
                button = itemView.findViewById(R.id.button);
            }
        }
    }

    public static class MonsterPageAdapter extends
        PageAdapter<NamedApiResource, MonsterPageAdapter.ViewHolder> {
        private final Picasso picasso;
        private final PokeApi api = PokeApi.create();
        private final MainObserver observer;

        public MonsterPageAdapter(
            @NonNull Context context,
            @NonNull DiffUtil.ItemCallback<NamedApiResource> diffCallback,
            @NonNull MainObserver observer
        ) {
            super(context, diffCallback);
            picasso = new Picasso.Builder(context).build();
            this.observer = observer;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_monster_page, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Tasks.executeResult(
                () -> api.getPokemon(Objects.requireNonNull(getItem(position)).name).get(),
                pokemon -> {
                    picasso
                        .load(pokemon.sprites.frontDefault)
                        .into(holder.image);
                    holder.text.setText(Urls.getDisplay(pokemon.name));
                    holder.itemView.setOnClickListener(
                        v -> observer.launchMonster(context, pokemon)
                    );
                }
            );
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView text;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                text = itemView.findViewById(R.id.text);
            }
        }
    }
}
