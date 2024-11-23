package com.example.pokemon;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Utility class to conveniently use {@link Executors}, which in itself is a replacement to
 * {@link android.os.AsyncTask} because the latter is obsolete.
 */
public final class Tasks {
    private Tasks() {}

    /**
     * Execute background and ui workers without result.
     *
     * @param backgroundAction non-blocking worker in the background.
     * @param uiAction final action in the UI.
     */
    public static void execute(@NonNull Runnable backgroundAction, @NonNull Runnable uiAction) {
        Executors
            .newSingleThreadExecutor()
            .execute(
                () -> {
                    backgroundAction.run();
                    new Handler(Looper.getMainLooper())
                        .post(uiAction);
                }
            );
    }

    public static void executeIf(
        @NonNull Callable<Boolean> backgroundAction,
        @NonNull Runnable uiAction
    ) {
        Executors
            .newSingleThreadExecutor()
            .execute(
                () -> {
                    try {
                        if (!backgroundAction.call()) {
                            return;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    new Handler(Looper.getMainLooper())
                        .post(uiAction);
                }
            );
    }

    /**
     * Execute background worker with result delivered to ui worker.
     *
     * @param backgroundAction non-blocking worker in the background.
     * @param uiAction final action in the UI.
     * @param <T> return type of background worker.
     */
    public static <T> void executeResult(
        @NonNull Callable<T> backgroundAction,
        @NonNull Consumer<T> uiAction
    ) {
        Executors
            .newSingleThreadExecutor()
            .execute(
                () -> {
                    final T result;
                    try {
                        result = backgroundAction.call();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    new Handler(Looper.getMainLooper())
                        .post(() -> uiAction.accept(result));
                }
            );
    }
}
