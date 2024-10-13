# [Homework 2](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/hw2.docx): Temperature Converter 2

A continuation of the previous homework regarding temperature conversion. In
this example, the application accepts user input in form of slider instead
of text field.

- Similar stacks as the previous assignment: [AndroidX Library](https://developer.android.com/jetpack/androidx)
  for compatibility, [Material Design Components](https://developer.android.com/design/ui/mobile/guides/components/material-overview)
  for theming and [Roboletric](https://robolectric.org/) for mocked testing.
- Animate `StubView` visibility changes within layout using
  `android:animateLayoutChanges` attribute.
- Employs MVC pattern with [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  from [Lifecycle Library](https://developer.android.com/jetpack/androidx/releases/lifecycle)
  to observe live data.

## Screenshots

### Interface

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw2/screenshot1_1.png">
<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw2/screenshot1_2.png"><br><small>Screenshot 1.1 & 1.2 &mdash; Main interface</small>

The main interface consists of a slider to convert temperature values and check
box to display 5-day weather forecast layout. The input and output value is
shown in text controls as user interacts with the slider.

<img width="640" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw2/screenshot1_3.png"><br><small>Screenshot 1.3 &mdash; Horizontal layout</small>

Just like **Temperature Converter 1**, the application supports dark mode theme
based on the system settings and the ability to adapt to horizontal screen.

### 5-Day Weather Forecast

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw2/screenshot2.png"><br><small>Screenshot 2 &mdash; `StubView` interface</small>

The weather forecast are contained in a `RecyclerView` that can customize
item layout using view holder pattern. Although this pattern can also be
implemented using `ArrayAdapter`, `RecyclerView` has built-in support and can
support different item type.
