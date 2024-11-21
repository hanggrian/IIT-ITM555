package com.example.pokemon.rest.schema;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Move {
    private NamedUrl move;

    @SerializedName("version_group_details")
    private List<Detail> versionGroupDetails;

    public NamedUrl getMove() {
        return move;
    }

    public void setMove(NamedUrl move) {
        this.move = move;
    }

    public List<Detail> getVersionGroupDetails() {
        return versionGroupDetails;
    }

    public void setVersionGroupDetails(List<Detail> versionGroupDetails) {
        this.versionGroupDetails = versionGroupDetails;
    }

    public static class Detail {
        @SerializedName("level_learned_at")
        private int levelLearnedAt;

        @SerializedName("move_learn_method")
        private NamedUrl moveLearnMethod;

        @SerializedName("version_group")
        private NamedUrl versionGroup;

        public int getLevelLearnedAt() {
            return levelLearnedAt;
        }

        public void setLevelLearnedAt(int levelLearnedAt) {
            this.levelLearnedAt = levelLearnedAt;
        }

        public NamedUrl getMoveLearnMethod() {
            return moveLearnMethod;
        }

        public void setMoveLearnMethod(NamedUrl moveLearnMethod) {
            this.moveLearnMethod = moveLearnMethod;
        }

        public NamedUrl getVersionGroup() {
            return versionGroup;
        }

        public void setVersionGroup(NamedUrl versionGroup) {
            this.versionGroup = versionGroup;
        }
    }
}
