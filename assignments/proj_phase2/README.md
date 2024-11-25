# [Project Phase 2](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/proj_phase2.docx): PokémonRoster Presentation

- Fetch content from [PokéAPI](https://pokeapi.co/) and paginate results in an
  endless scrolling list.
- Free template from [SketchBubble](https://www.sketchbubble.com/en/presentation-pokemon-theme.html).

## Features

- Authentication and storage
  - [ ] Firebase authentication and cloud storage.
  - [x] Guest session with local storage.
- Content retrieval
  - [x] Pokémon status, evolution and move details from *PokéAPI.*
  - [ ] Additional lore such as Pokémon height, weight and natural habitat from
        other APIs.
- Team building
  - [x] Configure team members and their movesets.
  - [x] Show statistical analysis of team composition.
  - [ ] Simulate move damage points given the opponent's Pokémon input.

## Script

> The ordered list corresponds to the slide animation order while the unordered
  list items denote topic for discussion.

![Cover](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide1.png)

Welcome, friends, lecturers and Pokémon trainers! I'm excited to share my final
project, which I call PokémonRoster, and welcome any feedback you may have.

![Table of contents](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide2.png)

Here is an overview of what we will discuss for the next few minutes. There are
approximately 2-3 slides for each category, so hopefully, it shouldn't take much
of your time.

We have:

1.  Background and motivation
1.  Architectural diagram
1.  Animated demonstration
1.  Deliverables and discussion

![Introduction: Background](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide3.png)

1.  This topic resonates with me because the first generation of Pokémon is my
    first gaming device.
    - It was *Nintendo GameBoy* with a Pokémon Red cartridge.
    - I also think it could be a good practice to make a game-related app since
      the UI needs to be creative. Finally, PokeAPI is free without a
      subscription and rate limit, so I don't have to manage session tokens or
      API keys.
1.  The idea is not new.
    - Websites and apps about Pokémon are abundant. Bulbapedia, shown here, is a
      personal favorite. However, most of them are static and don't have a
      team-building feature.
    - When I play Pokémon games, I always keep track of technical and hidden
      machines that my Pokémon can learn.

![Introduction: Features](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide4.png)

Here are the main features the app is targeting.

1.  Using data from PokeAPI, users can browse details about monsters and moves.
1.  These data are then passed to a local database when users enroll a Pokémon
    to the roster.
1.  With a roster, users can predict how many steps are needed to take down a
    selected enemy with the current skillset in a battle simulation.

![Architecture: Component](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide5.png)

This application conforms to the Material3 design language and Jetpack Compose
principles. At the data handling section, we have Retrofit to make REST API
calls and the Room persistence layer to generate SQLite Data Access Object.
These objects are preferred because hardcoded SQLite strings are unintuitive
and may have typos.

Next, we have standard views and layouts in the UI section. Material and
AppCompat libraries provide a set of reusable styles. A notable entry in this
group is the Navigation framework for defining component destinations in an XML
file.

In the final section, Parceler is a helper tool for converting existing Plain
Old Java objects into parcelable objects. Parcelable is more efficient than Java
Serializable object when passing values between activities. Robolectric creates
mocked objects in the unit tests to reduce testing and building time.

![Architecture: Navigation](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide6.png)

1.  The first chart denotes the application workflow. The main activity consists
    of three fragments with access to the view model integrated into the Android
    lifecycle. Each fragment has a different activity destination, but the
    bottom sheet dialog showing a skill move is accessible from all of them. The
    lifecycle observer component is useful for starting another activity without
    a result code and lets us handle the result outside the caller activity.
1.  The diagram below depicts how a client device processes data from the
    PokeAPI server. The application retrieves information in a certain size and
    location using GET queries. When a page is successfully loaded, the result
    is stored in the view model before the pager continues to the next page,
    creating an endless scrolling behavior. The pager resets itself in the event
    of a network error or manual refresh by pulling down the list.

![Demonstration: Main activity](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide7.png)

You will see horizontal screenshots of the application in a light theme state in
the left area. The rest are screen recordings in dark mode. I believe an
application should not force a specific configuration like portrait mode and let
users decide which settings work best for them.

The monster list loads 20 items per page, while the move list only has 10.
Monsters require more items since the card container is stretchable to a mini
size by selecting a menu item. Many of the item layouts here are reused later in
other activities.

![Demonstration: Monster activity](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide8.png)

Tapping into a Pokémon card will redirect users into a monster activity. The
monster base status is compared against the corresponding max status of
non-legendary Pokémon to produce a percentages below the toolbar. A Pokémon may
have separate sprites for female and shiny variants in the horizontal gallery
list.

![Demonstration: Move activity](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide9.png)

A bottom sheet dialog presents the details of skill moves such as power,
description and list of trainable Pokémon. This dialog needs to be attached to a
fragment manager to retain itself in a configuration change. The bottom sheet
will take full screen if the content is scrolled but will stretch itself down in
a landscape.

![Summary: Challenges](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide10.png)

1.  I faced several challenges during development. From a technical standpoint,
    there are limited resources online because Jetpack Compose is relatively
    new.
1.  Google declared Kotlin as the official language of Android in 2019 and has
    since aggressively rewritten AndroidX and Material libraries in Kotlin to a
    point where it's unavoidable. For example, the Paging API performs
    background calls in Kotlin coroutines instead of Java `Thread` or Android
    `AsyncTask`.
1.  The application is also not very efficient and vulnerable to network failure
    due to the large number of requests it is requesting from the server. For
    instance, the paged REST API call result only returns the name of Pokémon,
    requiring the app to send a subsequent call to collect the entire
    information. Unfortunately, this problem is unfixable as I have no control
    over the server and its API blueprint.
1.  Lastly, the app cannot determine which generation the information belong to.
    It is not a technical challenge, but rather, a design decision I have not
    made.

![Summary: Roadmap](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide11.png)

1.  The PokémonRoster project is established during the submission of phase 1,
    using the wireframe as UI guidelines.
1.  Initially, the development started with bridging a connection between client
    and server necessary for monster and move features.
1.  With a list of monsters available, I then focused on storing them in a local
    database for the roster feature.
1.  In the most recent progress, the majority of the codebase is modularized to
    maintain a low footprint.
1.  Then, we are here at phase 2.
1.  I acknowledge that some parts of the application are still missing, namely
    the battle simulation feature.
1.  I intend to finish the requirements before final submission, along with
    documentation related to the development.

![Thank you](https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase2/slide12.png)

Thanks for joining me today! I hope you found it interesting. I have learned a
lot from the development progress. I conclude this presentation by taking any
questions about the app. If there isn't any, I wish everyone a warm
Thanksgiving.

> Disclaimer: I do not own this PowerPoint template, the creator is attributed
  at the top of the page.
