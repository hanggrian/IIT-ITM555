# [Homework 2](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/hw2.docx): TemperatureConverter2 Report

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
sdk-min = "26"
sdk-target = "34"
android-plugin = "8.6.1"
androidx = "1.7.0"
androidx-lifecycle = "2.8.6"
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
androidx-recyclerview = "androidx.recyclerview:recyclerview:1.3.2"
androidx-lifecycle-viewmodel = {
  module = "androidx.lifecycle:lifecycle-viewmodel",
  version.ref = "androidx-lifecycle",
}
androidx-lifecycle-livedata = {
  module = "androidx.lifecycle:lifecycle-livedata",
  version.ref = "androidx-lifecycle",
}
androidx-lifecycle-extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
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
  "androidx-recyclerview",
  "androidx-lifecycle-viewmodel",
  "androidx-lifecycle-livedata",
  "androidx-lifecycle-extensions",
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

rootProject.name = "TempConverter2"
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

  <!-- text1 and text2 have short text and should not overlap. -->
  <issue id="RelativeOverlap" severity="ignore"/>
</lint>
```

### 5. /src/main/AndroidManifest.xml

```xml
<manifest>
  <application
    android:allowBackup="true"
    android:label="TempConverter2"
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
├ info.xml
├ weather_cloudy.xml
├ weather_rainy.xml
└ weather_sunny.xml
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
      android:layout_height="?actionBarSize"
      app:titleTextColor="?colorOnPrimarySurface"/>
  </com.google.android.material.appbar.AppBarLayout>

  <RelativeLayout
    android:id="@+id/layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:animateLayoutChanges="true"
    android:padding="16dp"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent">

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/text1"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_alignParentStart="true"
      android:layout_alignParentTop="true"
      android:textAppearance="@style/TextAppearance.AppCompat.Body1"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/text2"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_alignParentTop="true"
      android:layout_alignParentEnd="true"
      android:textAppearance="@style/TextAppearance.AppCompat.Body2"/>

    <com.google.android.material.slider.Slider
      android:id="@+id/slider"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:layout_below="@id/text1"
      android:layout_alignParentStart="true"
      android:layout_marginTop="32dp"
      android:layout_marginBottom="32dp"
      android:stepSize="1"
      android:value="0"
      android:valueFrom="-50"
      android:valueTo="50"
      app:tickVisible="false"/>

    <CheckBox
      android:id="@+id/check"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_below="@id/slider"
      android:layout_alignParentStart="true"
      android:text="@string/show_five_day_forecast"/>

    <ViewStub
      android:id="@+id/stub"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:layout="@layout/stubview"/>
  </RelativeLayout>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

#### 7b. activity_main.xml (landscape)

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
      android:layout_height="?actionBarSize"
      app:titleTextColor="?colorOnPrimarySurface"/>
  </com.google.android.material.appbar.AppBarLayout>

  <RelativeLayout
    android:id="@+id/layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:animateLayoutChanges="true"
    android:padding="16dp"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent">

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/text1"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_alignParentStart="true"
      android:layout_alignParentTop="true"
      android:textAppearance="@style/TextAppearance.AppCompat.Body1"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/text2"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_alignEnd="@id/slider"
      android:layout_alignParentTop="true"
      android:textAppearance="@style/TextAppearance.AppCompat.Body2"/>

    <com.google.android.material.slider.Slider
      android:id="@+id/slider"
      android:layout_width="512dp"
      android:layout_height="wrap_content"
      android:layout_below="@id/text1"
      android:layout_alignParentStart="true"
      android:layout_marginTop="32dp"
      android:layout_marginBottom="32dp"
      android:stepSize="1"
      android:value="0"
      android:valueFrom="-50"
      android:valueTo="50"
      app:tickVisible="false"/>

    <CheckBox
      android:id="@+id/check"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_alignParentTop="true"
      android:layout_alignParentEnd="true"
      android:text="@string/show_five_day_forecast"/>

    <ViewStub
      android:id="@+id/stub"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:layout="@layout/stubview"/>
  </RelativeLayout>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

#### 7c. stubview.xml

```xml
<LinearLayout
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:gravity="center_horizontal"
  android:orientation="vertical">

  <com.google.android.material.textview.MaterialTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginBottom="16dp"
    android:text="@string/five_day_forecast"
    android:textAppearance="@style/TextAppearance.MaterialComponents.Subtitle1"/>

  <androidx.recyclerview.widget.RecyclerView
    android:id="@+id/list"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    android:layout_weight="1"
    android:clipToPadding="false"
    app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"/>

  <com.google.android.material.button.MaterialButton
    android:id="@+id/button"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/back"/>
</LinearLayout>
```

### 8. /src/main/res/values/colors.xml

