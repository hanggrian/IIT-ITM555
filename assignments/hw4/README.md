# [Homework 4](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/hw4.docx): MusicPlaylist

- `RecyclerView` with multiple item view types to display a list of songs and
  their categories, which are countries of origin.
- Store login session in `SharedPreferences`, ensuring that the user does not
  need to log in again after closing the app.
- Utilize **Retrofit** to fetch data from a RESTful API, the background call is
  made using concurrent `Executors` instead of [the deprecated `AsyncTask`](https://developer.android.com/reference/android/os/AsyncTask).

## Screenshots

### Login screen

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw4/screenshot1_1.png">
<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw4/screenshot1_2.png"><br><small>Screenshot 1.1 & 1.2 &mdash; Entering credentials & login progress</small>

> The username needs to be at least **4 non-whitespace characters** and the
  password is always **itmd4555**.

In this application, the user is required to login using a username and password
combination. However, the login credentials are not validated against a server
and instead are hardcoded in the application.

### Playlist overview

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw4/screenshot2_1.png">
<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw4/screenshot2_2.png"><br><small>Screenshot 2.1 & 2.2 &mdash; Dark & light mode</small>

The music data is retrieved from [Papademas API](http://www.papademas.net:81/cd_catalog.json),
the raw input is then filled into [Guava's Multimap](https://guava.dev/releases/23.0/api/docs/com/google/common/collect/Multimap.html)
to group the songs by their origin country. Additionally, the card background
color is a randomized [Material2 color](https://m2.material.io/design/color/the-color-system.html)
with a custom implementation that avoids the same color in a set.

### Music detail

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw4/screenshot3_1.png"><br><small>Screenshot 3.1 &mdash; Half sheet in portrait</small>

When user taps a song from the playlist overview, a bottom sheet will slide up
to show the song's details. The card background color is also passed to the
dialog fragment to personalize the music detail.

<img width="640" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/hw4/screenshot3_2.png"><br><small>Screenshot 3.1 &mdash; Full sheet in landscape</small>

The cool feature of bottom dialog is that it can be expanded to full screen
by dragging it up. Usually in this state, the default rounded corner of the root
layout is automatically removed for a more immersive experience. In this
example, the floating action button is disabled because the `music#isSold`.
