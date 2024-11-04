# [Homework 1](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/hw1.docx): TemperatureConverter1 Report

This report contains all the codebase of the project. Several minor changes were
made for better readability:

- Reduce indent to **2 spaces.**
- Reduce max line length to **80.**
- Remove XML header tag and all attributes with `xmlns:` prefix in XML.
- Remove package and import statements in Java.

## Gradle

### 1. /gradle/libs.version.toml

```toml
[versions]
jdk = "21"
jre = "8"
checkstyle = "10.17.0"
kotlin = "2.0.20"
sdk-min = "26"
sdk-target = "34"
android-plugin = "8.6.1"
androidx = "1.7.0"
androidx-test = "1.6.1"

[plugins]
android-application =
  { id = "com.android.application", version.ref = "android-plugin" }

[libraries]
# lint
rulebook-checkstyle = "com.hanggrian.rulebook:rulebook-checkstyle:0.1"
# main
material =
  { module = "com.google.android.material:material", version.ref = "androidx" }
androidx-appcompat =
  { module = "androidx.appcompat:appcompat", version.ref = "androidx" }
androidx-coordinatorlayout =
  "androidx.coordinatorlayout:coordinatorlayout:1.2.0"
# test
androidx-test-core =
  { module = "androidx.test:core", version.ref = "androidx-test" }
androidx-test-runner =
  { module = "androidx.test:runner", version.ref = "androidx-test" }
androidx-test-junit = "androidx.test.ext:junit:1.2.1"
robolectric = "org.robolectric:robolectric:4.13"
truth = "com.google.truth:truth:1.4.4"

[bundles]
androidx = [
  "material",
  "androidx-appcompat",
  "androidx-coordinatorlayout",
]
androidx-test = [
  "androidx-test-core",
  "androidx-test-runner",
  "androidx-test-junit",
  "robolectric",
  "truth",
]
```

### 2. /settings.gradle.kts

```kt
pluginManagement.repositories {
  gradlePluginPortal()
  mavenCentral()
  google()
}
dependencyResolutionManagement.repositories {
  mavenCentral()
  google()
}

rootProject.name = "TempConverter1"
```

### 3. /build.gradle.kts

```kt
val releaseGroup: String by project
val releaseArtifact: String by project
val releaseVersion: String by project

val jdkVersion = JavaLanguageVersion.of(libs.versions.jdk.get())
val jreVersion = JavaLanguageVersion.of(libs.versions.jre.get())

plugins {
  alias(libs.plugins.android.application)
  checkstyle
  // required by some dependencies
  kotlin("android") version libs.versions.kotlin.get()
}

group = releaseGroup
version = releaseVersion

java.toolchain.languageVersion.set(jdkVersion)

android {
  namespace = "$releaseGroup.$releaseArtifact"
  testNamespace = "$namespace.test"
  compileSdk = libs.versions.sdk.target.get().toInt()
  defaultConfig {
    minSdk = libs.versions.sdk.min.get().toInt()
    targetSdk = libs.versions.sdk.target.get().toInt()
    version = releaseVersion
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    multiDexEnabled = true
    applicationId = namespace
  }
  compileOptions {
    sourceCompatibility = JavaVersion.toVersion(jreVersion)
    targetCompatibility = JavaVersion.toVersion(jreVersion)
  }
  testOptions.unitTests.isIncludeAndroidResources = true
  buildTypes {
    debug {
      enableAndroidTestCoverage = true
    }
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android.txt"),
        "proguard-rules.pro",
      )
    }
  }
}

checkstyle.toolVersion = libs.versions.checkstyle.get()

dependencies {
  checkstyle(libs.rulebook.checkstyle)

  implementation(libs.bundles.androidx)

  testImplementation(libs.bundles.androidx.test)
}

tasks.register<Checkstyle>("checkstyle") {
  group = LifecycleBasePlugin.VERIFICATION_GROUP
  source("src")
  include("**/*.java")
  exclude("**/gen/**", "**/R.java")
  classpath = files()
}
```

## XML

### 4. /lint.xml

```xml
<lint>
  <!-- TOML property variable is not always the latest. -->
  <issue id="GradleDependency" severity="ignore"/>

  <!-- Drawables are official Material Symbols. -->
  <issue id="VectorPath" severity="ignore"/>

  <!-- Sample application does not have an icon. -->
  <issue id="MissingApplicationIcon" severity="ignore"/>

  <!-- Deliberate violation. -->
  <issue id="UsingOnClickInXml" severity="ignore"/>
</lint>
```

### 5. /src/main/AndroidManifest.xml

```xml
<manifest>
  <application
    android:allowBackup="true"
    android:label="TempConverter1"
    android:supportsRtl="true"
    android:theme="@style/Theme.MaterialComponents.DayNight.NoActionBar">
    <activity
      android:name=".MainActivity"
      android:exported="true"
      android:windowSoftInputMode="adjustResize">
      <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
      </intent-filter>
    </activity>
  </application>
</manifest>
```

