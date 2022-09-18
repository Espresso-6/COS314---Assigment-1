import java.util.*;
import java.lang.String;

import static java.lang.System.exit;

public class Main
{
    public static int totalMoves = 0, singleMoves = 0;

//            1 123804765 134862705 5
//            2 123804765 281043765 9
//            3 123804765 281463075 12
//            4 134805726 123804765 6
//            5 231708654 123804765 14
//            6 231804765 123804765 16
//            7 123804765 231804765 16
//            8 123804765 567408321 30
//            9 876105234 123804765 28
//            10 867254301 123456780 31

    public static int[][] startState = {{1, 2, 3},{8, 0, 4},{7, 6, 5}};
    public static int level = -1;
    public static int[][] endState = {{1, 3, 4},{8, 6, 2},{7, 0, 5}};
    public static ArrayList<holdPuzzle> pastPuzzles = new ArrayList<holdPuzzle>();
    public static holdPuzzle prev = null;
    public static ArrayList<puzzleArray> boards = new ArrayList<puzzleArray>();

    public static ArrayList<coords> BFSMoves = new ArrayList<coords>();

    /* Driver code */
    public static void main (String[] args)
    {
        Scanner sc= new Scanner(System.in);    //System.in is a standard input stream
        System.out.print("Program only works with 8 puzzles.\n");
        System.out.print("Please enter the puzzles in the following format: xxxxxxxxx\n");
        System.out.print("For example, in this format a traditionally solved puzzle will look as follows: 123456780\n");
        System.out.print("Enter the start state: ");
        int a= sc.nextInt();

        int i = 2, j = 2;
        while (a > 0)
        {
            startState[j][i] = ( a % 10 );
            a = a / 10;
            i--;
            if (i < 0)
            {
                j--;
                i = 2;
            }
        }

        System.out.println(Arrays.deepToString(startState));

        System.out.print("\nEnter the goal state: ");
        a= sc.nextInt();

        i = 2;
        j = 2;
        while (a > 0)
        {
            endState[j][i] = ( a % 10 );
            a = a / 10;
            i--;
            if (i < 0)
            {
                j--;
                i = 2;
            }
        }

        System.out.println(Arrays.deepToString(endState));





        boards.add(new puzzleArray(startState));

        System.out.print("\nHere are the different algorithms that can be used to solve the puzzle.\n");
        System.out.print("When prompted, please pick ONLY the corresponding number of the algorithm you wish to use.\n");
        System.out.print("0 = Breadth First Search\n");
        System.out.print("1 = Best First Search\n");
        System.out.print("2 = Hill Climbing Search  (this one rarely works)\n");
        System.out.print("3 = A* Search\n");
        System.out.print("Please pick the algorithm you would like to use to solve the puzzle:");
        a= sc.nextInt();


        if (a == 0) {
            System.out.println("\nbreadth first (this one may take a while)");
            doBrFSMove(boards);
        }
        else if (a == 1) {
            System.out.println("\nBFS");
            doBFSMove(boards);
        } else if (a == 2) {
            System.out.println("\nHill");
            doHill(boards);
        } else if (a == 3) {
            System.out.println("\nA*");
            doAStar(boards);
        } else System.out.println("Nice try but please run the program again and pick a number between 0-3 :)");
    }


