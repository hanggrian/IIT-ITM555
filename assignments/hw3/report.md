# [Homework 3](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/hw3.docx): FamousQuotes Report

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
sdk-min = "31"
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
androidx-core-splashscreen = "androidx.core:core-splashscreen:1.0.1"
androidx-palette = "androidx.palette:palette:1.0.0"
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
  "androidx-core-splashscreen",
  "androidx-palette",
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

rootProject.name = "FamousQuotes"
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

  <!-- Steve Jobs' images are provided by lecturer. -->
  <issue id="IconDensities" severity="ignore"/>

  <!-- Custom item layout are more flexible. -->
  <issue id="UseCompoundDrawables" severity="ignore"/>
</lint>
```

### 5. /src/main/AndroidManifest.xml

```xml
<manifest>
  <application
    android:label="FamousQuotes"
    android:supportsRtl="true"
    android:theme="@style/Theme.Quote">
    <activity
      android:name=".QuoteReaderActivity"
      android:exported="true">
      <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
      </intent-filter>
    </activity>
    <activity
      android:name=".QuoteDetailActivity"
      android:exported="true"
      android:theme="@style/Theme.Quote.Detail"/>
  </application>
</manifest>
```

### 6. /src/main/res/drawable/

```
drawable/
├ apple.jpg
├ info.xml
├ splash_branding.jpg
├ splash_icon.jpg
├ steve1_thumb.jpg
├ steve1.jpg
├ steve2_thumb.jpg
├ steve2.jpg
├ steve3_thumb.jpg
├ steve3.jpg
├ steve4_thumb.jpg
├ steve4.jpg
├ steve5_thumb.jpg
├ steve5.jpg
├ steve6_thumb.jpg
├ steve6.jpg
├ steve7_thumb.jpg
├ steve7.jpg
├ steve8_thumb.jpg
├ steve8.jpg
├ steve9_thumb.jpg
├ steve9.jpg
└ steve10_thumb.xml
```

### 7. /src/main/res/layout/

#### 7a. activity_quote_detail.xml

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  tools:context=".QuoteDetailActivity">

  <com.google.android.material.appbar.AppBarLayout
    android:id="@+id/appbar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.google.android.material.appbar.MaterialToolbar
      android:id="@+id/toolbar"
      android:layout_width="match_parent"
      android:layout_height="?actionBarSize"
      app:title="@string/quote"/>
  </com.google.android.material.appbar.AppBarLayout>

  <androidx.core.widget.NestedScrollView
    android:id="@+id/scrollView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    app:layout_behavior="@string/appbar_scrolling_view_behavior">

    <LinearLayout
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:orientation="vertical"
      android:padding="16dp">

      <ImageView
        android:id="@+id/image"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:contentDescription="@string/desc_steve"
        android:src="@drawable/steve1"/>

      <com.google.android.material.textview.MaterialTextView
        android:id="@+id/text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:justificationMode="inter_word"
        android:textAppearance="@style/TextAppearance.Material3.BodyLarge"/>
    </LinearLayout>
  </androidx.core.widget.NestedScrollView>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

#### 7b. activity_quote_reader.xml

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  tools:context=".QuoteReaderActivity">

  <com.google.android.material.appbar.AppBarLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.google.android.material.appbar.MaterialToolbar
      android:id="@+id/toolbar"
      android:layout_width="match_parent"
      android:layout_height="?actionBarSize"/>
  </com.google.android.material.appbar.AppBarLayout>

  <ListView
    android:id="@+id/list"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"/>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

#### 7c. item_quote.xml

```xml
<LinearLayout
  android:layout_width="match_parent"
  android:layout_height="100dp"
  android:gravity="center_vertical"
  android:orientation="horizontal"
  android:paddingStart="16dp"
  android:paddingEnd="16dp">

  <ImageView
    android:id="@+id/image"
    android:layout_width="64dp"
    android:layout_height="64dp"
    android:contentDescription="@string/desc_steve"
    android:scaleType="centerCrop"/>

  <com.google.android.material.textview.MaterialTextView
    android:id="@+id/text"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginStart="16dp"
    android:ellipsize="end"
    android:singleLine="true"
    android:textAppearance="@style/TextAppearance.Material3.TitleMedium"/>
