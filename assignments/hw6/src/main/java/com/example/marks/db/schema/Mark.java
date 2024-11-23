package com.example.marks.db.schema;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A Plain Old Java Object representing information of students enrolled in a course.
 */
@Entity(tableName = "mark")
public class Mark implements Serializable {
    public static final Pattern PATTERN_NAME =
        Pattern.compile("^[A-Z][a-zA-Z'-]+( [A-Z][a-zA-Z'-]+)*$");
    public static final Pattern PATTERN_VALUE = Pattern.compile("^[A-F]$");

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "value")
    private char value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Mark other = (Mark) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @NonNull
    @Override
    public String toString() {
        String[] names = name.split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < names.length; i++) {
            builder
                .append(
                    i == 0
                        ? names[i].charAt(0) + "."
                        : names[i]
                ).append(' ');
        }
        return builder.toString();
    }

    public static class Builder {
        private int id;
        private String name;
        private char value;

        @NonNull
        public Builder id(int id) {
            this.id = id;
            return this;
        }

        @NonNull
        public Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        @NonNull
        public Builder value(char value) {
            this.value = value;
            return this;
        }

        @NonNull
        public Mark build() {
            Mark mark = new Mark();
            mark.id = id;
            mark.name = name;
            mark.value = value;
            return mark;
        }
    }
}
