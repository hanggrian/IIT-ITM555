package com.example.marks.db;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.marks.db.schema.Mark;

/**
 * A local SQLite database accessed through modern <i>Room</i> implementation.
 */
@Database(entities = {Mark.class}, version = 1)
public abstract class MarkDatabase extends RoomDatabase {
    public static final String NAME = "marks-db";

    public abstract Marks marks();

    @NonNull
    public static MarkDatabase from(@NonNull Context context) {
        return from(context, false);
    }

    @NonNull
    public static MarkDatabase from(@NonNull Context context, boolean isTest) {
        Builder<MarkDatabase> builder =
            Room.databaseBuilder(context, MarkDatabase.class, NAME);
        if (isTest) {
            builder = builder.allowMainThreadQueries();
        }
        return builder.build();
    }
}
