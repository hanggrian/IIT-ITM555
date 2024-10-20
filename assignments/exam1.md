<style type="text/css">ol { list-style-type: upper-alpha; }</style>

# Mid exam

## Problem 1

> To add in a placeholder for a textview to include the text 'name' you would use
>
> 1.  `android:text="name"`
> 1.  `android:placeholder="name"`
> 1.  `android:overlay="name"`
> 1.  `android:hint="name"`

**D.** `android:hint="name"`

## Problem 2

> Which of the following is not a valid callback in the activities life cycle?
>
> 1.  `onRoll`
> 1.  `onCreate`
> 1.  `onStart`
> 1.  `onResume`

**A.** `onRoll`

## Problem 3

> Which is **not** a valid Android layout measurement type for **spacing**?
>
> 1.  in (inches)
> 1.  mm (millimeters)
> 1.  pt (points)
> 1.  xhdpi (extra high DPIs)

**D.** xhdpi (extra high DPIs)

## Problem 4

> The `colors.xml` file contains ______ named constants.
>
> 1.  html
> 1.  hex
> 1.  color
> 1.  RGB

**C.** color

## Problem 5

> What action is included with Android's Event Listeners?
>
> 1.  a toast message
> 1.  an intent
> 1.  a single callback method (ex. `onItemClick`)
> 1.  an Event registration

**C.** a single callback method (ex. `onItemClick`)

## Problem 6

> Which XML file or files can you change your app name or 'label' in?
>
> 1.  `strings.xml` or `values.xml`
> 1.  `strings.xml` or `AndroidManifest.xml`
> 1.  just the `AndroidManifest.xml` file
> 1.  just the `strings.xml` file

**C.** just the `AndroidManifest.xml` file

## Problem 7

> What is the opening and closing root tag called in your `strings.xml` file?
>
> 1. `<strings>`
> 1. `<resources>`
> 1. `<string>`
> 1. `<values>`

**B.** `<resources>`

## Problem 8

> Two constants (*not* deprecated) that can be used as compatible values of
  `android:layout_width` and `android:layout_height` are ______ and ______.
>
> 1.  `match_parent`, `fill_parent`
> 1.  `match_parent`, `wrap_content`
> 1.  `wrap_content`, `fill_parent`
> 1.  `wrap_content`, `fill_content`

**B.** `match_parent`, `wrap_content`

## Problem 9

> The ______ activity gets cleared first to free up necessary memory in the
  activity stack.
>
> 1.  Active
> 1.  Previous Activity
> 1.  Oldest
> 1.  Top most

**C.** Oldest

## Problem 10

> To ensure just numeric floating point input, what would be the appropriate
  `inputType` attribute below for an EditText element that will allow for
  decimal input?
>
> 1.  `android:inputType="numberDecimal"`
> 1.  `android:inputType="Decimal"`
> 1.  `android:inputType="DecimalNumber"`
> 1.  `android:inputType="Number"`

**A.** `android:inputType="numberDecimal"`

## Problem 11

> The partial XML snippet below is well-formed.
>
> ```xml
> <Rules>
>   <ID attr="classA">id1</ID>
>   <Prefix>documents/</Prefix>
>   <Status>Enabled</Status>
>   <Transition>
>     <Days>30</Days>
>     <StorageClass>ICE</StorageClass>
>   </Transition>
> </Rules>
> ```
>
> 1.  True
> 1.  False

**A.** True

## Problem 12

> What is the proper line of code to assign a button object named `btnClick`
  that refers to an button element's ID called button?
>
> 1.  `Button btnClick = R.id.button;`
> 1.  `Button btnClick = findViewById(button);`
> 1.  `Button btnClick = findViewById(R.id.button);`
> 1.  `Button btnClick = findById(R.id.button);`

**C.** `Button btnClick = findViewById(R.id.button);`

## Problem 13

> In an ImageView element, the property that refers to the image file to display
  is `android:`______.
>
> 1.  `id`
> 1.  `src`
> 1.  `img`
> 1.  `ImgFile`

**B.** `src`

## Problem 14

> What is the only valid XML comment?
>
> 1.  `// comment`
> 1.  `/* comment */`
> 1.  `# comment`
> 1.  `<!-- comment -->`

**D.** `<!-- comment -->`

