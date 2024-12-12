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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pokemon.Contexts;
import com.example.pokemon.R;
import com.example.pokemon.Tasks;
import com.example.pokemon.Urls;
import com.example.pokemon.db.Members;
import com.example.pokemon.db.schema.Member;
import com.example.pokemon.rest.PokeApi;
import com.example.pokemon.rest.evolution.ChainLink;
import com.example.pokemon.rest.moves.Move;
import com.example.pokemon.rest.pokemon.Pokemon;
import com.example.pokemon.rest.pokemon.PokemonSpecies;
import com.example.pokemon.rest.pokemon.PokemonStat;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.example.pokemon.ui.NextActivity;
import com.example.pokemon.ui.SimpleAdapter;
import com.example.pokemon.ui.main.MainActivity;
import com.example.pokemon.ui.move.MoveDialogFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.parceler.Parcels;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MonsterActivity extends NextActivity {
    public static final String EXTRA_POKEMON = "MonsterActivity#EXTRA_POKEMON";

    CollapsingToolbarLayout toolbarLayout;
    FloatingActionButton action;
    TextView hpStatText;
    ProgressBar hpStatProgress;
    TextView atkStatText;
    ProgressBar atkStatProgress;
    TextView defStatText;
    ProgressBar defStatProgress;
    TextView spcAtkStatText;
    ProgressBar spcAtkStatProgress;
    TextView spcDefStatText;
    ProgressBar spcDefStatProgress;
    TextView spdStatText;
    ProgressBar spdStatProgress;
    RecyclerView galleryRecycler;
    LinearLayout evolutionLayout;
    RecyclerView moveRecycler;

    Pokemon pokemon;
    PokeApi api;

    GalleryAdapter galleryAdapter;
    MoveAdapter moveAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_monster;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbarLayout = findViewById(R.id.toolbarLayout);
        action = findViewById(R.id.action);
        evolutionLayout = findViewById(R.id.evolutionLayout);
        hpStatText = findViewById(R.id.hpStatText);
        hpStatProgress = findViewById(R.id.hpStatProgress);
        atkStatText = findViewById(R.id.atkStatText);
        atkStatProgress = findViewById(R.id.atkStatProgress);
        defStatText = findViewById(R.id.defStatText);
        defStatProgress = findViewById(R.id.defStatProgress);
        spcAtkStatText = findViewById(R.id.spcAtkStatText);
        spcAtkStatProgress = findViewById(R.id.spcAtkStatProgress);
        spcDefStatText = findViewById(R.id.spcDefStatText);
        spcDefStatProgress = findViewById(R.id.spcDefStatProgress);
        spdStatText = findViewById(R.id.spdStatText);
        galleryRecycler = findViewById(R.id.galleryRecycler);
        spdStatProgress = findViewById(R.id.spdStatProgress);
        moveRecycler = findViewById(R.id.moveRecycler);

        Intent intent = getIntent();
        pokemon = Objects.requireNonNull(Parcels.unwrap(intent.getParcelableExtra(EXTRA_POKEMON)));
        api = PokeApi.create();
        Members members = db.members();

        setTitle(Urls.getDisplay(pokemon.name));

        pokemon.stats.forEach(
            pokemonStat -> {
                String stat = String.valueOf(pokemonStat.baseStat);
                switch (pokemonStat.stat.name) {
                    case PokemonStat.HP:
                        hpStatText.setText(stat);
                        hpStatProgress.setProgress(pokemonStat.getPercentage(PokemonStat.MAX_HP));
                        break;
                    case PokemonStat.ATK:
                        atkStatText.setText(stat);
                        atkStatProgress.setProgress(pokemonStat.getPercentage(PokemonStat.MAX_ATK));
                        break;
                    case PokemonStat.DEF:
                        defStatText.setText(stat);
                        defStatProgress.setProgress(pokemonStat.getPercentage(PokemonStat.MAX_DEF));
                        break;
                    case PokemonStat.SPC_ATK:
                        spcAtkStatText.setText(stat);
                        spcAtkStatProgress.setProgress(
                            pokemonStat.getPercentage(PokemonStat.MAX_SPC_ATK)
                        );
                        break;
                    case PokemonStat.SPC_DEF:
                        spcDefStatText.setText(stat);
                        spcDefStatProgress.setProgress(
                            pokemonStat.getPercentage(PokemonStat.MAX_SPC_DEF)
                        );
                        break;
                    case PokemonStat.SPD:
                        spdStatText.setText(stat);
                        spdStatProgress.setProgress(pokemonStat.getPercentage(PokemonStat.MAX_SPD));
                        break;
                }
            }
        );

        galleryAdapter =
            new GalleryAdapter(
                this,
                Stream
                    .of(
                        pokemon.sprites.frontDefault,
                        pokemon.sprites.frontShiny,
                        pokemon.sprites.frontFemale,
                        pokemon.sprites.frontShinyFemale,
                        pokemon.sprites.backDefault,
                        pokemon.sprites.backShiny,
                        pokemon.sprites.backFemale,
                        pokemon.sprites.backShinyFemale
                    ).filter(Objects::nonNull)
                    .collect(Collectors.toList())
            );
        galleryRecycler.setAdapter(galleryAdapter);
        moveAdapter =
            new MoveAdapter(
                this,
                pokemon.moves
                    .stream()
                    .map(pokemonMove -> pokemonMove.move)
                    .collect(Collectors.toList())
            );
        moveRecycler.setAdapter(moveAdapter);
        action.setOnClickListener(
            v ->
                Tasks.executeResult(
                    () -> {
                        Member member =
                            new Member.Builder()
                                .pokemon(pokemon.name)
                                .sprite(pokemon.sprites.frontDefault)
                                .build();
                        members.insertAll(member);
                        return member;
                    }, (member) -> {
                        action.setVisibility(View.GONE);
                        Toast
                            .makeText(
                                this,
                                getString(
                                    R.string.btn_roster_add_msg,
                                    Urls.getDisplay(pokemon.name)
                                ), Toast.LENGTH_SHORT
                            ).show();

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(MainActivity.EXTRA_MEMBER, member);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                )
        );

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
                        Contexts.getAttrId(this, android.R.attr.selectableItemBackground)
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
        Tasks.executeIf(() -> members.getCount() >= 6, () -> action.setVisibility(View.GONE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_monster, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.shareItem) {
            String pokemonName = Urls.getDisplay(pokemon.name);
            Contexts.sendShareIntent(
                this,
                String.format("Check out pokemon %s!", pokemonName),
                String.format(
                    "https://bulbapedia.bulbagarden.net/wiki/%s_(Pokémon)",
                    pokemonName.replace(' ', '_')
                )
            );
        }
        return super.onOptionsItemSelected(item);
    }

    public static class GalleryAdapter extends
        SimpleAdapter<String, GalleryAdapter.ViewHolder> {
        private final Picasso picasso;

        public GalleryAdapter(@NonNull Context context, @NonNull List<String> list) {
            super(context, list);
            picasso = new Picasso.Builder(context).build();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_gallery, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            picasso
                .load(getItem(position))
                .into(holder.image);
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
            }
        }
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
                                MoveDialogFragment.EXTRA_MOVE,
                                Parcels.wrap(pair.first)
                            );

                            DialogFragment fragment = new MoveDialogFragment();
                            fragment.setArguments(bundle);
                            fragment.show(
                                ((AppCompatActivity) context).getSupportFragmentManager(),
                                MoveDialogFragment.TAG
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
