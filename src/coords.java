public class coords
{
    public int ci, cJ, zi, zJ;
    public int[][] puzzle;
    public int h = 0;
    public int level = 0;

    public coords(int ci, int cJ, int zi, int zJ, int[][] puzzle)
    {
        this.ci = ci;
        this.cJ = cJ;
        this.zi = zi;
        this.zJ = zJ;
        h = 0;

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
