package com.example.pokemon.ui.roster;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.pokemon.R;
import com.example.pokemon.Tasks;
import com.example.pokemon.Urls;
import com.example.pokemon.db.Members;
import com.example.pokemon.db.schema.Member;
import com.example.pokemon.ui.NextActivity;
import com.example.pokemon.ui.main.MainActivity;
import java.util.Objects;

public class RosterActivity extends NextActivity {
    public static final String EXTRA_MEMBER = "RosterActivity#EXTRA_MEMBER";

    Member member;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_roster;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        member = Objects.requireNonNull((Member) intent.getSerializableExtra(EXTRA_MEMBER));

        setTitle(Urls.getDisplay(member.getPokemon()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_roster, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.deleteItem) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ConfirmDialogFragment.EXTRA_MEMBER, member);

            DialogFragment dialogFragment = new ConfirmDialogFragment();
            dialogFragment.setArguments(bundle);
            dialogFragment.show(getSupportFragmentManager(), ConfirmDialogFragment.TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class ConfirmDialogFragment extends DialogFragment {
        public static final String TAG = "RosterActivity.ConfirmDialogFragment";
        public static final String EXTRA_MEMBER =
            "RosterActivity.ConfirmDialogFragment#EXTRA_MEMBER";

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            Member member =
                Objects.requireNonNull((Member) requireArguments().getSerializable(EXTRA_MEMBER));
            return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.confirm)
                .setMessage(
                    getString(
                        R.string.dialog_delete_member_desc,
                        Urls.getDisplay(member.getPokemon())
                    )
                ).setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
                .setPositiveButton(
                    android.R.string.ok,
                    (dialog, which) -> {
                        RosterActivity activity = (RosterActivity) requireActivity();
                        Members members = activity.db.members();
                        Tasks.execute(
                            () -> members.delete(member),
                            () -> {
                                Toast
                                    .makeText(
                                        activity,
                                        getString(
                                            R.string.dialog_delete_member_msg,
                                            Urls.getDisplay(member.getPokemon())
                                        ), Toast.LENGTH_LONG
                                    ).show();

                                Intent resultIntent = new Intent();
                                resultIntent.putExtra(MainActivity.EXTRA_MEMBER, member);
                                activity.setResult(RESULT_OK, resultIntent);
                                activity.finish();
                            }
                        );
                    }
                ).create();
        }
    }
}
