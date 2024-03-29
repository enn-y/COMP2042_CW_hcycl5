# Brick Breaker

Brick Breaker is a classic retro game implemented in Java using JavaFX for the graphical user interface. The aim of the game is to move the paddle left and right, hit the ball, and destroy all the blocks present. Users can control the paddle using arrow keys and move the paddle to hit the ball. The game is designed to provide an engaging and entertaining experience reminiscent of the arcade era. This projects uses Java 19 in IntelliJ, JavaFX for the GUI components, and Maven to manage dependencies.

As part of the project, the code has incorporated refactoring, code maintenance, design patterns, new features, git, and documentation practices to improve the code quality and overall maintainability.

### Table of Contents 
- **[Project Setup](#project-setup)**<br>
- **[Utilization of Git](#utilization-of-git)**<br>
- **[Refactoring](#refactoring)**<br>
- **[New Additions](#new-additions)**<br>
- **[Documentation](#documentation)**<br>

## Project Setup 

This project uses Java Version 19 on IntelliJ, on macOS
```
en@Ens-MacBook-Air ~ % java -version
java version "19" 2022-09-20
Java(TM) SE Runtime Environment (build 19+36-2238)
Java HotSpot(TM) 64-Bit Server VM (build 19+36-2238, mixed mode, sharing)
```

This project uses JavaFX on intellij, it is added as a library under project structure, on macOS
```
/Users/en/Downloads/javafx-sdk-21.0.01/lib
```

This project has also added JavaFX and JUnit dependencies to Maven, pom.xml file

```
<dependencies>
	<!-- https://mvnrepository.com/artifact/org.openjfx/javafx-controls -->
	<dependency>
		<groupId>org.openjfx</groupId>
		<artifactId>javafx-controls</artifactId>
		<version>22-ea+16</version>
	</dependency>
	<dependency>
		<groupId>junit</groupId>
		<artifactId>junit</artifactId>
		<version>RELEASE</version>
		<scope>test</scope>
	</dependency>
	<dependency>
		<groupId>junit</groupId>
		<artifactId>junit</artifactId>
		<version>4.13.2</version>
		<scope>compile</scope>
	</dependency>
</dependencies>
```

To run the game:
1) Select your IDE of choice, recommended IDE: Intellij, NetBeans, or Eclipse. These IDE provide support for Maven, JavaFX, and JUnit dependencies. 
2) Add the JavaFX Library either by manually adding through the module settings or adding through VM arguments
3) If you choose to add JavaFX manually, please download JavaFX SDK and then proceed to configure the project's library settings through module settings
4) However, if you choose to use Maven, dependencies are already present and so run the build on Maven as it will compile the code and ensure that all necessary dependencies are included so the game runs smoothly
5) Run game, have fun!

## Utilization of Git
- Frequent commit history from the start to the end of the project
- Meaningful commit messages
	- Bug fixes
	- Refactoring
	- Addition of new features
	- Commenting
	- JUnit tests
	- Using design patterns
	- Implementing basic maintenance
- Use of branch and merge
- Relevant .gitignore

## Refactoring
- Created new classes and organized classes into relevant packages
- Basic code maintenance
	- Renamed variables to improve readability
	- Improved encapsulation by organizing code into classes
	- Improved encapsulation within classes by creating new methods to prevent long and unnecessary code
	- Deleted unused imports and unnecessary lines of code
	- Split up main class into smaller and more specific classes
	- Added new interfaces
	- Error handling
	- Fixed bugs
	- Included comments to improve understanding
	- Used setter and getter methods
	- Code consistency throughout project, naming conventions and formatting the code for better readability
	- Removed code smells
	- Meaningful git commit messages
	- Relevant JUnit tests
	- Fixed thread issues and improved thread usage throughout the code
