package com.example.pokemon.rest.moves;

import androidx.annotation.NonNull;
import com.example.pokemon.rest.games.Generation;
import com.example.pokemon.rest.pokemon.AbilityEffectChange;
import com.example.pokemon.rest.utilities.MachineVersionDetail;
import com.example.pokemon.rest.utilities.Name;
import com.example.pokemon.rest.utilities.NamedApiResource;
import com.example.pokemon.rest.utilities.VerboseEffect;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Optional;
import org.parceler.Parcel;

@Parcel
public class Move extends NamedApiResource {
    private static final int MAX_POWER = 200;
    private static final int MAX_PP = 35; // without PP Up

    public int id;
    public int accuracy;

    @SerializedName("effect_chance")
    public int effectChance;

    public int pp;
    public int priority;
    public int power;

    @SerializedName("contest_combos")
    public ContestComboSets contestCombos;

    // public ContestType contest_type;
    // public ContestEffect contest_effect;

    @SerializedName("damage_class")
    public NamedApiResource damageClass;

    @SerializedName("effect_entries")
    public List<VerboseEffect> effectEntries;

    @SerializedName("effect_changes")
    public List<AbilityEffectChange> effectChanges;

    @SerializedName("flavor_text_entries")
    public List<MoveFlavorText> flavorTextEntries;

    public Generation generation;

    @SerializedName("learned_by_pokemon")
    public List<NamedApiResource> learnedByPokemon;

    public List<MachineVersionDetail> machines;
    public MoveMetaData meta;
    public List<Name> names;

    @SerializedName("past_values")
    public List<PastMoveStatValues> pastValues;

    @SerializedName("stat_changes")
    public List<MoveStatChange> statChanges;

    // public SuperContestEffect super_contest_effect;

    public NamedApiResource target;
    public NamedApiResource type;

    public int getPowerPercentage() {
        return getPercentage(power, MAX_POWER);
    }

    public int getPpPercentage() {
        return getPercentage(pp, MAX_PP);
    }

    public int getPriorityPercentage() {
        if (priority == 0) {
            return 50;
        }
        return priority < 0
            ? 0
            : 100;
    }

    @NonNull
    public String getShortDescription() {
        Optional<MoveFlavorText> moveFlavorText =
            flavorTextEntries
                .stream()
                .filter(text -> text.language.name.equals("en"))
                .findFirst();
        return moveFlavorText.isPresent()
            ? moveFlavorText.get().flavorText
            : "";
    }

    @NonNull
    public String getFullDescription() {
        StringBuilder builder = new StringBuilder(getShortDescription());
        effectEntries
            .stream()
            .filter(verboseEffect -> verboseEffect.language.name.equals("en"))
            .forEach(
                verboseEffect ->
                    builder
                        .append("\n\n")
                        .append(verboseEffect.effect)
            );
        return builder.toString();
    }

    private static int getPercentage(int current, int max) {
        return Float.valueOf(((float) current / max) * 100).intValue();
    }
}