</LinearLayout>
```

### 8. /src/main/res/values/

#### 8a. strings.xml

```xml
<resources>
  <string name="about">About</string>
  <string name="quote">Quote</string>

  <string name="app_about">Wise quotes from Apple founder Steve Jobs.</string>

  <string name="desc_steve">Photograph of Steve Jobs.</string>

  <string name="quote1">
    Innovation distinguished between a leader and a follower
  </string>
  <string name="quote2">
    I want to put a ding in the universe!
  </string>
  <string name="quote3">
    People don\'t know what they want until you show it to them.
  </string>
  <string name="quote4">
    The only way to be truly satisfied is to do what you believe is great work.
  </string>
  <string name="quote5">
    That\'s been one of my mantras — focus and simplicity. Simple can be harder than complex: You have to work hard to get your thinking clean to make it simple. But it\'s worth it in the end because once you get there, you can move mountains.
  </string>
  <string name="quote6">
    Great things in business are never done by one person, they\'re done by a team of people.
  </string>
  <string name="quote7">
    You can\'t connect the dots looking forward; you can only connect them looking backward.
  </string>
  <string name="quote8">
    Your work is going to fill a large part of your life, and the only way to be truly satisfied is to do what you believe is great work.
  </string>
  <string name="quote9">
    I\'m the only person I know that\'s lost a quarter of a billion dollars in one year. … It\'s very character-building.
  </string>
  <string name="quote10">
    It\'s more fun to be a pirate than join the Navy.
  </string>
</resources>
```

#### 8b. styles.xml

```xml
<resources>
  <style name="Theme.Quote" parent="Theme.Material3.DayNight.NoActionBar">
    <item name="android:windowSplashScreenBackground">?colorPrimary</item>
    <item name="android:windowSplashScreenAnimatedIcon">
      @drawable/splash_icon
    </item>
    <item name="android:windowSplashScreenIconBackgroundColor">
      @android:color/white
    </item>
    <item name="android:windowSplashScreenBrandingImage">
      @drawable/splash_branding
    </item>
  </style>
  <style
    name="Theme.Quote.Detail"
    parent="Theme.Material3.DayNight.NoActionBar">
    <item name="android:windowDrawsSystemBarBackgrounds">true</item>
  </style>
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

### 10. /src/main/java/com/example/quotes/

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

#### 10b. DataSource.java

```java
/**
 * A group of Steve Jobs' picture and quote resource IDs in array lists. This
 * class is serializable because it is passed to {@link QuoteDetailActivity}
 * using {@link android.content.Intent#getSerializableExtra}.
 */
public final class DataSource implements Serializable {
  private final List<Integer> quotes = new ArrayList<>();
  private final List<Integer> photos = new ArrayList<>();
  private final List<Integer> photoThumbnails = new ArrayList<>();

  /**
   * The default constructor, initialize array lists upon creation.
   */
  public DataSource() {
    setupQuotes();
    setupPhotos();
    setupPhotoThumbnails();
  }

  /**
   * Returns collection of quote resource IDs.
   */
  @NonNull
  public List<Integer> getQuotes() {
    return quotes;
  }

  /**
   * Returns collection of high-quality photos resource IDs.
   */
  @NonNull
  public List<Integer> getPhotos() {
    return photos;
  }

  /**
   * Returns collection of thumbnail photos resource IDs.
   */
  @NonNull
  public List<Integer> getPhotoThumbnails() {
    return photoThumbnails;
  }

  /**
   * Returns the current size.
   */
  public int getLength() {
    return photos.size();
  }

  private void setupQuotes() {
    quotes.add(R.string.quote1);
    quotes.add(R.string.quote2);
    quotes.add(R.string.quote3);
    quotes.add(R.string.quote4);
    quotes.add(R.string.quote5);
    quotes.add(R.string.quote6);
    quotes.add(R.string.quote7);
    quotes.add(R.string.quote8);
    quotes.add(R.string.quote9);
    quotes.add(R.string.quote10);
  }

  private void setupPhotos() {
    photos.add(R.drawable.steve1);
    photos.add(R.drawable.steve2);
    photos.add(R.drawable.steve3);
    photos.add(R.drawable.steve4);
    photos.add(R.drawable.steve5);
    photos.add(R.drawable.steve6);
    photos.add(R.drawable.steve7);
    photos.add(R.drawable.steve8);
    photos.add(R.drawable.steve9);
    photos.add(R.drawable.apple);
  }

  private void setupPhotoThumbnails() {
    photoThumbnails.add(R.drawable.steve1_thumb);
    photoThumbnails.add(R.drawable.steve2_thumb);
    photoThumbnails.add(R.drawable.steve3_thumb);
    photoThumbnails.add(R.drawable.steve4_thumb);
    photoThumbnails.add(R.drawable.steve5_thumb);
    photoThumbnails.add(R.drawable.steve6_thumb);
    photoThumbnails.add(R.drawable.steve7_thumb);
    photoThumbnails.add(R.drawable.steve8_thumb);
    photoThumbnails.add(R.drawable.steve9_thumb);
    photoThumbnails.add(R.drawable.steve10_thumb);
  }
}
```

