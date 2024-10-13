package com.example.playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.playlist.data.Music;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A list adapter with two item types: a regular music preview item and a parent type to group songs
 * based on their country of origin. This is done by populating a {@link Multimap} and rebuilding
 * a list using the keys periodically slipped into the values.
 */
public class MusicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int TYPE_GROUP = 0;
    static final int TYPE_INFO = 1;

    private final List<Object> actualList = new ArrayList<>();
    private final Iterator<Integer> colorIterable;
    private final FragmentManager manager;

    public MusicAdapter(AppCompatActivity activity) {
        colorIterable =
            RandomIterable
                .of(
                    ContextCompat.getColor(activity, R.color.red),
                    ContextCompat.getColor(activity, R.color.pink),
                    ContextCompat.getColor(activity, R.color.purple),
                    ContextCompat.getColor(activity, R.color.deep_purple),
                    ContextCompat.getColor(activity, R.color.indigo),
                    ContextCompat.getColor(activity, R.color.blue),
                    ContextCompat.getColor(activity, R.color.light_blue),
                    ContextCompat.getColor(activity, R.color.cyan),
                    ContextCompat.getColor(activity, R.color.teal),
                    ContextCompat.getColor(activity, R.color.green),
                    ContextCompat.getColor(activity, R.color.light_green),
                    ContextCompat.getColor(activity, R.color.lime),
                    ContextCompat.getColor(activity, R.color.yellow),
                    ContextCompat.getColor(activity, R.color.amber),
                    ContextCompat.getColor(activity, R.color.orange),
                    ContextCompat.getColor(activity, R.color.deep_orange),
                    ContextCompat.getColor(activity, R.color.brown),
                    ContextCompat.getColor(activity, R.color.gray),
                    ContextCompat.getColor(activity, R.color.blue_gray)
                ).iterator();
        manager = activity.getSupportFragmentManager();
    }

    public void replaceAll(Collection<Music> musics) {
        Multimap<String, Music> multimap = HashMultimap.create();
        musics.forEach(music -> multimap.put(music.getDisplayCountry(), music));

        int oldSize = actualList.size();
        actualList.clear();
        Music mostValuableMusic =
            multimap
                .values()
                .stream()
                .max(Comparator.comparing(Music::getPrice))
                .orElseThrow();
        AtomicInteger num = new AtomicInteger(1);
        multimap.keySet().forEach(
            s -> {
                actualList.add(s);
                multimap
                    .get(s)
                    .forEach(music ->
                        actualList.add(
                            new Entry(
                                num.getAndIncrement(),
                                music,
                                music.equals(mostValuableMusic))
                        )
                    );
            }
        );

        int newSize = actualList.size();
        if (oldSize == 0) {
            notifyItemRangeInserted(0, newSize);
        } else if (oldSize == newSize) {
            notifyItemRangeChanged(0, newSize);
        } else if (oldSize > newSize) {
            notifyItemRangeChanged(0, newSize);
            notifyItemRangeRemoved(newSize + 1, oldSize);
        } else {
            notifyItemRangeChanged(0, oldSize);
            notifyItemRangeInserted(oldSize + 1, newSize);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_GROUP) {
            return new GroupViewHolder(inflater.inflate(R.layout.item_music_group, parent, false));
        }
        return new ItemViewHolder(inflater.inflate(R.layout.item_music_info, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = actualList.get(position);
        if (item instanceof String) {
            ((GroupViewHolder) holder).text.setText((String) item);
            return;
        }

        ItemViewHolder infoHolder = (ItemViewHolder) holder;
        Entry entry = (Entry) item;
        int color = colorIterable.next();
        infoHolder.card.setOnClickListener(
            v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(MusicDialog.EXTRA_MUSIC, entry.music);
                bundle.putInt(MusicDialog.EXTRA_ACCENT, color);

                BottomSheetDialogFragment dialog = new MusicDialog();
                dialog.setArguments(bundle);
                dialog.show(manager, MusicDialog.TAG);
            }
        );
        infoHolder.card.setCardBackgroundColor(color);
        infoHolder.titleText.setText(
            String.format(Locale.US, "%d. %s", entry.number, entry.music.getTitle())
        );
        infoHolder.yearText.setText(entry.music.getYear());
        infoHolder.artistText.setText(entry.music.getArtist());
        infoHolder.priceText.setVisibility(entry.isHighestPrice ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return actualList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return actualList.get(position) instanceof String
            ? TYPE_GROUP
            : TYPE_INFO;
    }

    private static final class Entry {
        public final int number;
        public final Music music;
        public final boolean isHighestPrice;

        private Entry(int number, Music music, boolean isHighestPrice) {
            this.number = number;
            this.music = music;
            this.isHighestPrice = isHighestPrice;
        }
    }

    /**
     * The view holder for displaying a group based on country.
     */
    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            text = (TextView) itemView;
        }
    }

    /**
     * The view holder for music item preview.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView titleText;
        TextView yearText;
        TextView artistText;
        TextView priceText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            card = (CardView) itemView;
            titleText = itemView.findViewById(R.id.titleText);
            yearText = itemView.findViewById(R.id.yearText);
            artistText = itemView.findViewById(R.id.artistText);
            priceText = itemView.findViewById(R.id.priceText);
        }
    }
}
