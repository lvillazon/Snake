import java.util.ArrayList;

public class Snake_exemplar {
    private int maxLength = 100;
    private int[] xCoords;
    private int[] yCoords;
    private int length;
    private Grid grid;

    public Snake_exemplar(Grid g) {
        grid = g;
        // add some starting segments
        int startX = grid.getWidth()/2;
        int startY = grid.getHeight()/2;
        length = 5;
        xCoords = new int[maxLength];
        yCoords = new int[maxLength];
        for (int i=0; i<length; i++) {
            xCoords[i] = startX + i;
            yCoords[i] = startY;
        }
    }

    public int getLength() {
        return length;
    }

    public int getX(int i) {
        return xCoords[i];
    }
    public int getY(int i) {
        return yCoords[i];
    }

    public void move(int dx, int dy) {
        // shuffle the tail segments up 1 space
        for (int i=length-1; i>0; i--) {
            xCoords[i] = xCoords[i-1];
            yCoords[i] = yCoords[i-1];
        }
        // move the head to the new location
        xCoords[0] = xCoords[0] + dx;
        yCoords[0] = yCoords[0] + dy;

        // check for food
        if (grid.isFood(xCoords[0], yCoords[0])) {
            grow(1);
        }
    }

    public void grow(int segments) {
    }
}
