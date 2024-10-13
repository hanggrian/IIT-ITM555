# [Homework 3](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/hw3.docx): Famous Quotes

An Android app that lists Steve Jobs' popular quotes and thumbnail pictures.
Upon clicking a quote, the app will redirect the user to a new activity showing
the full quote and high-resolution picture.

- Send and receive extra data between activities using `Intent`.
- Utilize newer [AndroidX SplashScreen](https://developer.android.com/develop/ui/views/launch/splash-screen)
  API that provides cleaner implementation but less customizable splash screen
  layout. As a penalty, the API level needs to be raised to **31 (Android 12)**
  instead of the required **28 (Android 9)**.
- Update layout background and text color dynamically using [AndroidX Palette](https://developer.android.com/jetpack/androidx/releases/palette)
  the predicts vibrant and muted colors from an image.

## Screenshots

### Sender

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw3/screenshot1_1.png">
<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw3/screenshot1_2.png"><br><small>Screenshot 1.1 & 1.2 &mdash; Initial point</small>

The application starts with `QuoteReaderActivity` containing a list of quotes,
its item is clickable to navigate to the next activity.

Upgrade Material design language from
**V2 (`Theme.MaterialComponents.DayNight`)** to
**V3 (`Theme.Material3.DayNight`)**. The new theme has a simpler text
appearances and flatter colors, such as toolbar without shadow or elevation.

### Receiver

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw3/screenshot2_1.png">
<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw3/screenshot2_2.png"><br><small>Screenshot 2.1 & 2.2 &mdash; Final point</small>

The `QuoteDetailActivity` displays the quote and picture in full screen with
colorful background and text colors based on the image palette.

### Splash Screen

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw3/screenshot3.png"><br><small>Screenshot 3 &mdash; 5 seconds splash</small>

The AndroidX compatibility library group recently introduced a new splash screen
API. Previously, developers had to implement this feature manually with a custom
activity and timer. With the new API, the splash screen icon, background colors
and branding image can be defined in `styles.xml`.

```xml
<item name="android:windowSplashScreenBackground">
  ?colorPrimary
</item>
<item name="android:windowSplashScreenAnimatedIcon">
  @drawable/splash_icon
</item>
<item name="android:windowSplashScreenIconBackgroundColor">
  @android:color/white
</item>
<item name="android:windowSplashScreenBrandingImage">
  @drawable/splash_branding
</item>
```

Note that some Java code is still required for an [extended delay](https://developer.android.com/develop/ui/views/launch/splash-screen#suspend-drawing)
before closing the splash screen. In this instance, the delay is set to 5
seconds.

```java
boolean isReady = false;

content.getViewTreeObserver().addOnPreDrawListener(
    new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
            if (isReady) {
                content
                    .getViewTreeObserver()
                    .removeOnPreDrawListener(this);
            }
            return isReady;
        }
    }
);

new Handler(Looper.getMainLooper())
    .postDelayed(() -> isReady = true, DURATION_SPLASH);

```
