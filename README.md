<img align="left" src="src/res/roborally-icon.png" height="120px"/>

[![License][s1]][li]

[s1]: https://img.shields.io/badge/licence-GPL%203.0-blue.svg
[li]: https://raw.githubusercontent.com/matt77hias/RoboRally/master/LICENSE.txt

# RoboRally
Course Object Oriented Programming: RoboRally (*Part IV: A New Hope*)

**Team**:
* [Matthias Moulin](https://github.com/matt77hias) (Computer Science)
* [Ruben Pieters](https://github.com/rubenpieters) (Computer Science)

**Academic Year**: 2011-2012 (2nd semester - 2nd Bachelor of Science in Engineering: Computer Science)

**Score**: Maximum Score

## About
RoboRally is a board game where each player plays the role of a robot. The goal of the game is to move the robot from its starting position to the goal position as fast as possible. The first player to reach the goal position wins the game. This project creates a game loosely based on this board game supporting *robots*, *boards*, *walls*, *batteries*, *repair kits*, *surprise boxes*, *laser shooting*, a.o. Furthermore, robots can execute small programs (written in a customly defined language).

This project correctly uses *total*, *defensive* and *nominally* programming styles, *Value classes* to support different unit systems and the *Liskov Substitution Principle* for building hierarchies. Furthermore, all code is documented both *informally* as *formally* using *pre- and postconditions* and *invariants*. All code is nearly fully covered by more than thousand unit tests.

## Design
### Terminatable Hierarchy
<p align="center"><img src="res/RoboRally_Terminatable Hierarchy.png"></p>

### BoardModel Hierarchy
<p align="center"><img src="res/RoboRally_BoardModel Hierarchy.png"></p>

### InventoryModel Hierarchy
<p align="center"><img src="res/RoboRally_InventoryModel Hierarchy Advanced.png"></p>

### Collector Hierarchy
<p align="center"><img src="res/RoboRally_Collector Hierarchy.png"></p>

### EnergyModel Hierarchy
<p align="center"><img src="res/RoboRally_EnergyModel Hierarchy Advanced.png"></p>

### Board Associations
<p align="center"><img src="res/RoboRally_Board Associations.png"></p>

### Inventory Associations
<p align="center"><img src="res/RoboRally_Inventory Associations.png"></p>

### Value Classes
<p align="center"><img src="res/RoboRally_Comparables.png"></p>

### Program Hierarchy
<p align="center"><img src="res/RoboRally_Program Hierarchy.png"></p>

### LanguageElement Hierarchy
<p align="center"><img src="res/RoboRally_LanguageElement Hierarchy Advanced.png"></p>

### Condition Hierarchy
<p align="center"><img src="res/RoboRally_Condition Hierarchy Advanced.png"></p>

### Command Hierarchy
<p align="center"><img src="res/RoboRally_Command Hierarchy Advanced.png"></p>

### ConditionTester Hierarchy
<p align="center"><img src="res/RoboRally_ConditionTester Hierarchy Advanced.png"></p>

## Program Associations
<p align="center"><img src="res/RoboRally_Program Associations 2.png"></p>

### Connection to UI Layer
<p align="center"><img src="res/RoboRally_UI Layer.png"></p>
