# [Homework 1](https://github.com/hanggrian/IIT-ITM515/blob/assets/assignments/hw1.docx): Temperature Converter

A simple Android application that converts temperature between Celsius and
Fahrenheit and vice-versa. Additionally, the application also shows the likely
season based on the temperature.

- Uses [AndroidX Compatibility Library](https://developer.android.com/jetpack/androidx)
  and [Material Design Components](https://developer.android.com/design/ui/mobile/guides/components/material-overview)
  for app theme and widgets.
- `Season` enum class holding resource identifiers and `Temperature` data class
  for temperature conversion.
- [Roboletric](https://robolectric.org/) for mocked unit testing.

## Screenshots

### Interface

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw1/screenshot1_1.png">
<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw1/screenshot1_2.png"><br><small>Screenshot 1.1 & 1.2 &mdash; Main interface</small>

The application only has one activity, it consists of one input field and
two radio buttons indicating whether the input is in Celsius or Fahrenheit. The
input field only accepts decimal numbers with optional negative sign.

Because the theme is set to `Theme.MaterialComponents.DayNight`, the application
will automatically switch between light and dark mode based on the system
settings.

<img width="640" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw1/screenshot1_3.png"><br><small>Screenshot 1.3 &mdash; Horizontal layout</small>

Rather than restricting the layout to portrait mode, the application recognizes
the orientation change and adapts the layout accordingly.

### Error Handling

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw1/screenshot2.png"><br><small>Screenshot 2 &mdash; Show invalid input</small>

The description text will display an error message if the input temperature is
not in the range of reasonable temperature values.
