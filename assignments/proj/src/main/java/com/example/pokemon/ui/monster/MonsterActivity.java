package com.example.pokemon.ui.monster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pokemon.Contexts;
import com.example.pokemon.R;
import com.example.pokemon.SimpleAdapter;
import com.example.pokemon.Tasks;
import com.example.pokemon.Urls;
import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.evolution.ChainLink;
import com.example.pokemon.rest.moves.Move;
import com.example.pokemon.rest.pokemon.Pokemon;
import com.example.pokemon.rest.pokemon.PokemonSpecies;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.example.pokemon.ui.main.AboutDialogFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.parceler.Parcels;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MonsterActivity extends AppCompatActivity {
    public static final String EXTRA_POKEMON = "MonsterActivity#EXTRA_POKEMON";

    CollapsingToolbarLayout toolbarLayout;
    Toolbar toolbar;
    FloatingActionButton action;
    ImageView image;
    LinearLayout evolutionLayout;
    RecyclerView recycler;

    PokeApi api;
    MoveAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_monster);
        toolbarLayout = findViewById(R.id.toolbarLayout);
        toolbar = findViewById(R.id.toolbar);
        action = findViewById(R.id.action);
        image = findViewById(R.id.image);
        evolutionLayout = findViewById(R.id.evolutionLayout);
        recycler = findViewById(R.id.recycler);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Pokemon pokemon =
            Objects.requireNonNull(Parcels.unwrap(intent.getParcelableExtra(EXTRA_POKEMON)));
        toolbarLayout.setTitle(Urls.getDisplay(pokemon.name));
        Picasso
            .get()
            .load(pokemon.sprites.frontDefault)
            .into(image);

        adapter =
            new MoveAdapter(
                this,
                pokemon.moves
                    .stream()
                    .map(pokemonMove -> pokemonMove.move)
                    .collect(Collectors.toList())
            );
        recycler.setAdapter(adapter);

        api = PokeApi.create();
        Tasks.executeResult(
            () -> {
                PokemonSpecies species =
                    api.getPokemonSpecies(Urls.getId(pokemon.species.url)).get();
                return api.getEvolutionChain(Urls.getId(species.evolutionChain.url)).get();
            },
            evolutionChain -> {
                List<PokemonSpecies> speciesList = new ArrayList<>();
                speciesList.add(evolutionChain.chain.species);
                List<ChainLink> links = evolutionChain.chain.evolvesTo;
                while (!links.isEmpty()) {
                    ChainLink link = links.get(0);
                    speciesList.add(link.species);
                    links = link.evolvesTo;
                }
                for (int i = 0; i < speciesList.size(); i++) {
                    PokemonSpecies species = speciesList.get(i);
                    Button evolutionButton =
                        new Button(
                            new ContextThemeWrapper(
                                this,
                                R.style.Widget_Button_Evolution
                            ), null,
                            R.style.Widget_Button_Evolution
                        );
                    evolutionButton.setLayoutParams(
                        new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    );
                    evolutionButton.setBackgroundResource(
                        Contexts.getAttr(this, android.R.attr.selectableItemBackground)
                    );
                    evolutionButton.setText(Urls.getDisplay(species.name));
                    evolutionButton.setEnabled(!species.name.equals(pokemon.name));
                    evolutionButton.setOnClickListener(
                        v ->
                            Tasks.executeResult(
                                () -> api.getPokemon(species.name).get(),
                                pokemon2 -> {
                                    Intent intent2 = new Intent(this, MonsterActivity.class);
                                    intent2.putExtra(EXTRA_POKEMON, Parcels.wrap(pokemon2));
                                    startActivity(intent2);
                                }
                            )
                    );
                    evolutionLayout.addView(evolutionButton);

                    if (i == speciesList.size() - 1) {
                        return;
                    }

                    ImageView evolutionChevron = new ImageView(this);
                    evolutionChevron.setLayoutParams(
                        new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                    );
                    evolutionChevron.setImageResource(R.drawable.ic_chevron);
                    evolutionLayout.addView(evolutionChevron);
                }
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_monster, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.aboutItem) {
            new AboutDialogFragment().show(getSupportFragmentManager(), AboutDialogFragment.TAG);
        }
        return super.onOptionsItemSelected(item);
    }

    public static class MoveAdapter extends
        SimpleAdapter<NamedApiResource, MoveAdapter.ViewHolder> {
        private final PokeApi api = PokeApi.create();

        public MoveAdapter(@NonNull Context context, @NonNull List<NamedApiResource> list) {
            super(context, list);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_move, parent, false)
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
            TextView nameText;
            TextView powerText;
            TextView accuracyText;
            TextView ppText;
            TextView descriptionText;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nameText = itemView.findViewById(R.id.nameText);
                powerText = itemView.findViewById(R.id.powerText);
                accuracyText = itemView.findViewById(R.id.accuracyText);
                ppText = itemView.findViewById(R.id.ppText);
                descriptionText = itemView.findViewById(R.id.descriptionText);
            }
        }
    }
}
