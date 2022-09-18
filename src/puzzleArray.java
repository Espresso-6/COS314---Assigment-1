public class puzzleArray
{
    public int[][] puzzle;
    int level = 0;

    public puzzleArray(int[][] puzzle)
    {
        int cols, rows;
        rows = puzzle.length;
        cols = puzzle[0].length;


        this.puzzle = new int[rows][cols];
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                this.puzzle[i][j] = puzzle[i][j];
            }
        }
    }
}
