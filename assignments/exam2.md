<style type="text/css">ol { list-style-type: upper-alpha; }</style>

# Final exam

## Problem 1

> If an Activity is vulnerable to being killed by the Android operating system
  due to low memory, the Activity can save state information to a Bundle object
  by using which callback method?
>
> 1.  `onStop()`
> 1.  `isFinishing()`
> 1.  `onSavelnstanceState()`
> 1.  `killProcesses()`
> 1.  `saveIntent()`

**C.** `onSavelnstanceState()`

## Problem 2

> In the current Android Studio the minimum sok version for targeting devices
  can be found where?
>
> 1.  AndroidManifest
> 1.  settings.gradle file
> 1.  The gradle build file for the app
> 1.  The gradle build file for the project

**C.** The gradle build file for the app

## Problem 3

> The first phase of program development is to ______.
>
> 1.  code the program
> 1.  design the user interface then set its properties
> 1.  gather program requirements
> 1.  test the program

**C.** gather program requirements

## Problem 4

> Which statement establishes a currency decimal format so that the variable
  `itemCost` is displayed in U.S. dollar format in the following statement?
>
> ```java
> USdollars.format(itemCost);
> ```
>
> 1.  DecimalFormat USdollars = DecimalFormat("$###,###.##");
> 1.  DecimalFormat USdollars = CurrencyFormat("$###, ###.##");
> 1.  DecimalFormat USdollars = new DecimalFormat("$###,###.##");
> 1.  CurrencyFormat USdollars = new DecimalFormat("$###,###-##);

**C.** DecimalFormat USdollars = new DecimalFormat("$###,###.##");

## Problem 5

> Checking the progress of a background operation involves what generic types of
  an AsyncTask?
>
> 1.  Integer, String, and ArraryList<>
> 1.  Void, Void, Void
> 1.  Params, Progress, and Result
> 1.  Void... params

**C.** Params, Progress, and Result

## Problem 6

> For what purpose is a fragment commonly used?
>
> 1.  To allow application components such as activities and services to
      communicate with one another
> 1.  To measure the amount of battery usage
> 1.  To avoid app crashes due to memory leaks
> 1.  To hold the code and screen logic for placing the same user interface
      component in multiple screens

**D.** To hold the code and screen logic for placing the same user interface
component in multiple screens

## Problem 7

> The ______ method creates an intent to start another Activity.
>
> 1.  `beginActivity`
> 1.  `createActivity`
> 1.  `prepareActivity`
> 1.  `startActivity`

**D.** `startActivity`

## Problem 8

> Apps can talk to each other very simply using ______.
>
> 1.  activities
> 1.  classes
> 1.  intents
> 1.  values

**C.** intents

## Problem 9

> The ______ property sets the content for a TextView control.
>
> 1.  content
> 1.  display
> 1.  text
> 1.  view

**C.** text

## Problem 10

> In Android Studio, this is the window(s) that contains the application folders
  (ex. main folder for java activity files, res folder, etc) for a project.
>
> 1.  Packages
> 1.  Project Files
> 1.  Project
> 1.  Tests
> 1.  Both Project and Project Files

**B.** Project Files

## Problem 11

> Which is an exception thrown when the SQLite database can't be opened? Choose
  the most suitable response.
>
> 1.  Exception
> 1.  OpenDatabaseException
> 1.  SQLException
> 1.  SQLiteException

**D.** SQLiteException

## Problem 12

> `onUpgrade` is called when?
>
> 1.  When an alter command is given
> 1.  When a database table needs dropping
> 1.  When updates to a given table is needed
> 1.  When a database has a version change

**D.** When a database has a version change

## Problem 13

> Which command can help tap into the Android Emulator's internal memory bank.
>
> 1.  `adb devices`
> 1.  `adb device`
> 1.  `adb start-server`
> 1.  `adb shell`

**D.** adb shell

## Problem 14

> Which function call can write/read to/from an sqlite database?
>
> 1.  `getWritableDatabase()`
> 1.  `getReadableDatabase()`
> 1.  `onOpen()`
> 1.  `onCreate(SQLiteDatabase database)`

**A.** getWritableDatabase()

## Problem 15

> What would be a primary purpose of the database helper class?
>
> 1.  Allows for the immediate opening of a database when the app first starts.
> 1.  Allows a deferral for any opening or updating of a database to its first
      use.
> 1.  Includes complexities to allow content providers access to files.
> 1.  Causes concern over whether the application was terminated.

**B.** Allows a deferral for any opening or updating of a database to its first
use.

## Problem 16

> What method(s) fire(s) if no flags are set for configuration changes due to an
  orientation change?
>
> 1.  onCreate
> 1.  onStart
> 1.  onOrientationChange
> 1.  onRestart
> 1.  Both onCreate and onStart cycle methods fire

**E.** Both onCreate and onStart cycle methods fire

## Problem 17

> To easily view an SqLite schema for all BookDB tables you can type at an
  sqlite prompt ______.
>
> 1.  .schema
> 1.  .schema Books
> 1.  select * from books;
> 1.  .metadata

**A.** .schema

## Problem 18

> The method that hides an Activity is ______.
>
> 1.  `onDestroy`
> 1.  `onHide`
> 1.  `onPause`
> 1.  `onStop`

**C.** `onPause`

## Problem 19

> Designing a program is analogous to ______.
>
> 1.  constructing a building
> 1.  doing laundry
> 1.  painting with watercolor
> 1.  performing an experiment

**A.** constructing a building

## Problem 20

> The splash screen provides time for the Android to ______.
>
> 1.  bootstrap the phone driver
> 1.  download updates
> 1.  initialize resources for your app
> 1.  stream data from Google

**C.** initialize resources for your app

## Problem 21

> As you ready your final project app for Google Play, what are some key
  features you can claim that would make your app an attractive sell?

A simple and pleasing Ul/UX are some of the attractive selling points that apply
to every app category. An app may claim to have better stability or more
features than its counterpart. However, if users struggle with basic navigation,
the app will be judged harshly based on what users see on their screens.

## Problem 22

> Name at least three use cases Firebase Realtime Database can be utilized
  building an Android app.

In a Pub/Sub messaging model like Firebase Realtime Database, subscribers are
notified when the publisher makes changes. It contrasts traditional models such
as REST API when the client needs to check for every change manually. Several
good cases include:

- Notification and logging: The client can efficiently retrieve information on
  demand instead of on an interval.
- Messaging and streaming: The publisher broadcasts real-time data necessary for
  a chat room operation.
- Backup and mirroring: Creates copies of the existing database for uptime
  redundancy or backup during downtime.

## Problem 23

> Firebase Real Time Database stores data it the cloud in which format? What are
  some chief advantages of this format?

Firebase arranges data in a JSON tree format like most NoSQL database vendors.
Compared to conventional SQL, JSON is much more straightforward but lacks
relational capability among schema and columns. NoSQL is a popular starter
database for its ease of use and portability.

## Problem 24

> Explain how to implement onClickListener for a button. Show any code logic to
  implement the click event handler.

The function `setOnClickListener` is available on all Android views, it accepts
an interface with a single method, which can be replaced with Java 8 lambda.

```java
View button = activity.findViewByld(R.id.my_button);
button.setOnClickListener(
  () -> {
    doSomething(;
    doSomethingElse);
  }
);
```

## Problem 25

> List advantages and disadvantages of using a Firebase real time database.

As discussed earlier regarding the Firebase real-time database, its strengths
are:

> - Efficient communication
> - Instant feedback
> - Distributed operation
> - Free limited plan

Nevertheless, due to its proprietary nature, I believe Firebase is not the
solution for every project requirement. Being controlled by a single company,
the application is likely to be dependent on its preferred ecosystem and
disregard competition. In this case, Google could decide to drop iOS support or
hinder a migration to other services.

## Problem 26

> AsyncTask performs what duties in Android? What are some background processes
  that need to be performed using Async? Name at least two.

In Android, `AsyncTask` executes an action in the background and delivers the
result in the Ul thread. Performing a task in a worker thread is crucial to
maintaining app fluidity by not interfering with main code execution. Their
common usages are as follows:

> - Network and database: Responses from external sources are unpredictable.
    Their failure or success results are determined in the background.
> - Long operation: Complex math problems or collection manipulation should not
    block UI.

## Problem 27

> What is FCM and why is useful? What are the processes involved?

Firebase Cloud Messaging is a free push notification service that allows clients
to distribute messages passed along Google-backed servers. To engage in FCM
communication, the app developers need to set up a Firebase project with a valid
Google account. The API credentials are then authenticated in the project source
code along with the application logic.

## Problem 28

> Running tasks & background processing problem.
>
> Set an initial background image covering your entire screen. Change the
  background image every five seconds. Use three different types of images and
  design a UI into something like a slideshow.
>
> **Grad requirement:**
>
> Include also the date and a time stamp (to include current hours / min / sec)
  shown on screen with each image change.
>
> Include a font size with at least 21dp.
>
> ### MainActivity.java
>
> ```java
> public class MainActivity extends AppCompatActivity
>   implements View.OnClickListener {
>   Button button;
>
>   @Override
>   public void onCreate(Bundle savedInstanceState) {
>     super.onCreate(savedInstanceState);
>     setContentView(R.layout.activity_main);
>     button = (Button) findViewById(R.id.button);
>   }
>
>   public void onClick(View view) {
>     if (view.getId() == R.id.button) {
>       new MyTask().execute("");
>     }
>   }
>
>   private class MyTask extends AsyncTask <String, Void, String> {
>     int i;
>     @Override
>     protected String doInBackground(String... params) {
>       for (i = 0; i < 5; i++) {
>         try {
>           Thread.sleep(2000);
>         } catch (InterruptedException e) {
>           Thread.interrupted();
>         }
>       }
>       return "Executed";
>     }
>
>     @Override
>     protected void onPostExecute(String result) {
>       EditText edit = findViewById(R.id.edit);
>       edit.setText("Executed");
>       button.setVisibility(View.VISIBLE);
>     }
>
>     @Override
>     protected void onPreExecute() {
>       button.setVisibility(View.INVISIBLE);
>     }
>
>     @Override
>     protected void onProgressUpdate(Void... values) {
>     }
>   }
> }
> ```
>
> ### activity_main.xml
>
> ```xml
> <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
>   android:layout_width="wrap_content"
>   android:layout_height="match_parent"
>   android:orientation="vertical">
>
>   <EditText
>     android:id="@+id/edit"
>     android:layout_width="wrap_content"
>     android:layout_height="wrap_content"
>     android:duplicateParentState="true"
>     android:text="Testing 123..."/>
>
>   <Button
>     android:id="@+id/button"
>     android:onClick="onClick"
>     android:layout_width="wrap_content"
>     android:layout_height="wrap_content"
>     android:duplicateParentState="true"
>     android:text="Submit"/>
> </LinearLayout>
> ```

### activity_main.xml

While the example contains a button to start the task, the solution below
automatically starts the task when the activity is created, removing the need
for the button.

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="wrap_content"
  android:layout_height="match_parent"
  android:orientation="vertical"
  android:gravity="center">

  <ImageView
    android:id="@+id/image"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>

  <TextView
    android:id="@+id/text"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:fontSize="21sp"/>
</LinearLayout>
```

### MainActivity.java

Using local drawable resources for the example images, the `AsyncTask`
increments the image index every five seconds, updating the picture and the
timestamp on the screen.

```java
public class MainActivity extends AppCompatActivity {
  private static final DATE_FORMAT =
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  ImageView image;
  TextView text;
  int i = 0;
  int[] images = {
    R.drawable.image1,
    R.drawable.image2,
    R.drawable.image3,
    R.drawable.image4,
    R.drawable.image5,
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    image = (ImageView) findViewById(R.id.image);
    text = (TextView) findViewById(R.id.text);

    new ImageTask().execute("");
  }

  private class ImageTask extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... params) {
      for (i = 0; i < 5; i++) {
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          Thread.interrupted();
        }
        publishProgress(i);
      }
      return "Executed";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      image.setImageResource(images[values[0]]);
      text.setText(DATE_FORMAT.format(new Date()));
    }
  }
}
```