```xml
<resources>
  <string name="about">About</string>
  <string name="back">Back</string>
  <string name="show_five_day_forecast">Show 5-Day Forecast</string>
  <string name="five_day_forecast">5-Day Forecast (2024)</string>

  <string name="app_about">
    A continuation of temperature app with stub view and list view adapter.
  </string>

  <string name="desc_weather">
    An image depicting weather of temperature entry.
  </string>

  <!--
    5-Day Chicago forecast starting from 22th September 2024, data obtained from
    AccuWeather.
  -->
  <string-array name="wkTemps">
    <item>71 °F</item>
    <item>66 °F</item>
    <item>64 °F</item>
    <item>72 °F</item>
    <item>70 °F</item>
  </string-array>
  <string-array name="wkDates">
    <item>Sunday, September 22</item>
    <item>Monday, September 23</item>
    <item>Tuesday, September 24</item>
    <item>Wednesday, September 25</item>
    <item>Thursday, September 26</item>
  </string-array>
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

### 10. /src/main/java/com/example/tempconverter2/

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
 * Adapter for the forecast recycler view in the stub view.
 */
public class ForecastAdapter extends
  RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
  private String[] temperatures;
  private String[] dates;

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(
    @NonNull ViewGroup parent,
    int viewType
  ) {
    temperatures =
      parent.getContext().getResources().getStringArray(R.array.wkTemps);
    dates = parent.getContext().getResources().getStringArray(R.array.wkDates);
    return new ViewHolder(
      LayoutInflater.from(
        parent.getContext()).inflate(R.layout.stubview_item, parent, false
      )
    );
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.title.setText(temperatures[position]);
    holder.subtitle.setText(dates[position]);

    switch (position) {
      case 0:
        holder.image.setImageResource(R.drawable.weather_cloudy);
        break;
      case 1:
      case 2:
        holder.image.setImageResource(R.drawable.weather_rainy);
        break;
      case 3:
      default:
        holder.image.setImageResource(R.drawable.weather_sunny);
        break;
    }
  }

  @Override
  public int getItemCount() {
    return 5;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView image;
    TextView title;
    TextView subtitle;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
        title = itemView.findViewById(R.id.title);
        subtitle = itemView.findViewById(R.id.subtitle);
    }
  }
}
```

#### 10c. MainActivity.java

```java
/**
 * The main activity of Temperature Converter 2 sample application. Unlike the
 * first iteration of the app that accepts user text input, the second app
 * converts temperature value using a slider. Additionally, there is a stub view
 * displaying forecast when user selects the check box.
 */
public class MainActivity extends AppCompatActivity {
  Toolbar toolbar;
  RelativeLayout layout;
  TextView text1;
  TextView text2;
  Slider slider;
  CheckBox check;

  ViewStub stub;
  RecyclerView list;
  Button button;

  MainViewModel viewModel;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    toolbar = findViewById(R.id.toolbar);
    layout = findViewById(R.id.layout);
    text1 = findViewById(R.id.text1);
    text2 = findViewById(R.id.text2);
    stub = findViewById(R.id.stub);
    check = findViewById(R.id.check);
    slider = findViewById(R.id.slider);

    setSupportActionBar(toolbar);

    View inflated = stub.inflate();
    stub.setVisibility(View.INVISIBLE);
    list = inflated.findViewById(R.id.list);
    button = inflated.findViewById(R.id.button);

    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    viewModel.temperatureData.observe(
      this,
      temperature -> {
        text1.setText(String.format("Input: %s", temperature));
        temperature.convert();
        text2.setText(String.format("Output: %s", temperature));
      }
    );

    check.setOnCheckedChangeListener(
      (v, isChecked) -> {
        int stubVisibility = isChecked ? View.VISIBLE : View.GONE;
        int nonStubVisibility = !isChecked ? View.VISIBLE : View.GONE;
        stub.setVisibility(stubVisibility);
        text1.setVisibility(nonStubVisibility);
        text2.setVisibility(nonStubVisibility);
        slider.setVisibility(nonStubVisibility);
        check.setVisibility(nonStubVisibility);
      }
    );
    slider.addOnChangeListener(
      (slider, value, fromUser) ->
        viewModel.temperatureData
          .setValue(new Temperature((int) value, Temperature.Unit.CELSIUS))
    );

    list.setAdapter(new ForecastAdapter());
    button.setOnClickListener(view -> check.setChecked(false));
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

  @Override
  public void onBackPressed() {
    if (check.isChecked()) {
      check.setChecked(false);
      return;
    }
    super.onBackPressed();
  }
}
```

#### 10d. MainViewModel.java

```java
/**
 * The view model that holds observable value. In this instance, the text
 * controls will update every time {@link #temperatureData} changes its value.
 */
public class MainViewModel extends ViewModel {
  @NonNull
  public final MutableLiveData<Temperature> temperatureData =
    new MutableLiveData<>(new Temperature(0, Temperature.Unit.CELSIUS));
}
```

#### 10e. Temperature.java

```java
/**
 * Data class that represents a temperature value in fahrenheit or celsius.
 */
public class Temperature {
  private double value;
  private Unit unit;

  /**
   * Define temperature value and unit of this temperature.
   *
   * @param value a decimal.
   * @param unit  celsius or fahrenheit.
   */
  public Temperature(double value, @NonNull Unit unit) {
    this.value = value;
    this.unit = unit;
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

### 14. /src/test/java/com/example/tempconverter2/MainActivityTest.java

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
  public void sliderValue() {
    activity.slider.setValue(10);

    assertThat(activity.text1.getText().toString())
      .isEqualTo("Input: 10.0 °C");
    assertThat(activity.text2.getText().toString())
      .isEqualTo("Output: 50.0 °F");
  }

  @Test
  public void checkState() {
    activity.check.setChecked(true);

    assertThat(activity.text1.getVisibility()).isEqualTo(View.GONE);
    assertThat(activity.text1.getVisibility()).isEqualTo(View.GONE);
    assertThat(activity.check.getVisibility()).isEqualTo(View.GONE);
    assertThat(activity.slider.getVisibility()).isEqualTo(View.GONE);
  }
}
```
