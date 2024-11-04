# [Project Phase 1](https://github.com/hanggrian/IIT-ITM555/blob/assets/assignments/proj_phase1.docx): PokémonRoster Wireframe

- Fetch content from free REST [PokéAPI](https://pokeapi.co/) while saving
  roster state to Firebase.
- Diagram outlines are created with the open-source *draw.io* tool.
- Wireframe design is in proprietary *Adobe XD* format using the official
  [Material2 template](https://m2.material.io/resources).

## Background

Initially, my idea for the project was to self-host the [MySQL Sakila database](https://dev.mysql.com/doc/sakila/en/)
and serve it as a REST API. The Sakila database schema covers a wide range of
topics such as movie wikis, rental stores and payment records. However, I
recently discovered that [GitHub Pages](https://pages.github.com/) does not
support dynamic content, making it impossible to host server-side applications.
Theoretically, there are still services available like [Render](https://render.com/)
that supports Java frameworks, but I fear that the unfamiliarity with the
platform would hinder my progress considering the time constraints.

Therefore, I decided to pivot to a narrower scope by using the PokéAPI REST
service that is completely free and requires no running server. This application
allows users to build a Pokémon team by searching for Pokémon and their moves.
Because the Pokémon franchise spans multiple generations and has a vast number
of functionalities, the application is only limited to the first generation
(Red, Blue and Yellow) with core features.

## Features

- Authentication and storage
  - [ ] Firebase authentication and cloud storage.
  - [ ] Guest session with local storage.
- Content retrieval
  - [ ] Pokémon status, evolution and move details from *PokéAPI.*
  - [ ] Additional lore such as Pokémon height, weight and natural habitat from
        other APIs.
- Team building
  - [ ] Configure team members and their movesets.
  - [ ] Show statistical analysis of team composition.
  - [ ] Simulate move damage points given the opponent's Pokémon input.

> Note that currently planned features are rather ambitious and are subject to
  change based on the project's progress.

## Diagram

<img width="640" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase1/diagram1.svg"><br><small>Diagram 1 &mdash; Application workflow</small>

The application lets users enter with a guest account as well as Google account
authentication with Firebase services. During guest sessions, the roster state
is saved to local storage and restored upon re-entry, but does not persist
upon application uninstallation.

## Wireframe

<img width="640" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase1/wireframe1.png"><br><small>Wireframe 1 &mdash; Application tabs</small>

The main screen is a tabbed interface with adaptive toolbar menu items. For
example, the search button will expand to show the search bar when clicked.
Other menu items include application information and logout button.

<img width="640" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase1/wireframe2.png"><br><small>Wireframe 2 &mdash; Search Pokémon</small>

Pokémon entries are displayed in a large card container, tapping on a card will
redirect to the detailed view of status, evolutions and possible moves. Since
the moves are clickable as well to show Pokémon that can learn the move, this
create a recursive workflow detailed in the diagram above.

<img width="640" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase1/wireframe3.png"><br><small>Wireframe 3 &mdash; Search move</small>

Moves use a smaller card layout that is reused in the Pokémon detailed activity.

<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase1/wireframe4_1.png">
<img width="320" src="https://github.com/hanggrian/IIT-ITM555/raw/assets/assignments/proj_phase1/wireframe4_2.png"><br><small>Wireframe 4.1 & 4.2 &mdash; Build team</small>

There is a floating action button for adding Pokémon to the team, the button
disappears when the team reaches the maximum size of 6 members. After assigning
a member, users may configure the moveset in active Pokémon screen, notably has
a toolbar in the bottom for quick access to the team roster. Similarly, the
moves are limited to 4 slots per Pokémon.
