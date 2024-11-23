package com.example.pokemon.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import com.example.pokemon.db.schema.Member;
import com.example.pokemon.rest.pokemon.Pokemon;
import com.example.pokemon.ui.monster.MonsterActivity;
import com.example.pokemon.ui.roster.RosterActivity;
import java.util.List;
import org.parceler.Parcels;

public class MainObserver implements DefaultLifecycleObserver {
    private static final String KEY_MONSTER = "MainObserver#KEY_MONSTER";
    private static final String KEY_ROSTER = "MainObserver#KEY_ROSTER";

    private final AppCompatActivity activity;
    private final ActivityResultRegistry registry;
    private ActivityResultLauncher<Intent> monsterLauncher;
    private ActivityResultLauncher<Intent> rosterLauncher;

    public MainObserver(@NonNull AppCompatActivity activity) {
        this.activity = activity;
        this.registry = activity.getActivityResultRegistry();
    }

    public void onCreate(@NonNull LifecycleOwner owner) {
        monsterLauncher =
            registry.register(
                KEY_MONSTER,
                owner,
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() != Activity.RESULT_OK) {
                        return;
                    }
                    Intent intent = result.getData();
                    if (intent == null) {
                        return;
                    }
                    new ViewModelProvider(activity)
                        .get(MainViewModel.class)
                        .memberList
                        .add((Member) intent.getSerializableExtra(MainActivity.EXTRA_MEMBER));
                }
            );
        rosterLauncher =
            registry.register(
                KEY_ROSTER,
                owner,
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() != Activity.RESULT_OK) {
                        return;
                    }
                    Intent intent = result.getData();
                    if (intent == null) {
                        return;
                    }
                    List<Member> memberList =
                        new ViewModelProvider(activity)
                            .get(MainViewModel.class)
                            .memberList;
                    Member member = (Member) intent.getSerializableExtra(MainActivity.EXTRA_MEMBER);

                    int index = memberList.indexOf(member);
                    if (index == -1) {
                        return;
                    }
                    memberList.remove(member);
                    ((RosterFragment) owner).adapter.notifyItemRemoved(index);
                }
            );
    }

    public void launchMonster(@NonNull Context context, @NonNull Pokemon pokemon) {
        Intent intent = new Intent(context, MonsterActivity.class);
        intent.putExtra(MonsterActivity.EXTRA_POKEMON, Parcels.wrap(pokemon));
        monsterLauncher.launch(intent);
    }

    public void launchRoster(@NonNull Context context, @NonNull Member member) {
        Intent intent = new Intent(context, RosterActivity.class);
        intent.putExtra(RosterActivity.EXTRA_MEMBER, member);
        rosterLauncher.launch(intent);
    }
}
