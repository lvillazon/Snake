public class Grid {
    private int height;
    private int width;
    private int[][] squares;

    public Grid(int h, int w) {
        height = h;
        width = w;
        squares = new int[width][height];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
