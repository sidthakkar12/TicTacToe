[![Home-Screen.png](https://i.postimg.cc/LXSJ0yvX/Home-Screen.png)](https://postimg.cc/Lqy4J3cK)  [![Game-Board.png](https://i.postimg.cc/76Lk60Z9/Game-Board.png)](https://postimg.cc/3yz6fyp0)  [![Result.png](https://i.postimg.cc/VvQPFTFG/Result.png)](https://postimg.cc/cgmzxF2w)


### Steps to run in Android Studio:

### Below are 2 ways to open project locally or from Github:

**A) Open project locally.**

 **A1) Open project from Welcome screen of Android Studio:**

  A1-1) Open Android Studio.

  A1-2) On Welcome screen, Click on File > 'Open an Existing Project', and go the the folder where project is located and click on it.

  A1-3) Android Studio will download necessary gradle files required to run the app.

  A1-4) While gradle is syncing, a dialog will popup to change path of SDK. Just click on 'OK' and the path will be changed automatically and project will sync with your localised Android SDK.

  A1-5) After project completes gradle sync and indexing and importing, click on Run > Run 'app'.

**A2) Open project from inside Android Studio where other project is already loaded.**

  A2-1) Open Android Studio.

  A2-2) Click on File > Open, and go the the folder where project is located and click on it.

  A2-3) Android Studio will download necessary gradle files required to run the app.

  A2-4) While gradle is syncing, a dialog will popup to change path of SDK. Just click on 'OK' and the path will be changed automatically and project will sync with your localised Android SDK.

  A2-5) After project completes gradle sync and indexing and importing, click on Run > Run 'app'.

**B) Open project from Github:**

**B1) Open project from Welcome screen of Android Studio:**

  B1-1) Open Android Studio.

  B1-2) On Welcome screen, click on 'Get from Version Control'. Select 'Git' from dropdown, Enter Git URL (https://github.com/sidthakkar12/TicTacToe.git), Select directory path you want to store your project to.

  B1-3) Android Studio will download necessary gradle files required to run the app.

  B1-4) While gradle is syncing, a dialog will popup to change path of SDK. Just click on 'OK' and the path will be changed automatically and project will sync with your localised Android SDK.

  B1-5) After project completes gradle sync and indexing and importing, click on Run > Run 'app'.

**B2) Open project from inside Android Studio where other project is already loaded.**

  B2-1) Open Android Studio.

  B2-2) Click on File > New > Project from Version Control. Select 'Git' from dropdown, Enter Git URL (https://github.com/sidthakkar12/TicTacToe.git), Select directory path you want to store your project to.

  B2-3) Android Studio will download necessary gradle files required to run the app.

  B2-4) While gradle is syncing, a dialog will popup to change path of SDK. Just click on 'OK' and the path will be changed automatically and project will sync with your localised Android SDK.

  B2-5) After project completes gradle sync and indexing and importing, click on Run > Run 'app'.

Project will run and app will be installed in device.

### Understanding/Steps to play the game:

### NOTE: Node Server must be running to play the game.

1) Start the node server.
2) Tic Tac Toe can be played only when both the players tap on 'Play' button.
3) As the game starts, players will be redirected to the Game screen.
4) Game screen contains a board to play the game.
5) Turn-by-turn each player gets a chance, who's turn it is can be seen on top of screen.
6) To play a turn, player needs to tap on any grid cell.
7) The board will be updated for both players after each turn.
8) Player can quit the game in-between.
9) To quit the game, players needs to click on icon at top-right of the screen or back button from the device. A confirmation will popup before player quits the game.
10) The game has 3 possible results: Win/Lose/Draw.
11) As and when the game is completed, players will get a popup appropriate to the Winning player and Losing player.
12) App will show popup to both the users even if the game is drawn.
13) Players can tap on Play Again / Try Again to play the game again with the opponent player.

### Flea:

1) Application of second player is not in running state (not even in background), socket is disconnected from application, and first player taps on 'Play' button, still first player is getting second player as available to start the game (in response, in 'players' object, both 'X' and 'O' players are available).

### Add Ons:

1) In solution for Bug 1, we can create a socket that will be emitted from app itself, and will notify first player that no other player is available to play the game. 

2) We can ask for second player's confirmation to start the game and keep the first player on waiting mode, or ask to tap again on 'Play' to 'Retry' or 'Find other opponent'.

3) Need to make player aware that other player has left or quit the game.

4) Response objects' and fields' can be given appropriate name following naming conventions, and response can be updated as a matter of fact developer not requiring to give any static value, on the contrary of no local conditions shall be required after that.

5) Add timeout option for each player.

6) More animations and eye-catching app theme can be added to the app.  
