package com.example.pokemon.ui.main;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.pokemon.LoadAdapter;
import com.example.pokemon.PageAdapter;
import com.example.pokemon.R;
import com.example.pokemon.Tasks;
import com.example.pokemon.Urls;
import com.example.pokemon.databinding.FragmentMoveBinding;
import com.example.pokemon.rest.NamedApiResourceComparator;
import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.moves.Move;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.example.pokemon.ui.monster.MonsterDialogFragment;
import com.squareup.picasso.Picasso;
import java.util.Objects;
import org.parceler.Parcels;

public class MoveFragment extends MainFragment {
    SwipeRefreshLayout refreshLayout;
    RecyclerView recycler;

    MovePageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        return FragmentMoveBinding.inflate(inflater).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        recycler = view.findViewById(R.id.recycler);

        adapter = new MovePageAdapter(requireContext(), new NamedApiResourceComparator());
        recycler.setAdapter(
            adapter.withLoadStateFooter(new MoveLoadAdapter(() -> adapter.retry()))
        );
        refreshLayout.setOnRefreshListener(this::refresh);

        getViewModel().moveData.observe(
            requireActivity(),
            data -> adapter.submitData(getViewLifecycleOwner().getLifecycle(), data)
        );

        refresh();
    }

    private void refresh() {
        refreshLayout.setRefreshing(false);
    }

    public static class MoveLoadAdapter extends LoadAdapter<MoveLoadAdapter.ViewHolder> {
        public MoveLoadAdapter(@NonNull Runnable retryAction) {
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
                    .inflate(R.layout.item_move_load, parent, false)
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

    public static class MovePageAdapter extends
        PageAdapter<NamedApiResource, MovePageAdapter.ViewHolder> {
        private final Picasso picasso;
        private final PokeApi api = PokeApi.create();

        public MovePageAdapter(
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
                    .inflate(R.layout.item_move_page, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Tasks.executeResult(
                () -> {
                    Move move = api.getMove(Objects.requireNonNull(getItem(position)).name).get();
                    return new Pair<>(
                        move,
                        move.type == null
                            ? null
                            : api
                            .getType(move.type.name)
                            .get()
                            .sprites
                            .generation3
                            .fireredLeafgreen
                            .nameIcon
                    );
                }, pair -> {
                    (pair.second != null
                        ? picasso.load(pair.second)
                        : picasso.load(R.drawable.ic_placeholder))
                        .into(holder.image);
                    holder.nameText.setText(Urls.getDisplay(pair.first.name));
                    holder.powerText.setText(String.valueOf(pair.first.power));
                    holder.accuracyText.setText(String.valueOf(pair.first.accuracy));
                    holder.ppText.setText(String.valueOf(pair.first.pp));
                    holder.descriptionText.setText(pair.first.getShortDescription());
                    holder.itemView.setOnClickListener(
                        v -> {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(
                                MonsterDialogFragment.EXTRA_MOVE,
                                Parcels.wrap(pair.first)
                            );

                            DialogFragment fragment = new MonsterDialogFragment();
                            fragment.setArguments(bundle);
                            fragment.show(
                                ((AppCompatActivity) context).getSupportFragmentManager(),
                                MonsterDialogFragment.TAG
                            );
                        }
                    );
                }
            );
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView nameText;
            TextView powerText;
            TextView accuracyText;
            TextView ppText;
            TextView descriptionText;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                nameText = itemView.findViewById(R.id.nameText);
                powerText = itemView.findViewById(R.id.powerText);
                accuracyText = itemView.findViewById(R.id.accuracyText);
                ppText = itemView.findViewById(R.id.ppText);
                descriptionText = itemView.findViewById(R.id.descriptionText);
            }
        }
    }
}
