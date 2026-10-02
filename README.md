# Rapid Recall 

Rapid Recall is an Android memory game built using Kotlin and Jetpack Compose. The application challenges players to memorize and accurately input randomly generated number sequences of varying lengths.

Copyright (C) 2026 Ihsan Muhammad Anjakkulam

This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

## Architecture
This project strictly implements the Model-View-Controller (MVC) design pattern:
* **Model:** Handles sequence generation, attempt evaluation, and history tracking.
* **View:** Jetpack Compose UI screens (Start, Level Selector, Game, Result) that observe the Controller.
* **Controller:** Manages the UI state, navigation logic, and acts as the bridge between the View and Model.

## Features
* Adjustable difficulty (sequence length scaling).
* Persistent attempt history and statistical dashboard (Total Games, Wins, Accuracy).
* Custom styled UI using Jetpack Compose components.

## Acknowledgments and Tools
* **AI Assistance:** UI styling, layout refinement, and troubleshooting assistance were provided by Google's Gemini AI.
* **UML Generation:** The architectural UML Class Diagram was mapped and generated using [PlantText](https://www.planttext.com) (PlantUML).