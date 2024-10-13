# [Homework 4](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/hw4.docx): Music Playlist Report

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
android-plugin = "8.7.0"
androidx = "1.7.0"
androidx-test = "1.6.1"
retrofit = "2.11.0"

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
androidx-cardview = "androidx.cardview:cardview:1.0.0"
androidx-swiperefreshlayout =
  "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
androidx-preference = "androidx.preference:preference:1.2.1"
retrofit =
  { module = "com.squareup.retrofit2:retrofit", version.ref = "retrofit" }
retrofit-gson =
  { module = "com.squareup.retrofit2:converter-gson", version.ref = "retrofit" }
guava = "com.google.guava:guava:33.3.1-android"
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
  "androidx-cardview",
  "androidx-swiperefreshlayout",
  "androidx-preference",
]
androidx-test = [
  "androidx-test-core",
  "androidx-test-runner",
  "androidx-test-junit",
  "robolectric",
  "truth",
]
retrofit = [
  "retrofit",
  "retrofit-gson",
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

rootProject.name = "MusicPlaylist"
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
  implementation(libs.bundles.retrofit)
  implementation(libs.guava)

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
</lint>
```

### 5. /src/main/AndroidManifest.xml

```xml
<manifest>
  <uses-permission android:name="android.permission.INTERNET"/>

  <application
    android:label="MusicPlaylist"
    android:networkSecurityConfig="@xml/network_security_config"
    android:supportsRtl="true"
    android:theme="@style/Theme.Material3.DayNight.NoActionBar">
    <activity
      android:name=".MainActivity"
      android:exported="true">
      <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
      </intent-filter>
    </activity>
    <activity
      android:name=".LoginActivity"
      android:exported="true"
      android:windowSoftInputMode="adjustResize"/>
  </application>
</manifest>
```

### 6. /src/main/res/drawable/

```
drawable/
├ caption_artist.xml
├ caption_price.xml
├ caption_year.xml
├ display_artist.xml
├ display_company.xml
├ display_price.xml
├ ic_info.xml
├ ic_purchase.xml
└ ic_refresh.xml
```

### 7. /src/main/res/layout/

#### 7a. activity_login.xml

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  tools:context=".LoginActivity">

  <com.google.android.material.appbar.AppBarLayout
    android:id="@+id/appbar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <com.google.android.material.appbar.MaterialToolbar
      android:id="@+id/toolbar"
      android:layout_width="match_parent"
      android:layout_height="?actionBarSize"
      app:title="@string/login_to_musicplaylist"/>
  </com.google.android.material.appbar.AppBarLayout>

  <LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    app:layout_behavior="@string/appbar_scrolling_view_behavior">

    <LinearLayout
      android:layout_width="320dp"
      android:layout_height="0dp"
      android:layout_gravity="center"
      android:layout_marginTop="32dp"
      android:layout_weight="1"
      android:orientation="vertical">

      <com.google.android.material.textfield.TextInputLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/username">

        <com.google.android.material.textfield.TextInputEditText
          android:id="@+id/usernameEdit"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"/>
      </com.google.android.material.textfield.TextInputLayout>

      <com.google.android.material.textfield.TextInputLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:hint="@string/password">

        <com.google.android.material.textfield.TextInputEditText
          android:id="@+id/passwordEdit"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:inputType="textPassword"/>
      </com.google.android.material.textfield.TextInputLayout>

      <Button
        android:id="@+id/forgotPasswordButton"
        style="@style/Widget.Material3.Button.TextButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:text="@string/forgot_password_"/>
    </LinearLayout>

    <LinearLayout
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:layout_margin="16dp"
      android:orientation="vertical">

      <Button
        android:id="@+id/aboutButton"
        style="@style/Widget.Material3.Button.OutlinedButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/about"/>

      <Button
        android:id="@+id/proceedButton"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:enabled="false"
        android:text="@string/proceed"/>
    </LinearLayout>
  </LinearLayout>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

#### 7b. activity_main.xml

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

  <androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    android:id="@+id/refreshLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_behavior="@string/appbar_scrolling_view_behavior">

    <androidx.recyclerview.widget.RecyclerView
      android:id="@+id/recycler"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:clipToPadding="false"
      app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"/>
  </androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

#### 7c. item_music_group.xml

```xml
<TextView
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:paddingStart="16dp"
  android:paddingTop="8dp"
  android:paddingEnd="16dp"
  android:paddingBottom="8dp"
  android:textAppearance="@style/TextAppearance.Material3.LabelLarge"/>
```

#### 7d. item_music_info.xml

```xml
<androidx.cardview.widget.CardView
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:layout_margin="16dp"
  app:cardCornerRadius="8dp">

  <GridLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="16dp">

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/titleText"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_row="0"
      android:layout_column="0"
      android:layout_columnSpan="2"
      android:layout_marginBottom="8dp"
      android:textAppearance="@style/TextAppearance.Material3.TitleMedium"
      android:textColor="?colorOnSurface"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/yearText"
      style="@style/Widget.Playlist.TextView.Caption"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_row="1"
      android:layout_column="0"
      android:layout_columnWeight="1"
      android:drawableStart="@drawable/caption_year"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/artistText"
      style="@style/Widget.Playlist.TextView.Caption"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_row="1"
      android:layout_column="1"
      android:drawableStart="@drawable/caption_artist"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/priceText"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_row="2"
      android:layout_column="0"
      android:layout_columnSpan="2"
      android:layout_marginTop="8dp"
      android:drawableStart="@drawable/caption_price"
      android:drawablePadding="4dp"
      android:drawableTint="?colorOnSurfaceInverse"
      android:text="@string/highest_price_desc"
      android:textAppearance="@style/TextAppearance.Material3.TitleSmall"
      android:textColor="?colorOnSurfaceInverse"
      android:visibility="gone"/>
  </GridLayout>
</androidx.cardview.widget.CardView>
```

#### 7e. dialog_music.xml

```xml
<LinearLayout
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:orientation="vertical">

  <LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:paddingStart="32dp"
    android:paddingTop="32dp"
    android:paddingEnd="32dp">

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/titleText"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_gravity="center"
      android:textAlignment="center"
      android:textAppearance="@style/TextAppearance.Material3.DisplaySmall"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/artistText"
      style="@style/Widget.Playlist.TextView.Display"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginTop="32dp"
      android:drawableStart="@drawable/display_artist"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/priceText"
      style="@style/Widget.Playlist.TextView.Display"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginTop="16dp"
      android:drawableStart="@drawable/display_price"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/companyText"
      style="@style/Widget.Playlist.TextView.Display"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginTop="16dp"
      android:drawableStart="@drawable/display_company"/>
  </LinearLayout>

  <LinearLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="end"
    android:clipToPadding="false"
    android:gravity="center"
    android:orientation="vertical"
    android:padding="32dp">

    <com.google.android.material.floatingactionbutton.FloatingActionButton
      android:id="@+id/purchaseButton"
      style="@style/Widget.Material3.FloatingActionButton.Large.Primary"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:contentDescription="@string/purchase_desc"
      android:src="@drawable/ic_purchase"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/availabilityText"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginTop="8dp"
      android:text="@string/available"
      android:textAppearance="@style/TextAppearance.Material3.LabelLarge"
      android:textColor="?colorOnSurfaceVariant"/>
  </LinearLayout>
</LinearLayout>
```

#### 7f. dialog_music.xml (landscape)

```xml
<LinearLayout
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:orientation="vertical">

  <LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:paddingStart="16dp"
    android:paddingTop="16dp"
    android:paddingEnd="16dp">

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/titleText"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_gravity="center"
      android:textAlignment="center"
      android:textAppearance="@style/TextAppearance.Material3.DisplaySmall"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/artistText"
      style="@style/Widget.Playlist.TextView.Display"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginTop="16dp"
      android:drawableStart="@drawable/display_artist"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/priceText"
      style="@style/Widget.Playlist.TextView.Display"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginTop="8dp"
      android:drawableStart="@drawable/display_price"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/companyText"
      style="@style/Widget.Playlist.TextView.Display"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginTop="8dp"
      android:drawableStart="@drawable/display_company"/>
  </LinearLayout>

  <LinearLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="end"
    android:clipToPadding="false"
    android:gravity="center"
    android:orientation="vertical"
    android:padding="16dp">

    <com.google.android.material.floatingactionbutton.FloatingActionButton
      android:id="@+id/purchaseButton"
      style="@style/Widget.Material3.FloatingActionButton.Primary"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:contentDescription="@string/purchase_desc"
      android:src="@drawable/ic_purchase"/>

    <com.google.android.material.textview.MaterialTextView
      android:id="@+id/availabilityText"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginTop="4dp"
      android:text="@string/available"
      android:textAppearance="@style/TextAppearance.Material3.LabelLarge"
      android:textColor="?colorOnSurfaceVariant"/>
  </LinearLayout>
</LinearLayout>
```

### 8. /src/main/res/values/

#### 8a. colors.xml

```xml
<resources>
  <color name="semi_transparent">#99ffffff</color>

  <!--
      Material2 colors of code 500 intensity to support both dark and light
      mode.
  -->
  <color name="red">#f44336</color>
  <color name="pink">#e91e63</color>
  <color name="purple">#9c27b0</color>
  <color name="deep_purple">#673ab7</color>
  <color name="indigo">#3f51b5</color>
  <color name="blue">#2196f3</color>
  <color name="light_blue">#03a9f4</color>
  <color name="cyan">#00bcd4</color>
  <color name="teal">#009688</color>
  <color name="green">#4caf50</color>
  <color name="light_green">#8bc34a</color>
  <color name="lime">#cddc39</color>
  <color name="yellow">#ffeb3b</color>
  <color name="amber">#ffc107</color>
  <color name="orange">#ff9800</color>
  <color name="deep_orange">#ff5722</color>
  <color name="brown">#795548</color>
  <color name="gray">#9e9e9e</color>
  <color name="blue_gray">#607d8b</color>
</resources>
```

#### 8b. strings.xml

```xml
<resources>
  <string name="about">About</string>
  <string name="available">Available</string>
  <string name="forgot_password">Forgot Password</string>
  <string name="login_to_musicplaylist">Login to MusicPlaylist</string>
  <string name="password">Password</string>
  <string name="proceed">Proceed</string>
  <string name="refresh">Refresh</string>
  <string name="unavailable">Unavailable</string>
  <string name="username">Username</string>

  <string name="app_about">
    A music playlist app that retrieves catalogue using Rest API.
  </string>

  <string name="forgot_password_">Forgot password?</string>
  <string name="forgot_password_desc">
    Username need to be at least \"4 characters\" and password is \"%s\".
  </string>
  <string name="highest_price_desc">Highest price from the catalog.</string>
  <string name="invalid_credentials_desc">
    Wrong username and password combination, check "Forgot password?" for more
    information.
  </string>
  <string name="purchase_desc">
    Purchase button is activated when music isn\'t yet sold.
  </string>
  <string name="redirecting_">Redirecting…</string>
</resources>
```

#### 8c. styles.xml

```xml
<resources>
  <style
    name="Widget.Playlist.TextView.Caption"
    parent="Widget.AppCompat.TextView">
    <item name="android:gravity">center_vertical</item>
    <item name="android:textAppearance">
      @style/TextAppearance.Material3.BodyMedium
    </item>
    <item name="android:textColor">@color/semi_transparent</item>
    <item name="android:drawablePadding">4dp</item>
    <item name="android:drawableTint">@color/semi_transparent</item>
  </style>

  <style
    name="Widget.Playlist.TextView.Display"
    parent="Widget.AppCompat.TextView">
    <item name="android:gravity">center_vertical</item>
    <item name="android:textAppearance">
      @style/TextAppearance.Material3.BodyLarge
    </item>
    <item name="android:textColor">?colorOnSurface</item>
    <item name="android:drawablePadding">8dp</item>
    <item name="android:drawableTint">?colorOnSurface</item>
  </style>
</resources>
```

### 9. /src/main/res/menu/activity_main.xml

```xml
<menu>
  <item
    android:id="@+id/refresh"
    android:icon="@drawable/ic_refresh"
    android:title="@string/refresh"
    app:showAsAction="always"/>

  <item
    android:id="@+id/about"
    android:icon="@drawable/ic_info"
    android:title="@string/about"
    app:showAsAction="always"/>
</menu>
```

### 10. res/xml/network_security_config.xml

```xml
<network-security-config>
  <domain-config cleartextTrafficPermitted="true">
    <domain includeSubdomains="true">www.papademas.net</domain>
  </domain-config>
</network-security-config>
```

## Java

### 11. /src/main/java/com/example/playlist/data/

#### 11a. Music.java

```java
public class Music implements Serializable {
  @SerializedName("SOLD")
  private Sold sold;

  @SerializedName("TITLE")
  private String title;

  @SerializedName("ARTIST")
  private String artist;

  @SerializedName("COUNTRY")
  private String country;

  @SerializedName("COMPANY")
  private String company;

  @SerializedName("PRICE")
  private Double price;

  @SerializedName("YEAR")
  private String year;

  public Sold getSold() {
    return sold;
  }

  public void setSold(Sold sold) {
    this.sold = sold;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getYear() {
    return year;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public boolean isSold() {
    return sold == Sold.YES;
  }

  public String getDisplayCountry() {
    return new Locale("", country).getDisplayCountry();
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Music other = (Music) obj;
    return sold.equals(other.sold)
      && title.equals(other.title)
      && artist.equals(other.artist)
      && country.equals(other.country)
      && company.equals(other.company)
      && price.equals(other.price)
      && year.equals(other.year);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sold, title, artist, country, company, price, year);
  }

  @NonNull
  @Override
  public String toString() {
    return String.format("%s (%s)", title, year);
  }

  public enum Sold {
    @SerializedName("yes") YES,
    @SerializedName("no") NO,
  }
}
```

#### 11b. MusicAdapter.java

```java
public class MusicCatalog {
  @SerializedName("Bluesy_Music")
  private List<Music> bluesy;

  public List<Music> getBluesy() {
    return bluesy;
  }

  public void setBluesy(List<Music> bluesy) {
    this.bluesy = bluesy;
  }
}
```

### 12. /src/main/java/com/example/playlist/

#### 12a. AboutDialog.java

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

#### 12b. LoginActivity.java

```java
/**
 * A small activity that validates user input, then stores credentials in shared
 * prefs. Specifically, the password must match {@link #DEFAULT_PASSWORD} as
 * described in {@link ForgotPasswordDialog}.
 */
public class LoginActivity extends AppCompatActivity {
  private static final String DEFAULT_PASSWORD = "itmd4555";
  private static final long WAIT_DURATION = 5000L;

  Toolbar toolbar;
  ProgressBar progress;
  EditText usernameEdit;
  EditText passwordEdit;
  Button forgotPasswordButton;
  Button aboutButton;
  Button proceedButton;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_login);
    toolbar = findViewById(R.id.toolbar);
    progress = findViewById(R.id.progress);
    usernameEdit = findViewById(R.id.usernameEdit);
    passwordEdit = findViewById(R.id.passwordEdit);
    forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
    aboutButton = findViewById(R.id.aboutButton);
    proceedButton = findViewById(R.id.proceedButton);

    setSupportActionBar(toolbar);

    forgotPasswordButton.setOnClickListener(
      v ->
        new ForgotPasswordDialog()
          .show(getSupportFragmentManager(), ForgotPasswordDialog.TAG)
    );
    aboutButton.setOnClickListener(
      v ->
        new AboutDialog()
          .show(getSupportFragmentManager(), AboutDialog.TAG)
    );
    proceedButton.setOnClickListener(
      v -> {
        enableControls(false);
        new Handler(Looper.getMainLooper()).postDelayed(
          () -> {
            enableControls(true);
            if (!passwordEdit.getText().toString().equals(DEFAULT_PASSWORD)) {
              Snackbar
                .make(
                  v,
                  R.string.invalid_credentials_desc, Snackbar.LENGTH_LONG
                ).show();
              return;
            }

            Toast
              .makeText(this, R.string.redirecting_, Toast.LENGTH_LONG)
              .show();

            SharedPreferences.Editor editor =
              PreferenceManager
                .getDefaultSharedPreferences(this)
                .edit();
            editor.putString(
              MainActivity.USERNAME_KEY,
              usernameEdit.getText().toString()
            );
            editor.apply();

            startActivity(new Intent(this, MainActivity.class));
            finish();
          },
          WAIT_DURATION
        );
      }
    );

    TextWatcher watcher =
      new TextWatcher() {
        @Override
        public void beforeTextChanged(
          CharSequence s,
          int start,
          int count,
          int after
        ) {}

        @Override
        public void onTextChanged(
          CharSequence s,
          int start,
          int before,
          int count
        ) {
          proceedButton.setEnabled(
            !usernameEdit.getText().toString().contains(" ")
              && usernameEdit.getText().length() > 3
              && passwordEdit.getText().length() > 0
          );
        }

        @Override
        public void afterTextChanged(Editable s) {}
      };
    usernameEdit.addTextChangedListener(watcher);
    passwordEdit.addTextChangedListener(watcher);
  }

  private void enableControls(boolean isEnable) {
    progress.setVisibility(isEnable ? View.GONE : View.VISIBLE);
    usernameEdit.setEnabled(isEnable);
    passwordEdit.setEnabled(isEnable);
    proceedButton.setEnabled(isEnable);

    if (!isEnable) {
      ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
          .hideSoftInputFromWindow(proceedButton.getWindowToken(), 0);
    }
  }

  public static class ForgotPasswordDialog extends DialogFragment {
    public static final String TAG = "ForgotPasswordDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      return new AlertDialog.Builder(requireContext())
        .setTitle(R.string.forgot_password)
        .setMessage(getString(R.string.forgot_password_desc, DEFAULT_PASSWORD))
        .setPositiveButton(android.R.string.ok, (dialog, which) -> {})
        .create();
    }
  }
}
```

#### 12c. MainActivity.java

```java
/**
 * Initial point of the application. When a preference of key
 * {@link #USERNAME_KEY} is not found, the activity will destroy itself and
 * redirect to login screen for registration.
 */
