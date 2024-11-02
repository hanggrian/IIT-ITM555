package com.example.quiz;

/**
 * Viewing mode when answering the pop quiz.
 */
public enum State {
    /**
     * Fetching questions from server, loading dialog is blocking all controls.
     */
    LOADING,

    /**
     * User can answer the question by clicking <b>Display Result</b> button.
     */
    ANSWERING,

    /**
     * User can no longer answer current question because <b>Display Result</b> button is disabled,
     * while <b>Next</b> button appears.
     */
    ANSWERED,

    /**
     * No more questions to feed, all buttons become un-clickable.
     */
    FINISHED
}
