package com.example.pokemon;

public final class Numbers {
    private Numbers() {}

    public static int getPercentage(int current, int max) {
        if (current < 0) {
            return 0;
        } else if (current > max) {
            return 100;
        }
        return Float.valueOf(((float) current / max) * 100).intValue();
    }
}