## Problem 15

> Which import statement is always used when creating an Empty Views Activity?
>
> 1.  `android.view.View`
> 1.  `android.os.Bundle`
> 1.  `android.support.design.widget.Snackbar`
> 1.  `androidx.AppCompatActivity`

**B.** `android.os.Bundle`

## Problem 16

> A color has been defined in `colors.xml` using following tag settings:
>
> ```xml
> <color name="myColor">#33fd42</color>
> ```
>
> How would you refer to the defined color value in one of your layouts?
>
> 1.  `@color/33fd42`
> 1.  `@color/myColor`
> 1.  `color/myColor`
> 1.  `color/33fd42`

**B.** `@color/myColor`

## Problem 17

> More than one `AndroidManifest.xml` can be included in an APK file.
>
> 1.  True
> 1.  False

**B.** False

## Problem 18

> ______ is not a valid measurement unit for text display.
>
> 1.  dp
> 1.  dpi
> 1.  dip
> 1.  sp

**B.** dpi

## Problem 19

> Which of the following is not considered an activity state?
>
> 1.  Previous
> 1.  Active
> 1.  Stopped
> 1.  Inactive

**A.** Previous

## Problem 20

> Include the android attribute/value for assigning the background of your
  layout to the hext value: `#784f57`.
>
> 1.  `layout:background="#784f57"`
> 1.  `android:background="784f57"`
> 1.  `android:backgroundColor="784f57"`
> 1.  `android:background="#784f57"`

**D.** `android:background="#784f57"`

## Problem 21

> Given the activity actions/tasks shown below, describe what is happening in
  the back stack thru the described timeline.
>
> ![Activity tasks.](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/exam1_1.png)

In the illustration above, the activity lifecycle began when the user launched
the application, which occupied the stack as an active component. The first
activity then spawned the second activity, which in turn launched another one
consecutively. By the third activity, the user decided to press the back button
(or in recent times, swipe from the edge of the screen), destroying the current
activity and pushing the second activity back to the front screen.

## Problem 22

> Describe some ways intents are used for. How would you use an intent to open a
  web page such as `http://www.google.com/`?

Intent is used to describe an action performed by other activities and broadcast
receiver, that is, the native Android implementation of the publish-subscribe
communication pattern. To define a launch specification, an intent typically
contains a target class and extra arguments, but may also include additional
attributes like data and type. For example, opening a URL is done by declaring
view as the primary action and the hyperlink itself as data:

```java
Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/"));
startActivity(intent);
```

## Problem 23

> Why would it be a good idea to include some output detail in your LogCat view
  area when your app is running? Include at least 3 solid idea / uses for doing
  so.

1.  **Debugging:** LogCat constantly prints entries in the console, it is easier
    to notice an error while it is happening instead of searching a stack of
    information.
1.  **Limitation:** There is a finite number of logs that can be kept, an
    important piece of information may be missing when LogCa overwrites them.
1.  **Filtering:** Logs can be filtered based on application identifier and log
    type (debug, verbose, etc.), allowing us to isolate only the information we
    are interested in.

However, I acknowledge that this approach may not be for everyone because it
requires a large area of screen display to be useful. It is difficult to track
logs in a small window with a limited viewable area. This problem is exacerbated
when other windows are active and deemed crucial such as Project Explorer,
Terminal and Android Virtual Device Manager. For this reason, I believe LogCat
window should not be present in a single-screen laptop device and should only be
reserved in desktop environments.

## Problem 24

> Demonstrate with some code lines how would you receive a value from a String
  variable named `userName` in MainActivity sent from LoginActivity.

```java
public class MainActivity {
  public static final String EXTRA_USERNAME = "com.example.MainActivity.USERNAME";

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // ...

    Bundle extras = getIntent().getExtras();
    if (extras == null) {
      return;
    }
    String userName = extras.getString(EXTRA_USERNAME);
  }
}
```

## Problem 25

> Demonstrate with some code lines how would you transfer a value from a String
  variable named `userName` from LoginActivity to MainActivity.

```java
public class LoginActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // ...

    String userName = "John Doe";
    Intent intent = new Intent(this, MainActivity.class);
    intent.putExtra(MainActivity.EXTRA_USERNAME, userName);
    startActivity(intent);
  }
}
```
