Project 2 Description
====================

In this assignment, you’ll design and implement necessary classes for the simulation of the classical
**`Battleships`** game. **`Battleships`** is a two player game which is played on a 10x10 grid board. Considering the object oriented programming approach, please go through the descriptions and concepts below.

***Ships***

There are 4 different types of ships in the game. These ships are submarine (S), destroyer (D), cruiser (C) and battleship (B). They can be placed on the grid-based game board horizontally or vertically. The only difference between these ships is their sizes. They cover 1, 2, 3 and 4 squares on the grid board respectively. Any square of a ship can take a hit. If all squares of a ship are hit, then the ship is sank.

***Players***

Each player in the game has 10 ships containing 4 submarines, 3 destroyers, 2 cruisers and a battleship. Players place their ships on their own 10x10 grid game board, and attack to each other in turn. To attack the opponent, player says a game board coordinate such as ‘H7’ which states the 7th column of the 8 th (H) row. If there is a ship piece in that coordinate, the ship is hit. If all pieces of the ship is hit, then it is sank. The opponent should warn the other player about the success or fail of the attack. Player whose all ships are sank loses the game.

***Game Implementation***

To model the players, **`Side`** to the descriptions below and class is designed as follows. You should implement its methods with respect usages in the main function.

![alt tag](http://oi61.tinypic.com/2aihpic.jpg)

**`list`** is the array of pointers to the **`Ship`** classes. Although, the elements of **`list`** are pointers to **`Ship`** class, they should point different types of ships. **`attack_history`** is for storing previous attacks of the player, and **`counter`** is the index of the last element of this array.

Constructor of this class takes a file name as parameter (char*). Ships are placed on the board according to this input file. An example organization of input file and game board is given as follows.

![alt tag](http://oi57.tinypic.com/vl6co.jpg)

As stated above, each **`Ship`** element in the array of **`list`** should be defined one of the following four types; submarine, destroyer, cruiser and battleship. In fact, there isn’t any **`Ship`** class in use (see abstract classes in your lecture notes).

**`print`** function prints the game board to the screen as in the figure above. **`defeated`** function checks the player, whether he loses the game or not. **`damage`** function takes a char and an integer as its parameters which represent the board coordinate attacked by the opponent. **`damage`** function checks the given coordinate if there’s a ship at that location and performs necessary operations, more specifically it controls the coordinates and if there’s a ship at that location prints the related message to the screen. Additionally, only for battleships, **`damage`** function prints number of damaged parts (see sample output). Finally, **`attack`** function generates a board coordinate randomly, e.g., H6 (a two-length string with a letter and a number). Any coordinate that is attacked before should not be generated again. That’s why, you should use the variable **`attack_history`** to store previous attacks. Usages of these functions can be seen clearly from the main function below.

According to the given code pieces, you should implement the methods of **`Side`** class and design **`Ship`** classes to finish the whole assignment. Sample output of the program is as follows (output continues, this is just the beginning of it).
 
 ![alt tag](http://oi58.tinypic.com/259bfhv.jpg)

***Hints***

* **`Ship`** class may contain common variables of different types of ships such as coordinate(s), position, etc. (it is all up to you) 

* Obviously, **`damage`** and **`print`** functions in **`Side`** class use other functions that belong to subclasses of **`Ship`** class. 

* In ship class and its sub classes, you may or may not define variables dynamically with memory allocation. 

* Use ASCII arithmetic to calculate coordinates (each character is also an integer in [0,255]). 
