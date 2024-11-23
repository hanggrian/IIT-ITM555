package com.example.pokemon.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.pokemon.LoadAdapter;
import com.example.pokemon.PageAdapter;
import com.example.pokemon.R;
import com.example.pokemon.Tasks;
import com.example.pokemon.Urls;
import com.example.pokemon.databinding.FragmentMonsterBinding;
import com.example.pokemon.rest.NamedApiResourceComparator;
import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.example.pokemon.ui.monster.MonsterActivity;
import com.squareup.picasso.Picasso;
import java.util.Objects;
import org.parceler.Parcels;

public class MonsterFragment extends MainFragment {
    SwipeRefreshLayout refreshLayout;
    RecyclerView recycler;

    MonsterPageAdapter adapter;

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
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recycler = view.findViewById(R.id.recycler);

        adapter = new MonsterPageAdapter(requireContext(), new NamedApiResourceComparator());
        recycler.setAdapter(
            adapter.withLoadStateFooter(new MonsterLoadAdapter(() -> adapter.retry()))
        );
        refreshLayout.setOnRefreshListener(this::refresh);

        getViewModel().monsterData.observe(
            requireActivity(),
            data -> adapter.submitData(getViewLifecycleOwner().getLifecycle(), data)
        );

        refresh();
    }

    private void refresh() {
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

        public MonsterPageAdapter(
            @NonNull Context context,
            @NonNull DiffUtil.ItemCallback<NamedApiResource> diffCallback
        ) {
            super(context, diffCallback);
            picasso = new Picasso.Builder(context).build();
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
                        v -> {
                            Intent intent = new Intent(context, MonsterActivity.class);
                            intent.putExtra(MonsterActivity.EXTRA_POKEMON, Parcels.wrap(pokemon));
                            context.startActivity(intent);
                        }
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
