package com.example.pokemon;

import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.pokemon.databinding.FragmentMonsterBinding;
import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.schema.NamedUrl;
import com.example.pokemon.rest.schema.Pokemon;
import com.squareup.picasso.Picasso;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import org.apache.commons.text.WordUtils;

public class MonsterFragment extends Fragment {
    SwipeRefreshLayout refreshLayout;
    RecyclerView recycler;

    PageAdapter adapter;

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

        adapter = new PageAdapter(requireContext(), new PageComparator());
        recycler.setAdapter(adapter.withLoadStateFooter(new LoadAdapter(() -> adapter.retry())));
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

    @NonNull
    private MainViewModel getViewModel() {
        return ((MainActivity) requireActivity()).viewModel;
    }

    public static class LoadAdapter extends LoadStateAdapter<LoadAdapter.ViewHolder> {
        private final Runnable retryAction;

        public LoadAdapter(@NonNull Runnable retryAction) {
            this.retryAction = retryAction;
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

    public static class PageAdapter extends PagingDataAdapter<NamedUrl, PageAdapter.ViewHolder> {
        private final PokeApi api = PokeApi.create();
        private final Picasso picasso;

        public PageAdapter(
            @NonNull Context context,
            @NonNull DiffUtil.ItemCallback<NamedUrl> diffCallback
        ) {
            super(diffCallback);
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
            NamedUrl url = Objects.requireNonNull(getItem(position));
            Pokemon pokemon;
            try {
                pokemon = Objects.requireNonNull(api.getPokemon(url.getName()).get());
            } catch (ExecutionException | InterruptedException e) {
                return;
            }
            picasso.load(pokemon.getSprites().getFrontDefault()).into(holder.image);
            holder.text.setText(WordUtils.capitalize(pokemon.getName()));
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

    public static class PageComparator extends DiffUtil.ItemCallback<NamedUrl> {
        @Override
        public boolean areItemsTheSame(@NonNull NamedUrl oldItem, @NonNull NamedUrl newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull NamedUrl oldItem, @NonNull NamedUrl newItem) {
            return oldItem.equals(newItem);
        }
    }
}
