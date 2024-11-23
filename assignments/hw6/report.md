# [Homework 6](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/hw6.docx): MarksEditor Report

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
android-plugin = "8.7.2"
androidx = "1.7.0"
androidx-lifecycle = "2.8.6"
androidx-test = "1.6.1"
androidx-room = "2.6.1"

[plugins]
android-application = {
  id = "com.android.application",
  version.ref = "android-plugin",
}

[libraries]
# lint
rulebook-checkstyle = "com.hanggrian.rulebook:rulebook-checkstyle:0.1"
# main
material = {
  module = "com.google.android.material:material",
  version.ref = "androidx",
}
androidx-appcompat = {
  module = "androidx.appcompat:appcompat",
  version.ref = "androidx",
}
androidx-coordinatorlayout =
  "androidx.coordinatorlayout:coordinatorlayout:1.2.0"
androidx-lifecycle-viewmodel = {
  module = "androidx.lifecycle:lifecycle-viewmodel",
  version.ref = "androidx-lifecycle",
}
androidx-lifecycle-livedata = {
  module = "androidx.lifecycle:lifecycle-livedata",
  version.ref = "androidx-lifecycle",
}
androidx-lifecycle-extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
androidx-recyclerview = "androidx.recyclerview:recyclerview:1.3.2"
androidx-gridlayout = "androidx.gridlayout:gridlayout:1.0.0"
androidx-cardview = "androidx.cardview:cardview:1.0.0"
androidx-room-runtime = {
  module = "androidx.room:room-runtime",
  version.ref = "androidx-room",
}
androidx-room-compiler = {
  module = "androidx.room:room-compiler",
  version.ref = "androidx-room",
}
# test
androidx-test-core = {
  module = "androidx.test:core",
  version.ref = "androidx-test",
}
androidx-test-runner = {
  module = "androidx.test:runner",
  version.ref = "androidx-test",
}
androidx-test-junit = "androidx.test.ext:junit:1.2.1"

robolectric = "org.robolectric:robolectric:4.13"
truth = "com.google.truth:truth:1.4.4"

