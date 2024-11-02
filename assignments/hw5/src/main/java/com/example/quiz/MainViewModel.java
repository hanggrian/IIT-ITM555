package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A container of observable values controlling {@link MainActivity} behavior.
 */
public class MainViewModel extends ViewModel {
    /**
     * The question sheet for the quiz.
     */
    @NonNull
    public final List<String> questions = new ArrayList<>();

    /**
     * @see State
     */
    @NonNull
    public final MutableLiveData<State> stateData = new MutableLiveData<>(State.LOADING);

    /**
     * Current index of the question being answered.
     */
    @NonNull
    public final MutableLiveData<Integer> questionIndexData = new MutableLiveData<>(0);

    /**
     * Number of correct and total answers, these values produce rating stars.
     */
    @NonNull
    public final MutableLiveData<Pair<Integer, Integer>> answerTallyData =
        new MutableLiveData<>(new Pair<>(0, 0));

    /**
     * Increment tally answers.
     *
     * @param isCorrect whether the answer is right.
     */
    public void updateAnswerTally(boolean isCorrect) {
        Pair<Integer, Integer> tally = Objects.requireNonNull(answerTallyData.getValue());
        answerTallyData.setValue(
            new Pair<>(
                isCorrect ? tally.first + 1 : tally.first,
                tally.second + 1
            )
        );
    }
}
