package com.example.pokemon.ui.main;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pokemon.R;
import com.example.pokemon.Tasks;
import com.example.pokemon.Urls;
import com.example.pokemon.databinding.FragmentRosterBinding;
import com.example.pokemon.db.Members;
import com.example.pokemon.db.PokemonDatabase;
import com.example.pokemon.db.schema.Member;
import com.example.pokemon.ui.SimpleAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RosterFragment extends MainFragment {
    RecyclerView recycler;
    View emptyView;
    ExtendedFloatingActionButton action;

    MainObserver observer;
    PokemonDatabase db;
    RosterAdapter adapter;

    @Override
    protected int getMenuRes() {
        return R.menu.fragment_roster;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = PokemonDatabase.from(requireContext());
        observer = new MainObserver((MainActivity) requireActivity());
        getLifecycle().addObserver(observer);
    }

    @Nullable
    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        return FragmentRosterBinding.inflate(inflater).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = view.findViewById(R.id.recycler);
        emptyView = view.findViewById(R.id.emptyView);
        action = view.findViewById(R.id.action);

        adapter = new RosterAdapter(this, getViewModel().memberList, observer);
        adapter.registerAdapterDataObserver(
            new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    onChanged();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    onChanged();
                }

                @Override
                public void onChanged() {
                    if (getViewModel().memberList.isEmpty()) {
                        recycler.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        action.setVisibility(View.VISIBLE);
                        return;
                    }
                    recycler.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    action.setVisibility(
                        getViewModel().memberList.size() >= 6
                            ? View.GONE
                            : View.VISIBLE
                    );
                }
            }
        );
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(
            new DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        );

        Tasks.executeResult(
            () -> db.members().getAll(),
            list -> {
                getViewModel().memberList.clear();
                getViewModel().memberList.addAll(list);
                Collections.sort(getViewModel().memberList);
                adapter.notifyDataSetChanged();
            }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.aboutItem) {
            new AboutDialogFragment().show(getChildFragmentManager(), AboutDialogFragment.TAG);
            return true;
        }
        return false;
    }

    public static class RosterAdapter extends SimpleAdapter<Member, RosterAdapter.ViewHolder> {
        private final Fragment fragment;
        private final Picasso picasso;
        private final MainObserver observer;

        public RosterAdapter(
            @NonNull Fragment fragment,
            @NonNull List<Member> list,
            @NonNull MainObserver observer
        ) {
            super(fragment.requireContext(), list);
            this.fragment = fragment;
            picasso = new Picasso.Builder(context).build();
            this.observer = observer;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(
                LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_roster, parent, false)
            );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Member member = list.get(position);
            picasso
                .load(member.getSprite())
                .into(holder.image);
            holder.nameText.setText(Urls.getDisplay(member.getPokemon()));

            StringBuilder builder = new StringBuilder();
            append(builder, member.getMove1());
            append(builder, member.getMove2());
            append(builder, member.getMove3());
            append(builder, member.getMove4());
            String s = builder.toString();
            holder.descriptionText.setText(
                s.endsWith(", ")
                    ? s.substring(0, s.length() - 2)
                    : s
            );

            holder.itemView.setOnClickListener(v -> observer.launchRoster(context, member));
            holder.itemView.setOnLongClickListener(
                v -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ConfirmDialogFragment.EXTRA_MEMBER, member);

                    DialogFragment dialogFragment = new ConfirmDialogFragment();
                    dialogFragment.setArguments(bundle);
                    dialogFragment
                        .show(fragment.getChildFragmentManager(), ConfirmDialogFragment.TAG);
                    return false;
                }
            );
        }

        private void append(@NonNull StringBuilder builder, @Nullable String text) {
            if (text == null) {
                return;
            }
            builder
                .append(Urls.getDisplay(text))
                .append(", ");
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView image;
            TextView nameText;
            TextView descriptionText;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                nameText = itemView.findViewById(R.id.nameText);
                descriptionText = itemView.findViewById(R.id.descriptionText);
            }
        }
    }

    public static class ConfirmDialogFragment extends DialogFragment {
        public static final String TAG = "RosterFragment.ConfirmDialogFragment";
        public static final String EXTRA_MEMBER =
            "RosterFragment.ConfirmDialogFragment#EXTRA_MEMBER";

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
                        RosterFragment fragment = (RosterFragment) requireParentFragment();
                        Members members = fragment.db.members();
                        Tasks.execute(
                            () -> members.delete(member),
                            () -> {
                                int index = fragment.getViewModel().memberList.indexOf(member);
                                if (index == -1) {
                                    return;
                                }
                                fragment.getViewModel().memberList.remove(member);
                                fragment.adapter.notifyItemRemoved(index);
                                Snackbar
                                    .make(
                                        fragment.action,
                                        getString(
                                            R.string.dialog_delete_member_msg,
                                            Urls.getDisplay(member.getPokemon())
                                        ), Snackbar.LENGTH_LONG
                                    ).setAction(
                                        R.string.undo,
                                        v ->
                                            Tasks.execute(
                                                () -> members.insertAll(member),
                                                () -> {
                                                    fragment.getViewModel().memberList.add(member);
                                                    fragment.adapter.notifyItemInserted(
                                                        fragment.getViewModel().memberList.size()
                                                            - 1
                                                    );
                                                }
                                            )
                                    ).show();
                            }
                        );
                    }
                ).create();
        }
    }
}