    static public ArrayList<coords> findMove(int[][] puzzle)
    {
        //first, figure out where the empty space is
        int iZero = 0, jZero = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (puzzle[j][i] == 0)
                {
                    jZero = j;
                    iZero = i;
                    i = 4;
                    j = 4;
                }
            }
        }

        //now figure out the moves that can be done
        ArrayList<coords> moves = new ArrayList<coords>();

        //check:
        //right
        if (jZero+1 < 3)
        {
            int[][] temp = puzzle.clone();
            moves.add(new coords(iZero, jZero+1, iZero, jZero, temp));
        }
        //left
        if (jZero-1 > -1)
        {
            int[][] temp = puzzle.clone();
            moves.add(new coords(iZero, jZero-1, iZero, jZero, temp));
        }
        //up
        if (iZero-1 > -1)
        {
            int[][] temp = puzzle.clone();
            moves.add(new coords(iZero-1, jZero, iZero, jZero, temp));
        }
        //down
        if (iZero+1 < 3)
        {
            int[][] temp = puzzle.clone();
            moves.add(new coords(iZero+1, jZero, iZero, jZero, temp));
        }



        return moves;

    }

    public static void doBrFSMove(ArrayList<puzzleArray> board)
    {
        //System.out.println("\n");
        ArrayList<puzzleArray> BrFS = new ArrayList<>();

        for (int k = 0; k < board.size(); k++)
        {
            boolean duplicate = false;
            int found = 0;

            for (int a = 0; a < board.get(k).puzzle.length; a++)
            {
                for (int b = 0; b < board.get(k).puzzle[a].length; b++)
                {
                    if (board.get(k).puzzle[a][b] == endState[a][b])
                    {
                        found++;
                    }
                }
            }

            if (found == 9)
            {
                System.out.println("found it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nIt took a total of " + totalMoves + " moves to get there, and the optimum found by the algorithm was " + singleMoves);
                exit(0);
            }


            for (int i = 0; i < pastPuzzles.size(); i++) {
                if (Arrays.deepEquals(pastPuzzles.get(i).puzzle, board.get(k).puzzle))
                {
                    //System.out.println("duplicate...");
                    duplicate = true;
                }
            }

            if (!duplicate) {
                pastPuzzles.add(new holdPuzzle(board.get(k).puzzle));
                //System.out.println("finding move...");

                ArrayList<coords> AvMoves = new ArrayList<coords>();
                AvMoves = findMove(board.get(k).puzzle);


                for (int i = 0; i < AvMoves.size(); i++)
                {
                    coords current = AvMoves.get(i);
                    current.puzzle[current.zJ][current.zi] = current.puzzle[current.cJ][current.ci];
                    current.puzzle[current.cJ][current.ci] = 0;
                    //System.out.println("move completed: " + current.puzzle[current.zJ][current.zi]);
                    //for BrFS
                    totalMoves++;
                    BrFS.add(new puzzleArray(current.puzzle));
                    //for DFS
                    //doMove(current.puzzle);
                }


            }
        }
        singleMoves++;
        doBrFSMove(BrFS);
    }


    public static void doBFSMove(ArrayList<puzzleArray> board)
    {
        //System.out.println("\n");
        singleMoves = board.get(0).level;

        //check if endstate
        int hVal = 0;
        for (int a = 0; a < board.get(0).puzzle.length; a++)
        {
            for (int b = 0; b < board.get(0).puzzle[a].length; b++)
            {
                if (board.get(0).puzzle[a][b] == endState[a][b])
                {
                    hVal++;
                }
            }
        }
        if (hVal == 9)
        {
            System.out.println("found it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nIt took a total of " + totalMoves + " moves to get there.");
            System.out.println("The optimum found was " + singleMoves);
            exit(0);
        }

        //this puzzle will be resolved, as such, it has been seen and will get moved into pastPuzzles
        pastPuzzles.add(new holdPuzzle(board.get(0).puzzle));

        //find the moves it can do
        ArrayList<coords> AvMoves = new ArrayList<coords>();
        AvMoves = findMove(board.get(0).puzzle);

        //going to the next level so increment the level (really bad var name tho)
        singleMoves++;

        for (int i = 0; i < AvMoves.size(); i++)
        {
            //do the moves for all moves found
            coords current = AvMoves.get(i);
            current.puzzle[current.zJ][current.zi] = current.puzzle[current.cJ][current.ci];
            current.puzzle[current.cJ][current.ci] = 0;
            totalMoves++;

            hVal = 0;
            //calculate and store the heuristic
            for (int a = 0; a < current.puzzle.length; a++)
            {
                for (int b = 0; b < current.puzzle[a].length; b++)
                {
                    if (current.puzzle[a][b] == endState[a][b])
                    {
                        hVal++;
                    }
                }
            }
            current.h = hVal;
            current.level = singleMoves;
        }



        //check that we haven't moved to a duplicate state
        boolean duplicate = false;
        for (int i = 0; i < AvMoves.size(); i++)
        {
            for (int j = 0; j < pastPuzzles.size(); j++)
            {
                if (Arrays.deepEquals(pastPuzzles.get(j).puzzle, AvMoves.get(i).puzzle))
                {
                    duplicate = true;
                    //System.out.println("duplicate...");
                }
            }
            if (!duplicate)
            {
                //System.out.println("New move found...");
                AvMoves.get(i).level = singleMoves;
                BFSMoves.add(AvMoves.get(i));
            }
            duplicate = false;
        }


        //now use an insertion sort to make sure the best state is first
        //in BFSMoves there will never be a duplicate
        //because we check above that duplicates don't get added
        for (int i = 1; i < BFSMoves.size(); i++)
        {
            coords holder = BFSMoves.get(i);
            int j = i - 1;


            while (j >= 0 && holder.h > BFSMoves.get(j).h)
            {
                BFSMoves.set(j + 1, BFSMoves.get(j));
                --j;
            }
            BFSMoves.set(j + 1, holder);
        }

        //now we take the best move which is at the first position
        ArrayList<puzzleArray> BrFS = new ArrayList<>();
        BrFS.add(new puzzleArray(BFSMoves.get(0).puzzle));
        BrFS.get(0).level = BFSMoves.get(0).level;
        BFSMoves.remove(0);
        doBFSMove(BrFS);
    }


    public static void doHill(ArrayList<puzzleArray> board)
    {
        ArrayList<coords> AvMoves = new ArrayList<coords>();
        level++;

        for (int i = 0; i < board.size(); i++)
        {
            pastPuzzles.add(new holdPuzzle(board.get(i).puzzle));
            AvMoves = findMove(board.get(i).puzzle);
        }

        //System.out.println("testing move...");




        int bestH = -1, bestOne = -1;

        for (int i = 0; i < AvMoves.size(); i++)
        {
            coords current = AvMoves.get(i);
            current.puzzle[current.zJ][current.zi] = current.puzzle[current.cJ][current.ci];
            current.puzzle[current.cJ][current.ci] = 0;
            totalMoves++;
        }


        boolean duplicate;
        for (int i = 0; i < AvMoves.size(); i++)
        {
            duplicate = false;

            //we have done the moves, now we must check for duplicates
            for (int j = 0; j < pastPuzzles.size(); j++)
            {
                if (Arrays.deepEquals(pastPuzzles.get(j).puzzle, AvMoves.get(i).puzzle))
                {
                    //System.out.println("duplicate...");
                    duplicate = true;
                }
            }

            if (!duplicate)
            {
                //pastPuzzles.add(new holdPuzzle(AvMoves.get(i).puzzle));
                int hVal = 0;
                for (int j = 0; j < AvMoves.get(i).puzzle.length; j++) {
                    for (int k = 0; k < AvMoves.get(i).puzzle[0].length; k++) {
                        if (AvMoves.get(i).puzzle[j][k] == endState[j][k]) hVal++;
                    }
                }

                AvMoves.get(i).h = hVal;

                if (hVal == 9)
                {
                    System.out.println("found it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nIt took a total of " + totalMoves + " moves to get there.");
                    System.out.println("Optimum found: " + level);
                    exit(0);
                }

                if (hVal > bestH)
                {
                    bestH = hVal;
                }
            }
        }

        ArrayList<puzzleArray> nextHillArr = new ArrayList<>();

        for (int i = 0; i < AvMoves.size(); i++)
        {
            if (AvMoves.get(i).h == bestH) nextHillArr.add(new puzzleArray(AvMoves.get(i).puzzle));
        }
        if (nextHillArr.size() != 0)
        doHill(nextHillArr);
        else System.out.println("failed...........");
    }



    public static void doAStar(ArrayList<puzzleArray> board)
    {
        //System.out.println("\n");
        ArrayList<puzzleArray> BrFS = new ArrayList<>();
        singleMoves = board.get(0).level;

        //check if endstate
        int hVal = 0;
        for (int a = 0; a < board.get(0).puzzle.length; a++)
        {
            for (int b = 0; b < board.get(0).puzzle[a].length; b++)
            {
                if (board.get(0).puzzle[a][b] == endState[a][b])
                {
                    hVal++;
                }
            }
        }
        if (hVal == 9)
        {
            System.out.println("found it!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\nIt took a total of " + totalMoves + " moves to get there.");
            System.out.println("The optimum found was " + singleMoves);
            exit(0);
        }

        //this puzzle will be resolved, as such, it has been seen and will get moved into pastPuzzles
        pastPuzzles.add(new holdPuzzle(board.get(0).puzzle));

        ArrayList<coords> AvMoves = new ArrayList<coords>();
        AvMoves = findMove(board.get(0).puzzle);

        singleMoves++;
        for (int i = 0; i < AvMoves.size(); i++)
        {
            coords current = AvMoves.get(i);
            current.puzzle[current.zJ][current.zi] = current.puzzle[current.cJ][current.ci];
            current.puzzle[current.cJ][current.ci] = 0;
            totalMoves++;

            hVal = 0;
            for (int a = 0; a < current.puzzle.length; a++)
            {
                for (int b = 0; b < current.puzzle[a].length; b++)
                {
                    if (current.puzzle[a][b] == endState[a][b])
                    {
                        hVal++;
                    }
                }
            }
            current.level = singleMoves;
            current.h = 9 - hVal + singleMoves;
        }



        //check that we haven't moved to a duplicate state
        boolean duplicate = false;
        for (int i = 0; i < AvMoves.size(); i++)
        {
            for (int j = 0; j < pastPuzzles.size(); j++)
            {
                if (Arrays.deepEquals(pastPuzzles.get(j).puzzle, AvMoves.get(i).puzzle))
                {
                    duplicate = true;
                    //System.out.println("duplicate...");
                }
            }
            if (!duplicate)
            {
                //System.out.println("New move found...");
                AvMoves.get(i).level = singleMoves;
                BFSMoves.add(AvMoves.get(i));
            }
            duplicate = false;
        }


        //now use an insertion sort to make sure the best state is first
        //This is A* so the lowest state is the best
        //Above we got the number of tiles OUT of place by subtracting the number of tiles in place from 9
        //then we added the current level
        //in BFSMoves there will never be a duplicate
        //because we check above that duplicates don't get added
        for (int i = 1; i < BFSMoves.size(); i++)
        {
            coords holder = BFSMoves.get(i);
            int j = i - 1;


            while (j >= 0 && holder.h < BFSMoves.get(j).h)
            {
                BFSMoves.set(j + 1, BFSMoves.get(j));
                --j;
            }
            BFSMoves.set(j + 1, holder);
        }

        //now we take the best move which is at the first position
        if (BFSMoves.size() != 0) {
            BrFS.add(new puzzleArray(BFSMoves.get(0).puzzle));
            BrFS.get(0).level = BFSMoves.get(0).level;
            BFSMoves.remove(0);
            while (BFSMoves.size() > 100) BFSMoves.remove(BFSMoves.size() - 1);
            //System.out.println("Total moves so far: " + totalMoves);
            doAStar(BrFS);
        } else System.out.println("failed");
    }

}
