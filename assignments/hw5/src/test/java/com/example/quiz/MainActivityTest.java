package com.example.quiz;

import static com.google.common.truth.Truth.assertThat;

import androidx.core.util.Pair;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.DoNotInstrument;
import org.robolectric.shadows.ShadowLooper;

@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class MainActivityTest {
    private MainActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).setup().get();
    }

    @Test
    public void checkStates() {
        assertThat(activity.viewModel.stateData.getValue())
            .isEqualTo(State.LOADING);
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

        for (int i = 0; i < 5; i++) {
            assertThat(activity.viewModel.stateData.getValue())
                .isEqualTo(State.ANSWERING);

            activity.radioGroup.check(R.id.trueRadio);
            activity.displayButton.performClick();

            if (i == 4) {
                assertThat(activity.viewModel.stateData.getValue())
                    .isEqualTo(State.FINISHED);
                return;
            }
            assertThat(activity.viewModel.stateData.getValue())
                .isEqualTo(State.ANSWERED);
            activity.nextButton.performClick();
        }
    }

    @Test
    public void checkAnswers() {
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
        for (int i = 0; i < 5; i++) {
            activity.radioGroup.check(
                MainActivity.RESULT_MAP.get(activity.text.getText().toString())
            );
            activity.displayButton.performClick();
            if (activity.nextButton.isShown()) {
                activity.nextButton.performClick();
            }
        }

        Pair<Integer, Integer> tally =
            Objects.requireNonNull(activity.viewModel.answerTallyData.getValue());
        assertThat(tally.first)
            .isEqualTo(tally.second);
    }
}