#### 10c. QuoteDetailActivity.java

```java
/**
 * The next activity that receives extra data from {@link QuoteReaderActivity}.
 * Background and text colors are dynamically-generated from
 * {@link Palette.Builder#generate()}.
 */
public class QuoteDetailActivity extends AppCompatActivity {
  public static final String EXTRA_POSITION = "position";
  public static final String EXTRA_DATA_SOURCE = "data_source";

  AppBarLayout appbar;
  Toolbar toolbar;
  NestedScrollView scrollView;
  ImageView image;
  TextView text;

  private Palette palette;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quote_detail);
    appbar = findViewById(R.id.appbar);
    toolbar = findViewById(R.id.toolbar);
    scrollView = findViewById(R.id.scrollView);
    image = findViewById(R.id.image);
    text = findViewById(R.id.text);

    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    int position = intent.getIntExtra(EXTRA_POSITION, 0);
    DataSource source =
        Objects.requireNonNull(
          (DataSource) intent.getSerializableExtra(EXTRA_DATA_SOURCE)
        );
    toolbar.setSubtitle("#" + position);
    image.setImageResource(source.getPhotos().get(position));
    text.setText(getString(source.getQuotes().get(position)));

    palette =
      Palette
        .from(((BitmapDrawable) image.getDrawable()).getBitmap())
        .generate();
    setPaletteColor(
      Target.DARK_VIBRANT,
      color -> {
        appbar.setBackgroundColor(color);

        Window window = getWindow();
        window.setStatusBarColor(color);
        window.setNavigationBarColor(color);
      }
    );
    setPaletteColor(
      Target.DARK_MUTED,
      color -> scrollView.setBackgroundColor(color)
    );

    setPaletteColor(Target.VIBRANT, color -> toolbar.setTitleTextColor(color));
    setPaletteColor(
      Target.LIGHT_VIBRANT,
      color -> toolbar.setSubtitleTextColor(color)
    );
    setPaletteColor(Target.MUTED, color -> text.setTextColor(color));
  }

  private void setPaletteColor(
    @NonNull Target target,
    @NonNull Consumer<Integer> consumer
  ) {
    int color = palette.getColorForTarget(target, Color.TRANSPARENT);
    if (color != Color.TRANSPARENT) {
      consumer.accept(color);
    }
  }
}
```

#### 10d. QuoteReaderActivity.java

