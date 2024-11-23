package com.example.pokemon.ui.monster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pokemon.R;
import com.example.pokemon.SimpleAdapter;
import com.example.pokemon.Tasks;
import com.example.pokemon.Urls;
import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.moves.Move;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Objects;
import org.parceler.Parcels;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.EXTRA_SUBJECT;
import static android.content.Intent.EXTRA_TEXT;
import static android.widget.Toast.LENGTH_SHORT;

public class MonsterDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = "MonsterDialogFragment";
    public static final String EXTRA_MOVE = "MonsterDialogFragment#EXTRA_MOVE";

    Toolbar toolbar;
    TextView powerText;
    ProgressBar powerProgress;
    TextView accuracyText;
    ProgressBar accuracyProgress;
    TextView ppText;
    ProgressBar ppProgress;
    TextView effectText;
    ProgressBar effectProgress;
    TextView priorityText;
    ProgressBar priorityProgress;
    TextView descriptionText;
    RecyclerView recycler;

    MonsterAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.dialog_monster, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        toolbar = view.findViewById(R.id.toolbar);
        powerText = view.findViewById(R.id.powerText);
        powerProgress = view.findViewById(R.id.powerProgress);
        accuracyText = view.findViewById(R.id.accuracyText);
        accuracyProgress = view.findViewById(R.id.accuracyProgress);
        ppText = view.findViewById(R.id.ppText);
        ppProgress = view.findViewById(R.id.ppProgress);
        effectText = view.findViewById(R.id.effectText);
        effectProgress = view.findViewById(R.id.effectProgress);
        priorityText = view.findViewById(R.id.priorityText);
        priorityProgress = view.findViewById(R.id.priorityProgress);
        descriptionText = view.findViewById(R.id.descriptionText);
        recycler = view.findViewById(R.id.recycler);

        Bundle bundle = requireArguments();
        Move move = Objects.requireNonNull(Parcels.unwrap(bundle.getParcelable(EXTRA_MOVE)));
        toolbar.setTitle(Urls.getDisplay(move.name));
        toolbar.setSubtitle(Urls.getDisplay(move.type.name));
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.inflateMenu(R.menu.dialolg_monster);
        toolbar.setOnMenuItemClickListener(
            item -> {
                if (item.getItemId() == R.id.shareItem) {
                    if (item.getItemId() == R.id.shareItem) {
                        Intent sendIntent = new Intent(ACTION_SEND);
                        sendIntent.setType("text/plain");
                        sendIntent.putExtra(
                            EXTRA_SUBJECT,
                            String.format("Check out %s!", Urls.getDisplay(move.name))
                        );
                        sendIntent.putExtra(
                            EXTRA_TEXT,
                            String.format(
                                "https://bulbapedia.bulbagarden.net/wiki/%s_(move)",
                                Urls.getDisplay(move.name).replace(' ', '_')
                            )
                        );
                        startActivity(Intent.createChooser(sendIntent, "Share Move"));
                    }
                    return true;
                }
                return false;
            }
        );

        powerText.setText(String.valueOf(move.power));
        powerProgress.setProgress(move.getPowerPercentage());
        accuracyText.setText(String.valueOf(move.accuracy));
        accuracyProgress.setProgress(move.accuracy);
        ppText.setText(String.valueOf(move.pp));
        ppProgress.setProgress(move.getPpPercentage());
        effectText.setText(String.valueOf(move.effectChance));
        effectProgress.setProgress(move.effectChance);
        priorityText.setText(String.valueOf(move.priority));
        priorityProgress.setProgress(move.getPriorityPercentage());

        descriptionText.setText(move.getFullDescription());

        adapter = new MonsterAdapter(requireContext(), move.learnedByPokemon);
        recycler.setAdapter(adapter);
    }

    public static class MonsterAdapter extends
        SimpleAdapter<NamedApiResource, MonsterAdapter.ViewHolder> {
        private final Picasso picasso;
        private final PokeApi api = PokeApi.create();

        public MonsterAdapter(@NonNull Context context, @NonNull List<NamedApiResource> list) {
            super(context, list);
            picasso = new Picasso.Builder(context).build();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_monster, parent, false)
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
                    holder.itemView.setOnClickListener(
                        v -> {
                            Intent intent = new Intent(context, MonsterActivity.class);
                            intent.putExtra(MonsterActivity.EXTRA_POKEMON, Parcels.wrap(pokemon));
                            context.startActivity(intent);
                        }
                    );
                    holder.itemView.setOnLongClickListener(
                        v -> {
                            Toast
                                .makeText(context, Urls.getDisplay(pokemon.name), LENGTH_SHORT)
                                .show();
                            return false;
                        }
                    );
                }
            );
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
    }
}
