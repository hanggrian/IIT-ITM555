package com.example.playlist;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.playlist.data.Music;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Locale;
import java.util.Objects;

/**
 * A dialog that attaches itself into a bottom sheet in a fragment controller, showing music detail
 * with color accent data sent from the callee.
 */
public class MusicDialog extends BottomSheetDialogFragment {
    public static final String TAG = "MusicDialog";
    public static final String EXTRA_MUSIC = "music";
    public static final String EXTRA_ACCENT = "accent";

    TextView titleText;
    TextView artistText;
    TextView priceText;
    TextView companyText;
    FloatingActionButton purchaseButton;
    TextView availabilityText;

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        Bundle bundle = requireArguments();
        Music music = (Music) Objects.requireNonNull(bundle.getSerializable(EXTRA_MUSIC));
        int accent = bundle.getInt(EXTRA_ACCENT);

        View root = inflater.inflate(R.layout.dialog_music, container, false);
        titleText = root.findViewById(R.id.titleText);
        artistText = root.findViewById(R.id.artistText);
        priceText = root.findViewById(R.id.priceText);
        companyText = root.findViewById(R.id.companyText);
        purchaseButton = root.findViewById(R.id.purchaseButton);
        availabilityText = root.findViewById(R.id.availabilityText);

        titleText.setText(music.toString());
        titleText.setTextColor(accent);
        artistText.setText(music.getArtist());
        priceText.setText(String.format(Locale.US, "$%.2f", music.getPrice()));
        companyText.setText(String.format("%s, %s", music.getCompany(), music.getCountry()));
        if (music.isSold()) {
            purchaseButton.setEnabled(false);
            availabilityText.setText(R.string.unavailable);
        } else {
            purchaseButton.setBackgroundTintList(ColorStateList.valueOf(accent));
            purchaseButton.setEnabled(true);
            availabilityText.setText(R.string.available);
        }
        return root;
    }
}