[bundles]
androidx = [
  "material",
  "androidx-appcompat",
  "androidx-coordinatorlayout",
  "androidx-lifecycle-viewmodel",
  "androidx-lifecycle-livedata",
  "androidx-lifecycle-extensions",
  "androidx-recyclerview",
  "androidx-gridlayout",
  "androidx-cardview",
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

rootProject.name = "MarksEditor"
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
  implementation(libs.androidx.room.runtime)

  annotationProcessor(libs.androidx.room.compiler)

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

  <!-- Clear and remove all call this function. -->
  <issue id="NotifyDataSetChanged" severity="ignore"/>
</lint>
```

### 5. /src/main/AndroidManifest.xml

```xml
<manifest>
  <uses-permission android:name="android.permission.INTERNET"/>

  <application
    android:label="@string/app_name"
    android:supportsRtl="true"
    android:theme="@style/Theme.Marks"
    android:usesCleartextTraffic="true">
    <activity
      android:name=".MainActivity"
      android:exported="true">
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
├ btn_send.xml
└ ic_info.xml
```

### 7. /src/main/res/layout/

#### 7a. activity_main.xml

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  tools:context=".MainActivity">

  <com.google.android.material.appbar.AppBarLayout
    android:id="@+id/appbarLayout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="?colorSurfaceVariant">

    <com.google.android.material.appbar.CollapsingToolbarLayout
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      app:contentScrim="?colorSurfaceVariant"
      app:layout_scrollFlags="scroll|exitUntilCollapsed"
      app:titleEnabled="false">

      <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:paddingStart="32dp"
        android:paddingTop="?actionBarSize"
        android:paddingEnd="32dp"
        android:paddingBottom="32dp">

        <com.google.android.material.textfield.TextInputLayout
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:hint="@string/id">

          <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/idEdit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="number"/>
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_marginTop="16dp"
          android:hint="@string/name">

          <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/nameEdit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPersonName"/>
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_marginTop="16dp"
          android:hint="@string/mark">

          <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/valueEdit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textCapCharacters"/>
        </com.google.android.material.textfield.TextInputLayout>
      </LinearLayout>

      <com.google.android.material.appbar.MaterialToolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?actionBarSize"
        app:layout_collapseMode="pin"/>
    </com.google.android.material.appbar.CollapsingToolbarLayout>
  </com.google.android.material.appbar.AppBarLayout>

  <FrameLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:animateLayoutChanges="true"
    app:layout_behavior="@string/appbar_scrolling_view_behavior">

    <androidx.recyclerview.widget.RecyclerView
      android:id="@+id/recycler"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:clipToPadding="false"
      android:orientation="vertical"
      android:padding="16dp"
      app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
      app:spanCount="3"/>

    <LinearLayout
      android:id="@+id/emptyView"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_gravity="center_horizontal"
      android:layout_marginTop="160dp"
      android:gravity="center"
      android:orientation="vertical">

      <com.google.android.material.textview.MaterialTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/empty_desc1"
        android:textAlignment="center"
        android:textAppearance="@style/TextAppearance.Material3.DisplayLarge"/>

      <com.google.android.material.textview.MaterialTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:text="@string/empty_desc2"
        android:textAlignment="center"
        android:textAppearance="@style/TextAppearance.Material3.BodyLarge"/>
    </LinearLayout>
  </FrameLayout>

  <com.google.android.material.floatingactionbutton.FloatingActionButton
    android:id="@+id/action"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginEnd="32dp"
    android:contentDescription="@string/btn_send_desc"
    android:visibility="gone"
    app:layout_anchor="@id/appbarLayout"
    app:layout_anchorGravity="bottom|end"
    app:srcCompat="@drawable/btn_send"/>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

#### 7b. activity_main.xml (landsdcape)

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  tools:context=".MainActivity">

  <com.google.android.material.appbar.AppBarLayout
    android:id="@+id/appbarLayout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="?colorSurfaceVariant">

    <com.google.android.material.appbar.CollapsingToolbarLayout
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      app:contentScrim="?colorSurfaceVariant"
      app:layout_scrollFlags="scroll|exitUntilCollapsed"
      app:titleEnabled="false">

      <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:paddingStart="160dp"
        android:paddingTop="?actionBarSize"
        android:paddingEnd="160dp"
        android:paddingBottom="32dp">

        <com.google.android.material.textfield.TextInputLayout
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:hint="@string/id">

          <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/idEdit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="number"/>
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_marginTop="16dp"
          android:hint="@string/name">

          <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/nameEdit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPersonName"/>
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_marginTop="16dp"
          android:hint="@string/mark">

          <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/valueEdit"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textCapCharacters"/>
        </com.google.android.material.textfield.TextInputLayout>
      </LinearLayout>

      <com.google.android.material.appbar.MaterialToolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?actionBarSize"
        app:layout_collapseMode="pin"/>
    </com.google.android.material.appbar.CollapsingToolbarLayout>
  </com.google.android.material.appbar.AppBarLayout>

  <FrameLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:animateLayoutChanges="true"
    app:layout_behavior="@string/appbar_scrolling_view_behavior">

    <androidx.recyclerview.widget.RecyclerView
      android:id="@+id/recycler"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:clipToPadding="false"
      android:orientation="vertical"
      android:padding="16dp"
      app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
      app:spanCount="6"/>

    <LinearLayout
      android:id="@+id/emptyView"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_gravity="center_horizontal"
      android:layout_marginTop="160dp"
      android:gravity="center"
      android:orientation="vertical">

      <com.google.android.material.textview.MaterialTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/empty_desc1"
        android:textAlignment="center"
        android:textAppearance="@style/TextAppearance.Material3.DisplayLarge"/>

      <com.google.android.material.textview.MaterialTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:text="@string/empty_desc2"
        android:textAlignment="center"
        android:textAppearance="@style/TextAppearance.Material3.BodyLarge"/>
    </LinearLayout>
  </FrameLayout>

  <com.google.android.material.floatingactionbutton.FloatingActionButton
    android:id="@+id/action"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginEnd="32dp"
    android:contentDescription="@string/btn_send_desc"
    android:visibility="gone"
    app:layout_anchor="@id/appbarLayout"
    app:layout_anchorGravity="bottom|end"
    app:srcCompat="@drawable/btn_send"/>
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

#### 7c. item_mark.xml

```xml
<androidx.cardview.widget.CardView
  android:layout_width="match_parent"
  android:layout_height="wrap_content"
  android:layout_margin="8dp"
  android:foreground="?selectableItemBackground">

  <androidx.gridlayout.widget.GridLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <TextView
      android:id="@+id/nameText"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:ellipsize="end"
      android:maxLines="1"
      android:textAppearance="@style/TextAppearance.Material3.BodyMedium"
      app:layout_columnSpan="2"
      app:layout_row="0"/>

    <TextView
      android:id="@+id/valueText"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:textAppearance="@style/TextAppearance.Material3.DisplayLarge"
      app:layout_column="0"
      app:layout_columnWeight="1"
      app:layout_row="1"/>

    <TextView
      android:id="@+id/idText"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_marginStart="16dp"
      android:textAppearance="@style/TextAppearance.Material3.LabelLarge"
      app:layout_column="1"
      app:layout_columnWeight="0"
      app:layout_row="1"/>
  </androidx.gridlayout.widget.GridLayout>
</androidx.cardview.widget.CardView>
```

### 8. /src/main/res/values

#### 8a. strings.xml

```xml
<resources>
  <string name="about">About</string>
  <string name="confirm">Confirm</string>
  <string name="id">ID</string>
  <string name="mark">Mark</string>
  <string name="name">Name</string>
  <string name="reset">Reset</string>

  <string name="app_name">MarksEditor</string>
  <string name="app_about">Store student grades in persistent SQLite.</string>

  <string name="btn_send_desc">Add mark to table.</string>
  <string name="empty_desc1">🍃</string>
  <string name="empty_desc2">Nothing to see here.</string>
  <string name="dialog_delete_desc">Are you sure to delete %1$d. %2$s?</string>
  <string name="dialog_clear_desc">Are you sure to clear all entries?</string>
  <string name="menu_reset_desc">Local database cleared.</string>
</resources>
```

#### 8b. styles.xml

```xml
<resources>
  <style
    name="Theme.Marks"
    parent="@style/Theme.Material3.DayNight.NoActionBar">
    <item name="android:statusBarColor">?colorSurfaceVariant</item>
  </style>
</resources>
```

### 9. /src/main/res/menu/activity_main.xml

```xml
<menu>
  <item
    android:id="@+id/about"
    android:icon="@drawable/ic_info"
    android:title="@string/about"
    app:showAsAction="ifRoom"/>

  <item
    android:id="@+id/reset"
    android:title="@string/reset"
    app:showAsAction="never"/>
</menu>
```

## Java

### 11. /src/main/java/com/example/marks/db

#### 11a. MarkDatabase.java

```java
/**
 * A local SQLite database accessed through modern <i>Room</i> implementation.
 */
@Database(entities = {Mark.class}, version = 1)
public abstract class MarkDatabase extends RoomDatabase {
  public static final String NAME = "marks-db";

  public abstract Marks marks();

  @NonNull
  public static MarkDatabase from(@NonNull Context context) {
    return from(context, false);
  }

  @NonNull
  public static MarkDatabase from(@NonNull Context context, boolean isTest) {
    Builder<MarkDatabase> builder =
      Room.databaseBuilder(context, MarkDatabase.class, NAME);
    if (isTest) {
      builder = builder.allowMainThreadQueries();
    }
    return builder.build();
  }
}
```

#### 11b. Marks.java

```java
/**
 * A Data Access Object for {@link Mark} entity.
 */
@Dao
public interface Marks {
  @Query("SELECT COUNT(*) FROM mark")
  int getCount();

  @Query("SELECT EXISTS(SELECT 1 FROM mark WHERE id = :id)")
  boolean isExist(int id);

  @Query("SELECT * FROM mark")
  List<Mark> getAll();

  @Query("SELECT * FROM mark WHERE id IN (:ids)")
  List<Mark> loadAllByIds(int[] ids);

  @Query("SELECT * FROM mark WHERE id = :id LIMIT 1")
  Mark findById(String id);

  @Insert
  void insertAll(Mark... marks);

  @Update
  void update(Mark mark);

  @Delete
  void delete(Mark mark);

  @Query("DELETE FROM mark")
  void deleteAll();
}
```

#### 11c. schema/Mark.java

```java
/**
 * A Plain Old Java Object representing information of students enrolled in a
 * course.
 */
@Entity(tableName = "mark")
public class Mark implements Serializable {
  public static final Pattern PATTERN_NAME =
    Pattern.compile("^[A-Z][a-zA-Z'-]+( [A-Z][a-zA-Z'-]+)*$");
  public static final Pattern PATTERN_VALUE = Pattern.compile("^[A-F]$");

  @PrimaryKey
  private int id;

  @ColumnInfo(name = "name")
  private String name;

  @ColumnInfo(name = "value")
  private char value;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public char getValue() {
    return value;
  }

  public void setValue(char value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Mark other = (Mark) obj;
    return id == other.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @NonNull
  @Override
  public String toString() {
    String[] names = name.split(" ");
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < names.length; i++) {
      builder
        .append(
          i == 0
            ? names[i].charAt(0) + "."
            : names[i]
        ).append(' ');
    }
    return builder.toString();
  }

  public static class Builder {
    private int id;
    private String name;
    private char value;

    @NonNull
    public Builder id(int id) {
      this.id = id;
      return this;
    }

    @NonNull
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    @NonNull
    public Builder value(char value) {
      this.value = value;
      return this;
    }

    @NonNull
    public Mark build() {
      Mark mark = new Mark();
      mark.id = id;
      mark.name = name;
      mark.value = value;
      return mark;
    }
  }
}
```

### 12. /src/main/java/com/example/marks

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

#### 12b. MainActivity.java

```java
/**
 * The only activity in the application, responsible for displaying items with
 * {@link #adapter} and connecting to database in {@link #viewModel}.
 */
public class MainActivity extends AppCompatActivity {
  Toolbar toolbar;
  EditText idEdit;
  EditText nameEdit;
  EditText valueEdit;
  RecyclerView recycler;
  View emptyView;
  FloatingActionButton action;

  MainViewModel viewModel;
  MarkAdapter adapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    toolbar = findViewById(R.id.toolbar);
    idEdit = findViewById(R.id.idEdit);
    nameEdit = findViewById(R.id.nameEdit);
    valueEdit = findViewById(R.id.valueEdit);
    recycler = findViewById(R.id.recycler);
    emptyView = findViewById(R.id.emptyView);
    action = findViewById(R.id.action);

    setSupportActionBar(toolbar);

    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    viewModel.db = MarkDatabase.from(this);
    adapter = new MarkAdapter(viewModel.marks);
    adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override
      public void onItemRangeInserted(int positionStart, int itemCount) {
        onChanged();
      }

      @Override
      public void onItemRangeRemoved(int positionStart, int itemCount) {
        onChanged();
      }

      @Override
      public void onChanged() {
        if (viewModel.marks.isEmpty()) {
          recycler.setVisibility(View.GONE);
          emptyView.setVisibility(View.VISIBLE);
          return;
        }
        recycler.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
      }
    });

    Tasks.executeResult(
      () -> viewModel.db.marks().getAll(),
      list -> {
        viewModel.marks.clear();
        viewModel.marks.addAll(list);
        adapter.notifyDataSetChanged();
      }
    );

    recycler.setAdapter(adapter);

    action.setOnClickListener(
      v -> {
        int id = Integer.parseInt(idEdit.getText().toString().trim());
        Tasks.executeResult(
          () -> {
            if (!viewModel.db.marks().isExist(id)) {
              Mark mark =
                new Mark.Builder()
                  .id(id)
                  .name(nameEdit.getText().toString().trim())
                  .value(valueEdit.getText().toString().trim().charAt(0))
                  .build();
              viewModel.db.marks().insertAll(mark);
              return mark;
            }
            Mark mark = viewModel.db.marks().findById(String.valueOf(id));
            mark.setName(nameEdit.getText().toString().trim());
            mark.setValue(valueEdit.getText().toString().trim().charAt(0));
            viewModel.db.marks().update(mark);
            return mark;
          }, (mark) -> {
            int index = viewModel.marks.indexOf(mark);
            if (index > -1) {
              viewModel.marks.set(index, mark);
              adapter.notifyItemChanged(index);
            } else {
              if (viewModel.marks.add(mark)) {
                adapter.notifyItemInserted(viewModel.marks.size() - 1);
              }
            }
            idEdit.setText("");
            nameEdit.setText("");
            valueEdit.setText("");
            idEdit.requestFocus();
          }
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
          action.setVisibility(
            TextUtils.isEmpty(idEdit.getText())
              || !Mark.PATTERN_NAME.matcher(nameEdit.getText()).matches()
              || !Mark.PATTERN_VALUE.matcher(valueEdit.getText()).matches()
              ? View.GONE
              : View.VISIBLE
          );
        }

        @Override
        public void afterTextChanged(Editable s) {}
      };
    idEdit.addTextChangedListener(watcher);
    nameEdit.addTextChangedListener(watcher);
    valueEdit.addTextChangedListener(watcher);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Objects.requireNonNull(viewModel.db).close();
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
    } else if (item.getItemId() == R.id.reset) {
      new ConfirmDialog().show(getSupportFragmentManager(), ConfirmDialog.TAG);
    }
    return super.onOptionsItemSelected(item);
  }

  public static class ConfirmDialog extends DialogFragment {
    public static final String TAG = "MainActivity.ConfirmDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      return new AlertDialog.Builder(requireContext())
        .setTitle(R.string.confirm)
        .setMessage(R.string.dialog_clear_desc)
        .setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
        .setPositiveButton(
          android.R.string.ok,
          (dialog, which) -> {
            MainActivity activity = (MainActivity) requireActivity();
            Tasks.execute(
              () ->
                Objects
                  .requireNonNull(activity.viewModel.db)
                  .marks()
                  .deleteAll(),
              () ->
                Snackbar
                  .make(
                    activity.action,
                    R.string.menu_reset_desc,
                    Snackbar.LENGTH_INDEFINITE
                  ).setAction(
                    android.R.string.ok,
                    v -> {
                      int size = activity.viewModel.marks.size();
                      activity.viewModel.marks.clear();
                      activity.adapter.notifyItemRangeRemoved(0, size);
                    }
                  ).show()
            );
          }
        ).create();
    }
  }
}
```

#### 12c. MainViewModel.java

```java
/**
 * A container that retains complex dataset in the event of configuration
 * changes, such as screen rotation.
 */