### 6. /src/main/res/drawable/

```
drawable/
├ autumn.xml
├ error.xml
├ hi.xml
├ info.xml
├ spring.xml
├ summer.xml
└ winter.xml
```

### 7. /src/main/res/layout/

#### 7a. activity_main.xml

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  tools:context=".MainActivity">

  <com.google.android.material.appbar.AppBarLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.google.android.material.appbar.MaterialToolbar
      android:id="@+id/toolbar"
      android:layout_width="match_parent"
      android:layout_height="?actionBarSize"/>
  </com.google.android.material.appbar.AppBarLayout>

  <LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical"
    android:padding="32dp"
    app:layout_behavior="@string/appbar_scrolling_view_behavior">

    <LinearLayout
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:gravity="center"
      android:orientation="horizontal">

      <com.google.android.material.textview.MaterialTextView
        android:layout_width="128dp"
        android:layout_height="wrap_content"
        android:text="@string/colon_temperature"
        android:textAppearance="@style/TextAppearance.Material3.TitleMedium"/>

      <EditText
        android:id="@+id/input"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/desc_temperature"
        android:importantForAutofill="no"
        android:inputType="numberDecimal|numberSigned"/>
    </LinearLayout>

    <LinearLayout
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:layout_marginTop="8dp"
      android:layout_marginBottom="64dp"
      android:gravity="center"
      android:orientation="horizontal">

      <com.google.android.material.textview.MaterialTextView
        android:layout_width="128dp"
        android:layout_height="wrap_content"
        android:text="@string/colon_unit"
        android:textAppearance="@style/TextAppearance.Material3.TitleMedium"/>

      <RadioGroup
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <RadioButton
          android:id="@+id/fahrenheitRadio"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:checked="true"
          android:onClick="calculate"
          android:text="@string/fahrenheit"/>

        <RadioButton
          android:id="@+id/celsiusRadio"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:layout_marginStart="8dp"
          android:onClick="calculate"
          android:text="@string/celsius"/>
      </RadioGroup>
    </LinearLayout>

    <ImageView
      android:id="@+id/image"
      android:layout_width="128dp"
      android:layout_height="128dp"
      android:contentDescription="@string/desc_season"
      android:src="@drawable/hi"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/titleText"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:textAppearance="@style/TextAppearance.Material3.HeadlineLarge"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/subtitleText"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginTop="16dp"
      android:gravity="center"
      android:text="@string/desc_welcome"
      android:textAppearance="@style/TextAppearance.Material3.HeadlineSmall"/>
  </LinearLayout>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

#### 7b. activity_main.xml (landscape)

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
  android:layout_width="match_parent"
  android:layout_height="match_parent">

  <com.google.android.material.appbar.AppBarLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.google.android.material.appbar.MaterialToolbar
      android:id="@+id/toolbar"
      android:layout_width="match_parent"
      android:layout_height="?actionBarSize"/>
  </com.google.android.material.appbar.AppBarLayout>

  <LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:baselineAligned="false"
    android:orientation="horizontal"
    android:padding="16dp"
    app:layout_behavior="@string/appbar_scrolling_view_behavior">

    <LinearLayout
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:layout_weight="2"
      android:gravity="center"
      android:orientation="vertical"
      android:padding="16dp">

      <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:orientation="horizontal">

        <com.google.android.material.textview.MaterialTextView
          android:layout_width="128dp"
          android:layout_height="wrap_content"
          android:text="@string/colon_temperature"
          android:textAppearance="@style/TextAppearance.Material3.TitleMedium"/>

        <EditText
          android:id="@+id/input"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:hint="@string/desc_temperature"
          android:importantForAutofill="no"
          android:inputType="numberDecimal|numberSigned"/>
      </LinearLayout>

      <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:gravity="center"
        android:orientation="horizontal">

        <com.google.android.material.textview.MaterialTextView
          android:layout_width="128dp"
          android:layout_height="wrap_content"
          android:text="@string/colon_unit"
          android:textAppearance="@style/TextAppearance.Material3.TitleMedium"/>

        <RadioGroup
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:orientation="horizontal">

          <RadioButton
            android:id="@+id/fahrenheitRadio"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:checked="true"
            android:onClick="calculate"
            android:text="@string/fahrenheit"/>

          <RadioButton
            android:id="@+id/celsiusRadio"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:onClick="calculate"
            android:text="@string/celsius"/>
        </RadioGroup>
      </LinearLayout>
    </LinearLayout>

    <LinearLayout
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:layout_weight="3"
      android:gravity="center"
      android:orientation="vertical"
      android:paddingStart="16dp"
      android:paddingEnd="16dp">

      <ImageView
        android:id="@+id/image"
        android:layout_width="128dp"
        android:layout_height="128dp"
        android:contentDescription="@string/desc_season"
        android:src="@drawable/hi"/>

      <com.google.android.material.textview.MaterialTextView
        android:id="@+id/titleText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textAppearance="@style/TextAppearance.Material3.HeadlineLarge"/>

      <com.google.android.material.textview.MaterialTextView
        android:id="@+id/subtitleText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:gravity="center"
        android:text="@string/desc_welcome"
        android:textAppearance="@style/TextAppearance.Material3.HeadlineSmall"/>
    </LinearLayout>
  </LinearLayout>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

