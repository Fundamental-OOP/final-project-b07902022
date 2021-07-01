# Final-Project
## How to play our 2D game

First, compile and run the program by typing

```bash=
make run
```

Or typing the following commands:

```bash=
javac -cp . -sourcepath src -d ./out/ ./src/*.java
java -cp out/ dontTouchTheWhiteTile/DontTouchTheWhiteTile
```

Once the program is running, you will see the starting window. You can adjust your settings here. After that, you can click the play button to play. If you don't type anything the default will be 

```
username: player
speed: slow
Columns: 3
music: Canon
```

For each column from left to right, the keyboard setting is like the following section.

```
Column = 3: F [space] J
Column = 4: D F J K
```

Once you enter the game, you'll see the grids and a red line. This red line represent the vertical position of where you keypress. The target for you is to keypress all the black tiles as many as you can. For the short tile, you just need to keypress it once. For the long tile, you should keypress it until it reaches the end. If you keypress the white tile, your combo will be 0 and you score will be deducted.

The duration of the game is one minute. When the game is finished, there will be a scoreboard showing the top 5 score of the same setting. If you want to play again you can press key `R` to restart the game.