public class MainViewModel extends ViewModel {
  /**
   * The SQLite database connected via <i>Room</i> library, this property needs
   * to be initialized during startup and closed after destroy.
   */
  @Nullable
  public MarkDatabase db = null;

  /**
   * A collection for card view.
   */
  @NonNull
  public final List<Mark> marks = new ArrayList<>();
}
```

#### 12d. MarkAdapter.java

```java
/**
 * An adapter that controls mark's card view and behavior.
 */
public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.ViewHolder> {
  private final List<Mark> marks;

  public MarkAdapter(List<Mark> marks) {
    this.marks = marks;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(
    @NonNull ViewGroup parent,
    int viewType
  ) {
    return new ViewHolder(
      LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.item_mark, parent, false)
    );
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Mark mark = marks.get(position);
    holder.idText.setText(String.format("#%s", mark.getId()));
    holder.nameText.setText(mark.toString());
    holder.valueText.setText(String.valueOf(mark.getValue()));

    holder.itemView.setOnClickListener(
      v ->
        Snackbar
          .make(
            v,
            String.format(
              Locale.US,
              "%d. %s has a grade of %s",
              mark.getId(),
              mark.getName(),
              mark.getValue()
            ), Snackbar.LENGTH_SHORT
          ).show()
    );
    holder.itemView.setOnLongClickListener(
      v -> {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConfirmDialog.EXTRA_MARK, mark);

        DialogFragment dialog = new ConfirmDialog();
        dialog.setArguments(bundle);
        dialog.show(
          ((AppCompatActivity) v.getContext()).getSupportFragmentManager(),
          ConfirmDialog.TAG
        );
        return false;
      }
    );
  }

  @Override
  public int getItemCount() {
    return marks.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    TextView idText;
    TextView nameText;
    TextView valueText;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      idText = itemView.findViewById(R.id.idText);
      nameText = itemView.findViewById(R.id.nameText);
      valueText = itemView.findViewById(R.id.valueText);
    }
  }

  public static class ConfirmDialog extends DialogFragment {
    public static final String TAG = "MarkAdapter.ConfirmDialog";
    public static final String EXTRA_MARK = "MARK";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      Mark mark =
        Objects.requireNonNull(
          (Mark) requireArguments().getSerializable(EXTRA_MARK)
        );
      return new AlertDialog.Builder(requireContext())
        .setTitle(R.string.confirm)
        .setMessage(
          getString(
            R.string.dialog_delete_desc,
            mark.getId(),
            mark.getName()
          )
        ).setNegativeButton(android.R.string.cancel, (dialog, which) -> {})
        .setPositiveButton(
          android.R.string.ok,
          (dialog, which) -> {
            MainActivity activity = (MainActivity) requireActivity();
            Tasks.executeResult(
              () -> {
                Objects
                  .requireNonNull(activity.viewModel.db)
                  .marks()
                  .delete(mark);
                return mark;
              }, mark2 -> {
                activity.viewModel.marks.remove(mark2);
                int index = activity.viewModel.marks.indexOf(mark2);
                if (index == -1) {
                  return;
                }
                activity.viewModel.marks.remove(mark2);
                activity.adapter.notifyItemRemoved(index);
              }
            );
          }
        ).create();
    }
  }
}
```

#### 12e. Tasks.java

```java
/**
 * Utility class to conveniently use {@link Executors}, which in itself is a
 * replacement to {@link android.os.AsyncTask} because the latter is obsolete.
 */