### 8. /src/main/res/values/

#### 8a. colors.xml

```xml
<resources>
  <resources>
  <color name="winter">#2196f3</color>
  <color name="summer">#ff9800</color>
  <color name="spring">#4caf50</color>
  <color name="autumn">#795548</color>
  <color name="error">#f44336</color> <!-- Red 500 -->
</resources>
```

#### 8b. strings.xml

```xml
<resources>
  <string name="about">About</string>
  <string name="celsius">Celsius</string>
  <string name="fahrenheit">Fahrenheit</string>

  <string name="colon_temperature">Temperature:</string>
  <string name="colon_unit">Unit:</string>

  <string name="app_about">A simple temperature converter app.</string>

  <string name="desc_welcome">Oh, hi there!</string>
  <string name="desc_temperature">Enter conversion input.</string>
  <string name="desc_season">Predicted season given the temperature value.</string>
  <string name="desc_season_winter">It\'s getting chilly… Winter has arrived!</string>
  <string name="desc_season_summer">The sun is shining bright &#8212; Summer has arrived!</string>
  <string name="desc_season_spring">The flowers are blooming, Spring is here!</string>
  <string name="desc_season_autumn">The leaves are falling; Autumn is here!</string>
  <string name="desc_season_error">That temperature seems a bit off!</string>
</resources>
```

### 9. /src/main/res/menu/activity_main.xml

```xml
<menu>
  <item
    android:id="@+id/about"
    android:icon="@drawable/info"
    android:title="@string/about"
    app:showAsAction="always"/>
</menu>
```

## Java

### 10. /src/main/java/com/example/tempconverter1/

#### 10a. AboutDialog.java

```java
/**
 * A simple dialog describing what the application does. This dialog must be
 * attached to a {@link DialogFragment}.
 */
public class AboutDialog extends DialogFragment {
  public static final String TAG = "AboutDialog";

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    return new AlertDialog.Builder(requireContext())
      .setTitle(R.string.about)
      .setMessage(R.string.app_about)
      .setPositiveButton(android.R.string.ok, (dialog, which) -> {})
      .create();
  }
}
```

#### 10b. MusicAdapter.java

```java
/**
 * The first and only activity of this application. It uses the deprecated XML
 * {@code OnClick} binding into {@link #calculate(View)} to update controls
 * based on user input.
 */
public class MainActivity extends AppCompatActivity {
  Toolbar toolbar;
  EditText input;
  RadioButton fahrenheitRadio;
  RadioButton celsiusRadio;
  ImageView image;
  TextView titleText;
  TextView subtitleText;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    toolbar = findViewById(R.id.toolbar);
    input = findViewById(R.id.input);
    fahrenheitRadio = findViewById(R.id.fahrenheitRadio);
    celsiusRadio = findViewById(R.id.celsiusRadio);
    image = findViewById(R.id.image);
    titleText = findViewById(R.id.titleText);
    subtitleText = findViewById(R.id.subtitleText);

    setSupportActionBar(toolbar);

    input.addTextChangedListener(
      new TextWatcher() {
        @Override
        public void beforeTextChanged(
          CharSequence charSequence,
          int i,
          int i1,
          int i2
        ) {}

        @Override
        public void onTextChanged(
          CharSequence charSequence,
          int i,
          int i1,
          int i2
        ) {
          calculate(
            fahrenheitRadio.isChecked()
              ? fahrenheitRadio
              : celsiusRadio
          );
        }

        @Override
        public void afterTextChanged(Editable editable) {}
      }
    );
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.about) {
      new AboutDialog().show(getSupportFragmentManager(), AboutDialog.TAG);
    }
    return super.onOptionsItemSelected(item);
  }

  public void calculate(View view) {
    String s = input.getText().toString();
    if (TextUtils.isEmpty(s) || s.equals("-")) {
      image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.hi));
      image.clearColorFilter();
      titleText.setText(null);
      subtitleText.setText(R.string.desc_welcome);
      return;
    }

    Temperature temperature =
      new Temperature(
        Double.parseDouble(s),
        view == fahrenheitRadio
          ? Temperature.Unit.FAHRENHEIT
          : Temperature.Unit.CELSIUS
      );
    temperature.convert();

    String title = temperature.toString();
    Season season = temperature.getSeason();
    int color = season.getColor(this);
    image.setImageDrawable(season.getLogo(this));
    image.setColorFilter(color);
    titleText.setText(title);
    titleText.setTextColor(color);
    subtitleText.setText(season.getDescription(this));
  }
}
```