public class MainActivity extends AppCompatActivity {
  public static final String USERNAME_KEY = "username";

  Toolbar toolbar;
  SwipeRefreshLayout refreshLayout;
  RecyclerView recycler;

  MusicAdapter adapter;
  PapademasApi api;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    toolbar = findViewById(R.id.toolbar);
    refreshLayout = findViewById(R.id.refreshLayout);
    recycler = findViewById(R.id.recycler);

    setSupportActionBar(toolbar);

    api = PapademasApi.create();
    adapter = new MusicAdapter(this);

    recycler.setAdapter(adapter);
    refreshLayout.setOnRefreshListener(this::refresh);
    refresh();
  }

  @Override
  protected void onResume() {
    super.onResume();

    String username =
      PreferenceManager
        .getDefaultSharedPreferences(this)
        .getString(USERNAME_KEY, null);
    if (username != null) {
      toolbar.setSubtitle(username);
      return;
    }
    startActivity(new Intent(this, LoginActivity.class));
    finish();
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
    } else if (item.getItemId() == R.id.refresh) {
      refreshLayout.setRefreshing(true);
      refresh();
    }
    return super.onOptionsItemSelected(item);
  }

  private void refresh() {
    Executors.newSingleThreadExecutor().execute(
      () -> {
        try {
          MusicCatalog catalog = api.getMusicCatalog().execute().body();
          new Handler(Looper.getMainLooper()).post(
            () -> {
              refreshLayout.setRefreshing(false);
              adapter.replaceAll(
                catalog != null
                  ? catalog.getBluesy()
                  : Collections.emptyList()
              );
            }
          );
        } catch (IOException e) {
          String message = e.getMessage();
          if (message == null) {
            message = "Unknown error.";
          }
          Snackbar
            .make(recycler, message, Snackbar.LENGTH_LONG)
            .show();
        }
      }
    );
  }
}
```

### 12d. MusicAdapter.java

```java
/**
 * A list adapter with two item types: a regular music preview item and a parent
 * type to group songs based on their country of origin. This is done by
 * populating a {@link Multimap} and rebuilding a list using the keys
 * periodically slipped into the values.
 */