public final class Tasks {
  private Tasks() {}

  /**
   * Execute background and ui workers without result.
   *
   * @param backgroundAction non-blocking worker in the background.
   * @param uiAction final action in the UI.
   */
  public static void execute(
    @NonNull Runnable backgroundAction,
    @NonNull Runnable uiAction
  ) {
    Executors
      .newSingleThreadExecutor()
      .execute(
        () -> {
          backgroundAction.run();
          new Handler(Looper.getMainLooper())
            .post(uiAction);
        }
      );
  }

  /**
   * Execute background worker with result delivered to ui worker.
   *
   * @param backgroundAction non-blocking worker in the background.
   * @param uiAction final action in the UI.
   * @param <T> return type of background worker.
   */
  public static <T> void executeResult(
    @NonNull Callable<T> backgroundAction,
    @NonNull Consumer<T> uiAction
  ) {
    Executors
      .newSingleThreadExecutor()
      .execute(
        () -> {
          final T result;
          try {
            result = backgroundAction.call();
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
          new Handler(Looper.getMainLooper())
            .post(() -> uiAction.accept(result));
        }
      );
  }
}
```

## Tests

### 14. /src/test/AndroidManifest.xml

```xml
<manifest>
  <uses-sdk tools:overrideLibrary="androidx.test.core"/>