```java
/**
 * The first activity that is shown after splash screen displayed using
 * {@link SplashScreen#installSplashScreen(android.app.Activity)}. It contains a
 * list control of Steve Jobs' quotes.
 */
public class QuoteReaderActivity extends AppCompatActivity {
  private static final long DURATION_SPLASH = 5000L;

  View content;
  Toolbar toolbar;
  ListView list;

  private DataSource source;
  private boolean isReady;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    SplashScreen.installSplashScreen(this);
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_quote_reader);
    content = findViewById(android.R.id.content);
    toolbar = findViewById(R.id.toolbar);
    list = findViewById(R.id.list);

    setSupportActionBar(toolbar);

    source = new DataSource();

    list.setAdapter(new QuoteAdapter(this, source));
    list.setOnItemClickListener(
      (parent, view, position, id) -> {
        Intent intent =
          new Intent(QuoteReaderActivity.this, QuoteDetailActivity.class);
        intent.putExtra(QuoteDetailActivity.EXTRA_POSITION, position);
        intent.putExtra(QuoteDetailActivity.EXTRA_DATA_SOURCE, source);
        startActivity(intent);
      }
    );

    content.getViewTreeObserver().addOnPreDrawListener(
      new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
          if (isReady) {
            content.getViewTreeObserver().removeOnPreDrawListener(this);
          }
          return isReady;
        }
      }
    );
    new Handler(Looper.getMainLooper())
      .postDelayed(() -> isReady = true, DURATION_SPLASH);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_quote_reader, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.about) {
      new AboutDialog().show(getSupportFragmentManager(), AboutDialog.TAG);
    }
    return super.onOptionsItemSelected(item);
  }

  /**
   * List adapter in the main activity.
   */
  public static class QuoteAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final List<Integer> thumbnails;
    private final List<Integer> quotes;

    /**
     * Create a new adapter with defined collections.
     *
     * @param context active activity.
     * @param source list item content.
     */
    public QuoteAdapter(@NonNull Context context, @NonNull DataSource source) {
      inflater =
        (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      thumbnails = source.getPhotoThumbnails();
      quotes = source.getQuotes();
    }

    @Override
    public int getCount() {
      return thumbnails.size();
    }

    @Override
    public Object getItem(int position) {
      return quotes.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        convertView = inflater.inflate(R.layout.item_quote, parent, false);
      }

      ImageView thumbnail = convertView.findViewById(R.id.image);
      TextView quote = convertView.findViewById(R.id.text);

      thumbnail.setImageResource(thumbnails.get(position));
      quote.setText(quotes.get(position));

      return convertView;
    }
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

### 14. /src/test/java/com/example/quotes/

#### 14a. QuoteDetailActivityTest.java

```java
@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class QuoteDetailActivityTest {
  private QuoteDetailActivity activity;

  @Before
  public void setup() {
    Intent intent = new Intent();
    intent.putExtra(QuoteDetailActivity.EXTRA_POSITION, 0);
    intent.putExtra(QuoteDetailActivity.EXTRA_DATA_SOURCE, new DataSource());
    activity =
      Robolectric
        .buildActivity(QuoteDetailActivity.class, intent)
        .setup()
        .get();
  }

  @Test
  public void intentReceiving() {
    assertThat(activity.image.getDrawable().getConstantState())
      .isEqualTo(
        Objects
          .requireNonNull(activity.getDrawable(R.drawable.steve1))
          .getConstantState()
      );
    assertThat(activity.text.getText())
      .isEqualTo(activity.getString(R.string.quote1));
  }
}
```

#### 14b. QuoteReaderActivityTest.java

```java
@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class QuoteReaderActivityTest {
  private QuoteReaderActivity activity;

  @Before
  public void setup() {
    activity =
      Robolectric
        .buildActivity(QuoteReaderActivity.class)
        .setup()
        .get();
  }

  @Test
  public void quoteContent() {
    ListAdapter adapter = activity.list.getAdapter();

    assertThat(adapter.getItem(0)).isEqualTo(R.string.quote1);
    assertThat(adapter.getItemId(0)).isEqualTo(0);

    int lastIndex = adapter.getCount() - 1;
    assertThat(adapter.getItem(lastIndex)).isEqualTo(R.string.quote10);
    assertThat(adapter.getItemId(lastIndex)).isEqualTo(lastIndex);
  }
}
```