public class MusicAdapter extends
  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int TYPE_GROUP = 0;
    static final int TYPE_INFO = 1;

    private final List<Object> actualList = new ArrayList<>();
    private final Iterator<Integer> colorIterable;
    private final FragmentManager manager;

    public MusicAdapter(AppCompatActivity activity) {
      colorIterable =
        RandomIterable
          .of(
            ContextCompat.getColor(activity, R.color.red),
            ContextCompat.getColor(activity, R.color.pink),
            ContextCompat.getColor(activity, R.color.purple),
            ContextCompat.getColor(activity, R.color.deep_purple),
            ContextCompat.getColor(activity, R.color.indigo),
            ContextCompat.getColor(activity, R.color.blue),
            ContextCompat.getColor(activity, R.color.light_blue),
            ContextCompat.getColor(activity, R.color.cyan),
            ContextCompat.getColor(activity, R.color.teal),
            ContextCompat.getColor(activity, R.color.green),
            ContextCompat.getColor(activity, R.color.light_green),
            ContextCompat.getColor(activity, R.color.lime),
            ContextCompat.getColor(activity, R.color.yellow),
            ContextCompat.getColor(activity, R.color.amber),
            ContextCompat.getColor(activity, R.color.orange),
            ContextCompat.getColor(activity, R.color.deep_orange),
            ContextCompat.getColor(activity, R.color.brown),
            ContextCompat.getColor(activity, R.color.gray),
            ContextCompat.getColor(activity, R.color.blue_gray)
          ).iterator();
      manager = activity.getSupportFragmentManager();
    }

    public void replaceAll(Collection<Music> musics) {
      Multimap<String, Music> multimap = HashMultimap.create();
      musics.forEach(music -> multimap.put(music.getDisplayCountry(), music));

      int oldSize = actualList.size();
      actualList.clear();
      Music mostValuableMusic =
        multimap
          .values()
          .stream()
          .max(Comparator.comparing(Music::getPrice))
          .orElseThrow();
      AtomicInteger num = new AtomicInteger(1);
      multimap.keySet().forEach(
        s -> {
          actualList.add(s);
          multimap
            .get(s)
            .forEach(music ->
              actualList.add(
                new Entry(
                  num.getAndIncrement(),
                  music,
                  music.equals(mostValuableMusic))
              )
            );
        }
      );

      int newSize = actualList.size();
      if (oldSize == 0) {
        notifyItemRangeInserted(0, newSize);
      } else if (oldSize == newSize) {
        notifyItemRangeChanged(0, newSize);
      } else if (oldSize > newSize) {
        notifyItemRangeChanged(0, newSize);
        notifyItemRangeRemoved(newSize + 1, oldSize);
      } else {
        notifyItemRangeChanged(0, oldSize);
        notifyItemRangeInserted(oldSize + 1, newSize);
      }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
      @NonNull ViewGroup parent,
      int viewType
    ) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      if (viewType == TYPE_GROUP) {
        return new GroupViewHolder(
          inflater.inflate(R.layout.item_music_group, parent, false)
        );
      }
      return new ItemViewHolder(
        inflater.inflate(R.layout.item_music_info, parent, false)
      );
    }

    @Override
    public void onBindViewHolder(
      @NonNull RecyclerView.ViewHolder holder,
      int position
    ) {
      Object item = actualList.get(position);
      if (item instanceof String) {
        ((GroupViewHolder) holder).text.setText((String) item);
        return;
      }

      ItemViewHolder infoHolder = (ItemViewHolder) holder;
      Entry entry = (Entry) item;
      int color = colorIterable.next();
      infoHolder.card.setOnClickListener(
        v -> {
          Bundle bundle = new Bundle();
          bundle.putSerializable(MusicDialog.EXTRA_MUSIC, entry.music);
          bundle.putInt(MusicDialog.EXTRA_ACCENT, color);

          BottomSheetDialogFragment dialog = new MusicDialog();
          dialog.setArguments(bundle);
          dialog.show(manager, MusicDialog.TAG);
        }
      );
      infoHolder.card.setCardBackgroundColor(color);
      infoHolder.titleText.setText(
        String.format(Locale.US, "%d. %s", entry.number, entry.music.getTitle())
      );
      infoHolder.yearText.setText(entry.music.getYear());
      infoHolder.artistText.setText(entry.music.getArtist());
      infoHolder.priceText.setVisibility(
        entry.isHighestPrice
          ? View.VISIBLE
          : View.GONE
      );
    }

    @Override
    public int getItemCount() {
      return actualList.size();
    }

    @Override
    public int getItemViewType(int position) {
      return actualList.get(position) instanceof String
        ? TYPE_GROUP
        : TYPE_INFO;
    }

    private static final class Entry {
      public final int number;
      public final Music music;
      public final boolean isHighestPrice;

      private Entry(int number, Music music, boolean isHighestPrice) {
        this.number = number;
        this.music = music;
        this.isHighestPrice = isHighestPrice;
      }
    }

    /**
     * The view holder for displaying a group based on country.
     */
    public static class GroupViewHolder extends RecyclerView.ViewHolder {
      TextView text;

      public GroupViewHolder(@NonNull View itemView) {
        super(itemView);
        text = (TextView) itemView;
      }
    }

    /**
     * The view holder for music item preview.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
      CardView card;
      TextView titleText;
      TextView yearText;
      TextView artistText;
      TextView priceText;

      public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        card = (CardView) itemView;
        titleText = itemView.findViewById(R.id.titleText);
        yearText = itemView.findViewById(R.id.yearText);
        artistText = itemView.findViewById(R.id.artistText);
        priceText = itemView.findViewById(R.id.priceText);
      }
    }
}
```

#### 12e. MusicDialog.java

```java
/**
 * A dialog that attaches itself into a bottom sheet in a fragment controller,
 * showing music detail with color accent data sent from the callee.
 */