  <application/>
</manifest>
```

### 15. /src/test/java/com/example/marks

#### 15a. MainActivityTest.java

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
  public void buttonVisibility() {
    assertThat(activity.action.getVisibility())
      .isEqualTo(View.GONE);

    activity.idEdit.setText("0");
    activity.nameEdit.setText("John Doe");
    activity.valueEdit.setText("A");

    assertThat(activity.action.getVisibility())
      .isEqualTo(View.VISIBLE);
  }
}
```

#### 15b. MarksTest.java

```java
@RunWith(RobolectricTestRunner.class)
@DoNotInstrument
public class MarksTest {
  private MarkDatabase db;

  @Before
  public void setup() {
    db =
      MarkDatabase.from(
        Robolectric
          .buildActivity(MainActivity.class)
          .setup()
          .get()
          .getApplicationContext(),
        true
      );
  }

  @Test
  public void test() {
    Marks marks = db.marks();
    marks.deleteAll();

    // create
    Mark mark =
      new Mark.Builder()
        .id(0)
        .name("John Doe")
        .value('A')
        .build();
    marks.insertAll(mark);
    assertThat(marks.getCount())
      .isEqualTo(1);

    // read
    assertThat(mark.getId())
      .isEqualTo(0);
    assertThat(mark.getName())
      .isEqualTo("John Doe");
    assertThat(mark.getValue())
      .isEqualTo('A');

    // update
    mark.setId(1);
    mark.setName("Jane Doe");
    mark.setValue('B');
    marks.update(mark);
    assertThat(mark.getId())
      .isEqualTo(1);
    assertThat(mark.getName())
      .isEqualTo("Jane Doe");
    assertThat(mark.getValue())
      .isEqualTo('B');

    // delete
    marks.delete(mark);
    assertThat(marks.isExist(1))
      .isFalse();
  }

  @After
  public void finish() {
    db.close();
  }
}
```