#### 10c. Season.java

```java
/**
 * Temporal seasons in enumeration to obtain local resources defined in
 * constructor parameters.
 */
public enum Season {
  WINTER(R.drawable.winter, R.color.winter, R.string.desc_season_winter),
  SUMMER(R.drawable.summer, R.color.summer, R.string.desc_season_summer),
  SPRING(R.drawable.spring, R.color.spring, R.string.desc_season_spring),
  AUTUMN(R.drawable.autumn, R.color.autumn, R.string.desc_season_autumn),
  ERROR(R.drawable.error, R.color.error, R.string.desc_season_error);

  private final int drawableId;
  private final int colorId;
  private final int textId;

  Season(
    @DrawableRes int drawableId,
    @ColorRes int colorId,
    @StringRes int textId
  ) {
    this.drawableId = drawableId;
    this.colorId = colorId;
    this.textId = textId;
  }

  @NonNull
  public Drawable getLogo(@NonNull Context context) {
    return Objects
      .requireNonNull(ContextCompat.getDrawable(context, drawableId));
  }

  @ColorInt
  public int getColor(@NonNull Context context) {
    return ContextCompat.getColor(context, colorId);
  }

  @NonNull
  public CharSequence getDescription(@NonNull Context context) {
    return context.getText(textId);
  }
}
```

#### 10d. Temperature.java

```java
/**
 * Data class that represents a temperature value in fahrenheit or celsius.
 */
public class Temperature {
  private static final ValueRange RANGE_FAHRENHEIT = ValueRange.of(-130, 140);
  private static final ValueRange RANGE_CELSIUS = ValueRange.of(-90, 60);

  private Double value;
  private Unit unit;

  /**
   * Define temperature value and unit of this temperature.
   *
   * @param value a decimal.
   * @param unit celsius or fahrenheit.
   */
  public Temperature(double value, @NonNull Unit unit) {
    this.value = value;
    this.unit = unit;
  }

  /**
   * Returns the predicted season given current temperature, or
   * {@link Season#ERROR} if user enters a temperature that does not fall within
   * reasonable range.
   */
  @NonNull
  public Season getSeason() {
    if (unit == Unit.FAHRENHEIT) {
      if (!RANGE_FAHRENHEIT.isValidIntValue(value.intValue())) {
        return Season.ERROR;
      } else if (value >= 90) {
        return Season.SUMMER;
      } else if (value >= 70) {
        return Season.SPRING;
      } else if (value >= 50) {
        return Season.AUTUMN;
      }
      return Season.WINTER;
    }
    if (!RANGE_CELSIUS.isValidIntValue(value.intValue())) {
      return Season.ERROR;
    } else if (value >= 30) {
      return Season.SUMMER;
    } else if (value >= 20) {
      return Season.SPRING;
    } else if (value >= 10) {
      return Season.AUTUMN;
    }
    return Season.WINTER;
  }

  /**
   * Toggles temperature unit, thereby changing its value.
   */
  public void convert() {
    if (unit == Unit.FAHRENHEIT) {
      value = (value - 32) * 5 / 9;
      unit = Unit.CELSIUS;
      return;
    }
    value = value * 9 / 5 + 32;
    unit = Unit.FAHRENHEIT;
  }

  @NonNull
  @Override
  public String toString() {
    return String.format(Locale.US, "%3.1f \u00B0%s", value, unit.toString());
  }

  public enum Unit {
    FAHRENHEIT {
      @NonNull
      @Override
      public String toString() {
        return "F";
      }
    },
    CELSIUS {
      @NonNull
      @Override
      public String toString() {
        return "C";
      }
    },
  }
}
```

## Tests

### 11. /src/test/AndroidManifest.xml

```xml
<manifest>
  <uses-sdk tools:overrideLibrary="androidx.test.core"/>

  <application/>
</manifest>
```

### 14. /src/test/java/com/example/tempconverter1/MainActivityTest.java

```java
@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class MainActivityTest {
  private MainActivity activity;

  @Before
  public void setup() {
    activity = Robolectric.buildActivity(MainActivity.class).setup().get();
  }

  @Test
  public void fahrenheitToCelsius() {
    activity.input.setText("10");

    assertThat(activity.titleText.getText().toString()).isEqualTo("-12.2 °C");
  }

  @Test
  public void celsiusToFahrenheit() {
    activity.celsiusRadio.setChecked(true);
    activity.input.setText("0");

    assertThat(activity.titleText.getText().toString()).isEqualTo("32.0 °F");
  }
}
```