public class MusicDialog extends BottomSheetDialogFragment {
  public static final String TAG = "MusicDialog";
  public static final String EXTRA_MUSIC = "music";
  public static final String EXTRA_ACCENT = "accent";

  TextView titleText;
  TextView artistText;
  TextView priceText;
  TextView companyText;
  FloatingActionButton purchaseButton;
  TextView availabilityText;

  @Nullable
  @Override
  public View onCreateView(
    @NonNull LayoutInflater inflater,
    @Nullable ViewGroup container,
    @Nullable Bundle savedInstanceState
  ) {
    Bundle bundle = requireArguments();
    Music music =
      (Music) Objects.requireNonNull(bundle.getSerializable(EXTRA_MUSIC));
    int accent = bundle.getInt(EXTRA_ACCENT);

    View root = inflater.inflate(R.layout.dialog_music, container, false);
    titleText = root.findViewById(R.id.titleText);
    artistText = root.findViewById(R.id.artistText);
    priceText = root.findViewById(R.id.priceText);
    companyText = root.findViewById(R.id.companyText);
    purchaseButton = root.findViewById(R.id.purchaseButton);
    availabilityText = root.findViewById(R.id.availabilityText);

    titleText.setText(music.toString());
    titleText.setTextColor(accent);
    artistText.setText(music.getArtist());
    priceText
      .setText(String.format(Locale.US, "$%.2f", music.getPrice()));
    companyText
      .setText(String.format("%s, %s", music.getCompany(), music.getCountry()));
    if (music.isSold()) {
      purchaseButton.setEnabled(false);
      availabilityText.setText(R.string.unavailable);
    } else {
      purchaseButton.setBackgroundTintList(ColorStateList.valueOf(accent));
      purchaseButton.setEnabled(true);
      availabilityText.setText(R.string.available);
    }
    return root;
  }
}
```

#### 12f. PapademasApi.java

```java
/**
 * A REST API invocation site to retrieve music catalog from the Papademas
 * website.
 */
