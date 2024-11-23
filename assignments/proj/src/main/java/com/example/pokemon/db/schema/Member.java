package com.example.pokemon.db.schema;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "member")
public class Member implements Serializable {
    @PrimaryKey
    @NonNull
    @SuppressWarnings("NotNullFieldNotInitialized")
    private String pokemon;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "move1")
    private String move1;

    @ColumnInfo(name = "move2")
    private String move2;

    @ColumnInfo(name = "move3")
    private String move3;

    @ColumnInfo(name = "move4")
    private String move4;

    @NonNull
    public String getPokemon() {
        return pokemon;
    }

    public void setPokemon(@NonNull String pokemon) {
        this.pokemon = pokemon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMove1() {
        return move1;
    }

    public void setMove1(String move1) {
        this.move1 = move1;
    }

    public String getMove2() {
        return move2;
    }

    public void setMove2(String move2) {
        this.move2 = move2;
    }

    public String getMove3() {
        return move3;
    }

    public void setMove3(String move3) {
        this.move3 = move3;
    }

    public String getMove4() {
        return move4;
    }

    public void setMove4(String move4) {
        this.move4 = move4;
    }

    public static class Builder {
        private String pokemon;
        private String image;
        private String move1;
        private String move2;
        private String move3;
        private String move4;

        @NonNull
        public Builder pokemon(@Nullable String pokemon) {
            this.pokemon = pokemon;
            return this;
        }

        @NonNull
        public Builder image(@Nullable String image) {
            this.image = image;
            return this;
        }

        @NonNull
        public Builder move1(@Nullable String move1) {
            this.move1 = move1;
            return this;
        }

        @NonNull
        public Builder move2(@Nullable String move2) {
            this.move2 = move2;
            return this;
        }

        @NonNull
        public Builder move3(@Nullable String move3) {
            this.move3 = move3;
            return this;
        }

        @NonNull
        public Builder move4(@Nullable String move4) {
            this.move4 = move4;
            return this;
        }

        @NonNull
        public Member build() {
            Member result = new Member();
            result.pokemon = pokemon;
            result.image = image;
            result.move1 = move1;
            result.move2 = move2;
            result.move3 = move3;
            result.move4 = move4;
            return result;
        }
    }
}
