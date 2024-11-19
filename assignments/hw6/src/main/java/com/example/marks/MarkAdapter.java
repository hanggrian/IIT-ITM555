package com.example.marks;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.marks.db.schema.Mark;
import com.google.android.material.snackbar.Snackbar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * An adapter that controls mark's card view and behavior.
 */
public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.ViewHolder> {
    private final List<Mark> marks;

    public MarkAdapter(List<Mark> marks) {
        this.marks = marks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mark, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mark mark = marks.get(position);
        holder.idText.setText(String.format("#%s", mark.getId()));
        holder.nameText.setText(mark.toString());
        holder.valueText.setText(String.valueOf(mark.getValue()));

        holder.itemView.setOnClickListener(
            v ->
                Snackbar
                    .make(
                        v,
                        String.format(
                            Locale.US,
                            "%d. %s has a grade of %s",
                            mark.getId(),
                            mark.getName(),
                            mark.getValue()
                        ), Snackbar.LENGTH_SHORT
                    ).show()
        );
        holder.itemView.setOnLongClickListener(
            v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(ConfirmDialog.EXTRA_MARK, mark);

                DialogFragment dialog = new ConfirmDialog();
                dialog.setArguments(bundle);
                dialog.show(
                    ((AppCompatActivity) v.getContext()).getSupportFragmentManager(),
                    ConfirmDialog.TAG
                );
                return false;
            }
        );
    }

    @Override
    public int getItemCount() {
        return marks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView idText;
        TextView nameText;
        TextView valueText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idText = itemView.findViewById(R.id.idText);
            nameText = itemView.findViewById(R.id.nameText);
            valueText = itemView.findViewById(R.id.valueText);
        }
    }

    public static class ConfirmDialog extends DialogFragment {
        public static final String TAG = "MarkAdapter.ConfirmDialog";
        public static final String EXTRA_MARK = "MARK";

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            Mark mark =
                Objects.requireNonNull((Mark) requireArguments().getSerializable(EXTRA_MARK));
            return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.confirm)
                .setMessage(getString(R.string.dialog_delete_desc, mark.getId(), mark.getName()))
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
                .setPositiveButton(
                    android.R.string.ok,
                    (dialog, which) -> {
                        MainActivity activity = (MainActivity) requireActivity();
                        Tasks.executeResult(
                            () -> {
                                Objects
                                    .requireNonNull(activity.viewModel.db)
                                    .marks()
                                    .delete(mark);
                                return mark;
                            }, mark2 -> {
                                activity.viewModel.marks.remove(mark2);
                                int index = activity.viewModel.marks.indexOf(mark2);
                                if (index == -1) {
                                    return;
                                }
                                activity.viewModel.marks.remove(mark2);
                                activity.adapter.notifyItemRemoved(index);
                            }
                        );
                    }
                ).create();
        }
    }
}
