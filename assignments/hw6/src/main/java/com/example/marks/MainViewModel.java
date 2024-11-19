package com.example.marks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import com.example.marks.db.MarkDatabase;
import com.example.marks.db.schema.Mark;
import java.util.ArrayList;
import java.util.List;

/**
 * A container that retains complex dataset in the event of configuration changes, such as screen
 * rotation.
 */
public class MainViewModel extends ViewModel {
    /**
     * The SQLite database connected via <i>Room</i> library, this property needs to be initialized
     * during startup and closed after destroy.
     */
    @Nullable
    public MarkDatabase db = null;

    /**
     * A collection for card view.
     */
    @NonNull
    public final List<Mark> marks = new ArrayList<>();
}
