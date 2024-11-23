package com.example.pokemon.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pokemon.R;
import com.example.pokemon.SimpleAdapter;
import com.example.pokemon.Urls;
import com.example.pokemon.databinding.FragmentRosterBinding;
import com.example.pokemon.db.schema.Member;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RosterFragment extends MainFragment {
    RecyclerView recycler;
    View emptyView;
    ExtendedFloatingActionButton action;

    RosterAdapter adapter;

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
        recycler = view.findViewById(R.id.recycler);
        emptyView = view.findViewById(R.id.emptyView);
        action = view.findViewById(R.id.action);

        adapter = new RosterAdapter(requireContext(), getViewModel().memberList);
        adapter.registerAdapterDataObserver(
            new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    if (getViewModel().memberList.isEmpty()) {
                        recycler.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        return;
                    }
                    recycler.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
            }
        );
        recycler.setAdapter(adapter);
    }

    public static class RosterAdapter extends SimpleAdapter<Member, RosterAdapter.ViewHolder> {
        private final Picasso picasso;

        public RosterAdapter(@NonNull Context context, @NonNull List<Member> list) {
            super(context, list);
            picasso = new Picasso.Builder(context).build();
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
                .load(member.getImage())
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
}