public interface PapademasApi {
  String ENDPOINT = "http://www.papademas.net:81";

  @GET("/cd_catalog.json")
  Call<MusicCatalog> getMusicCatalog();

  /**
   * Convenient method to instantiate builder with GSON converter.
   */
  @NonNull
  static PapademasApi create() {
    return new Retrofit.Builder()
      .baseUrl(ENDPOINT)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(PapademasApi.class);
  }
}
```

#### 12g. RandomIterable.java

```java
/**
 * A list that retrieves item in a randomized index but never duplicate values
 * consecutively. Because there is a requirement of no duplicate, excess
 * elements are filtered out in the {@link #actualList}.
 */
public final class RandomIterable<E> implements Iterable<E> {
  private final List<E> actualList;

  private RandomIterable(@NonNull List<E> list) {
    actualList =
      list
        .stream()
        .distinct()
        .collect(Collectors.toList());
  }

  @NonNull
  @Override
  public Iterator<E> iterator() {
    return new RandomIterator();
  }

  /**
   * Creates a random-access list from the specified array.
   *
   * @param array the array by which the list will be backed.
   */
  @NonNull
  public static <E> RandomIterable<E> of(@NonNull E... array) {
    checkArgument(array.length > 0, "Input array cannot be empty.");
    return new RandomIterable<>(Arrays.asList(array));
  }

