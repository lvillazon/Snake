public class Grid {
    private int height;
    private int width;
    private int[][] squares;

    public Grid(int h, int w) {
        height = h;
        width = w;
        squares = new int[width][height];
        // create top & bottom border
        for (int i=0; i<width; i++) {
            squares[i][0] = 1;
            squares[i][height-1] = 1;
        }
        // side borders
        for (int i=0; i<height; i++) {
            squares[0][i] = 1;
            squares[width-1][i] = 1;
        }

        // add a food, for testing
        squares[20][20] = 2;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isWall(int x, int y) {
        return (squares[x][y]==1);
    }

    public boolean isFood(int x, int y) {
        return (squares[x][y]==2);
    }
}
