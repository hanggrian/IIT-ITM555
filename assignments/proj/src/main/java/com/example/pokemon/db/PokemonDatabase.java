package com.example.pokemon.db;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.pokemon.db.schema.Member;

@Database(entities = {Member.class}, version = 1)
public abstract class PokemonDatabase extends RoomDatabase {
    public static final String NAME = "pokemon-db";

    public abstract Members members();

    @NonNull
    public static PokemonDatabase from(@NonNull Context context) {
        return from(context, false);
    }

    @NonNull
    public static PokemonDatabase from(@NonNull Context context, boolean isTest) {
        Builder<PokemonDatabase> builder =
            Room.databaseBuilder(context, PokemonDatabase.class, NAME);
        if (isTest) {
            builder = builder.allowMainThreadQueries();
        }
        return builder.build();
    }
}