  private final class RandomIterator implements Iterator<E> {
    private final Random random = new Random();
    private final Set<Integer> pastIndices = new HashSet<>();

    @Override
    public boolean hasNext() {
      return actualList.size() > pastIndices.size();
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Items are fixed-length.");
    }

    @Override
    public E next() {
      int index = random.nextInt(actualList.size());
      while (!pastIndices.add(index)) {
        index = random.nextInt(actualList.size());
      }
      return actualList.get(index);
    }
  }
}
```

## Tests

### 13. /src/test/AndroidManifest.xml

```xml
<manifest>
  <uses-sdk tools:overrideLibrary="androidx.test.core"/>

  <application/>
</manifest>
```

### 14. /src/test/java/com/example/playlist/

#### 14a. LoginActivityTest.java

```java
@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class LoginActivityTest {
  private LoginActivity activity;

  @Before
  public void setup() {
    activity = Robolectric.buildActivity(LoginActivity.class).setup().get();
  }

  @Test
  public void buttonState() {
    assertThat(activity.proceedButton.isEnabled()).isFalse();

    activity.usernameEdit.setText("1234");
    activity.passwordEdit.setText(" ");
    assertThat(activity.proceedButton.isEnabled()).isTrue();
  }
}
```

#### 14b. MainActivityTest.java

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
  public void recyclerAdapter() {
    try {
      List<Music> musics =
        activity.api.getMusicCatalog().execute().body().getBluesy();
      activity.adapter.replaceAll(musics);

      assertThat(activity.adapter.getItemViewType(0))
        .isEqualTo(MusicAdapter.TYPE_GROUP);
      assertThat(activity.adapter.getItemViewType(1))
        .isEqualTo(MusicAdapter.TYPE_INFO);
      assertThat(activity.adapter.getItemCount())
        .isEqualTo(
          musics.size()
            + musics
              .stream()
              .map((Function<Music, Object>) Music::getCountry)
              .distinct()
              .count()
        );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
```

