# [Homework 5](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/hw5.docx): PopupQuiz

- Progress bar and toolbar title indicate the current question number.
- Imports [AndroidX Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
  library for observable values and configuration-aware components.
- Material3 theme compliant, supports dark mode, utilize `FloatingActionButton`
  and `ExtendedFloatingActionButton` in the same layout.

## Diagram

<img width="640" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw5/diagram1.svg"><br><small>Diagram 1 &mdash; Application workflow</small>

At any point during the quiz, the user may return to the initial point by
selecting **Reset** menu item.

## Screenshots

### Screen rotation

<img width="640" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw5/screenshot1.png"><br><small>Screenshot 1 &mdash; Handling screen rotation changes</small>

There are several ways to retain the activity state during
[configuration changes](https://developer.android.com/guide/topics/resources/runtime-changes)
such as screen rotation. The most common way is to override the
`Activity#onSaveInstanceState` and `Activity#onRestoreInstanceState` methods to
manually store and restore values in the bundle. We can also define the
`android:configChanges` attribute in the manifest file to prevent the recreation
of the activity, but this is not recommended because landscape layout cannot be
rendered when the screen is initially in portrait mode. In few cases, we might
want to disable the screen rotation entirely by configuring the
`android:screenOrientation` to the desired orientation.

However, the newer approaches to handle configuration changes are to use
`DataStore`, a Jetpack component that replaces `SharedPreferences`, and
`ViewModel`, part of AndroidX lifecycle library. In this example, values
declared in the `ViewModel` bind to the UI components and are retained during
screen rotation.

### States

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw5/screenshot2_1.png"><br><small>Screenshot 2.1 &mdash; Initial `LOADING` state</small>

When it first launches, a progress dialog is displayed to indicate that the
application is fetching questions from the server. Note that a regular
`AlertDialog` is used instead of the deprecated `ProgressDialog`.

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw5/screenshot2_2.png">
<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw5/screenshot2_3.png"><br><small>Screenshot 2.2 &ndash; 2.3 &mdash; `ANSWERING` and `ANSWERED` states</small>

In this state, a question is presented with several indicators showing current
progress. The user can select an answer by tapping corresponding radio button
and submit it with **Display Result** button. At this point, the rating bar is
updated to reflect the user's performance and **Next** button is enabled to
proceed to the next question.

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw5/screenshot2_4.png"><br><small>Screenshot 2.4 &mdash; Final `FINISHED` state</small>

If no more questions are available after answering the last question, the final
state is reached and user can no longer interact until the quiz is reset.

### Login screen

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw5/screenshot3_1.png">
<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw5/screenshot3_2.png"><br><small>Screenshot 3.1 & 3.2 &mdash; Randomized question sheet</small>

The indices of question sheet entries are randomized each API call, it is
unlikely that the same order will appear consecutively.