- Ensured single responsibility among classes by splitting up code
- Arranged overall code into MVC pattern
	- Model
		- Blocks (Directory), contains the blocks and block related methods, factory design pattern used here
		- Ball (Directory), contains the ball and ball related methods, factory design pattern used here
		- Player (Directory), contains the paddle and player related methods, singleton design pattern used here
		- Interfaces (Directory), contains interfaces, organizes the code and provides blueprint to classes
		- Serializable (Directory), contains block serializable
		- GameEngine, contains game logic
		- ScoreManager, contains score methods
		- LevelManager, contains level methods
	- View
		- GameScreen, contains the methods to load and run game screen
		- State, contains methods to control save, read, load, checkgame methods
	- Controller
		- ButtonController, contains the logic for the buttons
		- KeyboardController, contains the logic for the keyboard keys used in the game, uses eventhandler
- Applied design patterns to relevant classes
	- Block Factory - Factory Design Pattern
	- Ball Factory - Factory Design Pattern
	- Added Singleton to Player Class - Singleton Design Pattern
- Added meaningful JUnit tests, sufficient number of JUnit tests to test methods and classes of the game
	- ButtonControllerTest
	- KeyboardControllerTest
	- BallFactoryTest
	- BallModelTest
	- BlockFactoryTest
	- BlockModelTest
	- BonusTest
	- PlayerInitializerTest
	- PlayerModelTest
	- LevelManagerTest
	- ScoreManagerTest
	- GameScreenTest
	- StateTest
- Converted project to a Maven project to handle JUnit & JavaFX dependencies, added JUnit & JavaFX dependencies
  - Ensured that module-info.java is present in the folder, displays the metadata about the module
  - Added JUnit dependencies from mvnrepository.com
  - Added JavaFX dependencies from mvnrepository.com
- Bug Fixes
	- Adjusted sensitivity of blocks, added additional padding to each block
	- Added padding to the paddle and the walls to prevent ball from entering the objects
	- Fixed score label, modified the thread to remove the label after it has been shown
	- Fixed a heart label bug when hearts==0, created a new method to check for gameover
	- Fixed collision detection on blocks, modified the thread to support multiple blocks
	- Modified thread for method "onPhysicsUpdate" to prevent error
	- Fixed save game button, game saves
	- Fixed load game button, game now loads from previously saved status
	- Modified threads on the whole project to prevent errors
	- Modified the game levels to 10 instead of 18, as 18 will mean the blocks will overflow the game screen

## New Additions
- Added 5 new blocks
	- Bomb Block: The ball will disappear for 1 seconds, bomb image on the block
      - Simulates an explosion upon hitting the bomb block
	- Speed Block: The ball will increase speed by 2x, lightning image on the block
      - Simulates an increase of ball speed upon hitting the speed block
	- Slime Block: The ball will decrease by 70%, slime image on block
      - Simulates an decrease of ball speed upon hitting the slime block
	- Teleport Block: The ball x and y coordinate will randomly change within the game window, portal image on the block
      - Simulates a random change in ball position
	- Question Block: There is a 50% chance of getting a heart or losing a heart, question mark image on the block. However if the player is at 1 heart, the question block will always give a heart
      - Simulates the heart block but with a twist
- Different effects for each newly added block, every block is unique in its effects
- Added a pause game function
	- Spacebar to pause game, corresponding message also displays
	- Game pauses when spacebar is pressed
- Added new buttons in the main menu
	- Exit Game, game will end.
	- Instructions, instructions will display
	- User friendly UI
- Exit Game Button
	- It will present an alert and double confirm with the user whether to exit or not
	- User friendly UI
- Instruction Button
	- It presents an alert to the user outlining the rules and instructions to the game
	- User friendly UI
- Added new keyboard controls to the keyboard
	- Pause Game, Spacebar
	- Exit Game, Q key, corresponding message displays, game closes
	- Restart Game, R key, corresponding message displays, game restarts
	- Save Game, S key, corresponding message displays, game saves
- Added a Game Over page
	- Game Over page displays the score, level, and number of hearts
	- Also present are 2 buttons, restart and exit. Restart will restart game and exit will end game
- Added a Game Win page
	- Game Win page displays the score, level, and number of hearts
	- Also present is the exit button to exit game
- Added ToolTips to all buttons for enhanced user experience, upon hovering, text will be shown

## Documentation
- Complete Readme.md
	- All changes highlighted
		- Use of git
		- Refactoring
		- Additions
		- Documentation
- Informative, concise, and complete Javadocs
- High-level class diagram: 
	![Class Diagram](src/main/resources/classdiagram.png)