#### 14c. PapademasApiTest.java

```java
public class PapademasApiTest {
  private PapademasApi api;

  @Before
  public void init() {
    api = PapademasApi.create();
  }

  @Test
  public void getBluesy() {
    try {
      List<Music> musics = api.getMusicCatalog().execute().body().getBluesy();
      assertThat(musics).isNotEmpty();

      Music music = musics.get(0);
      assertThat(music.isSold()).isTrue();
      assertThat(music.getTitle()).isEqualTo("Empires Burlesque");
      assertThat(music.getArtist()).isEqualTo("Bob Dylan");
      assertThat(music.getCountry()).isEqualTo("USA");
      assertThat(music.getCompany()).isEqualTo("Columbia");
      assertThat(music.getPrice()).isEqualTo(10.9);
      assertThat(music.getYear()).isEqualTo("1985");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
```

#### 14d. RandomIterableTest.java

```java
public class RandomIterableTest {
  @Test
  public void emptyInput() {
    assertThrows(IllegalArgumentException.class, RandomIterable::of);
  }

  @Test
  public void unchangedTotalWithIterable() {
    int total = 0;
    for (int i : RandomIterable.of(1, 2, 3, 4, 5, 6, 7, 8, 9)) {
      total += i;
    }
    assertThat(total).isEqualTo(45);
  }

  @Test
  public void distinctValuesWithIterator() {
    Iterator<Integer> iterator = RandomIterable.of(3, 1, 2, 1, 2, 3).iterator();
    assertThat(iterator.next() + iterator.next() + iterator.next())
      .isEqualTo(6);
    assertThat(iterator.hasNext()).isFalse();
  }
}
```
