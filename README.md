# COS314---Assigment-1
This is my Assignment 1 from COS314 (Artificial Intelligence)
UP Yearbook link to COS314, which I took as an elective: https://www.up.ac.za/parents/yearbooks/2019/EBIT-faculty/UG-modules/view/COS%20314

The problem I was tasked with solving was the 8 Puzzle Problem. This puzzle involves sliding tiles on a 3x3 grid, where one tile space is
left open so that the other tiles can be moved around. The goal is to organise these tiles in a specific order. We had to use 4 different
search algorithms (Breadth First search, Best First Search, Hill Climbing and A-Star) to get to the end state and try and find the shortest
amount of moves the puzzle could be solved in.


I coded in Java and used 3 extra classes that I made myself:

- holdPuzzle
  This class keeps track of the order the puzzles are in. I use a 2D array to simulate the 3x3 space the puzzles are in, and this class holds
  that 2D array. I mostly use this to compare puzzles and keep track of past puzzles.

- puzzleArray
  This class is similar to holdPuzzle but I use it to keep track of the new moves that can be made. It also keeps track of how many moves
  have been made in the sequence of moves currently being explored (to find the smallest amount of moves possible).

- coords
  I use this class to keep track of where in the 2D the empty space is, and the tile that I want to move. I use this information to
  simulate and execute moves.

In order to check if the puzzle is in the goal state, I check each array individually and increment a counter variable for every number in
the array that matches the goal state. When the counter variable is nine, then I know the puzzle has been solved.















CG
