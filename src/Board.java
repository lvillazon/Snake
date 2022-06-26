import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;

public class Board extends JPanel implements Runnable{

        private int SCREEN_HEIGHT = 480;
        private int SCREEN_WIDTH = 640;
        private final int BOTTOM_MARGIN = -50;
        public final int TARGET_DRAW_TIME = 200; // notional time in ms for each frame update
        public final int GRID_SIZE = 10;

        private JFrame frame;
        private Thread gameThread;
        private volatile boolean running;   // volatile to preserve state across threads
        private long updateDurationMillis;
        private KeyChecker keys;

        private Snake snake;
        private Grid grid;

        public Board(Grid g, Snake s) {
            grid = g;
            snake = s;
            frame = new JFrame("Snake");
            SCREEN_WIDTH = ((int)640/grid.getWidth()) * grid.getWidth();
            SCREEN_HEIGHT = ((int)480/grid.getHeight()) * grid.getHeight();
            System.out.println("Screen size: " + SCREEN_WIDTH + "," + SCREEN_HEIGHT);
            frame.setSize(SCREEN_WIDTH+16, SCREEN_HEIGHT+39);
            setBounds(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
            setBackground(Color.black);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(this);
            frame.setVisible(true);
            setFocusable(true);
            keys = new KeyChecker();
            addKeyListener(keys);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // clear the frame
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(new Color(0,0,0));
            g2.fillRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);

            // draw anything on the grid
            for (int y=0; y< grid.getHeight(); y++) {
                for (int x=0; x<grid.getWidth(); x++) {
                    if (grid.isWall(x,y)) {
                        g.setColor(Color.orange);
                        g2.fillRect(x*gridWidth(), y*gridHeight(), gridWidth(), gridHeight());
                    }
                    if (grid.isFood(x,y)) {
                        g.setColor(Color.red);
                        g2.fillOval(x*gridWidth(), y*gridHeight(), gridWidth(), gridHeight());
                    }
                }
            }

            // update and draw snake
            if (snake != null) {
                if (keys.getLastPressed() == "left") {
                    snake.move(-1,0);
                }
                if (keys.getLastPressed() == "right") {
                    snake.move(1,0);
                }
                if (keys.getLastPressed() == "up") {
                    snake.move(0, -1);
                }
                if (keys.getLastPressed() == "down") {
                    snake.move(0, 1);
                }

                int red = 0;
                for (int i = 0; i < snake.getLength(); i++) {
                    if (i==0) {
                        g.setColor(Color.red);
                    } else {
                        g.setColor(new Color(red, 128, 0));
                    }
                    red = i * (255 / snake.getLength());
                    g2.fillRect(snake.getX(i)*gridWidth(), snake.getY(i)*gridHeight(), gridWidth(), gridHeight());
                }
            }
        }

    private int gridWidth(){
        return (int)SCREEN_WIDTH/grid.getWidth();
    }

    private int gridHeight(){
        return (int)(SCREEN_HEIGHT)/grid.getHeight();
    }

    @Override
        public void addNotify() {
            System.out.println("SEQUENCE: Game addNotify");
            // called when the Game object is added to the JFrame
            // good place to put initialisation code for the game
            super.addNotify();
            initInput();
            initGame();
        }

        private void initInput() {
            // assigns a dispatcher for mouse and keyboard events
            //inputHandler = new InputHandler();
            //addKeyListener(inputHandler);
            //addMouseListener(inputHandler);
        }

        private void initGame() {
            // give the game its own thread
            System.out.println("SEQUENCE: Game thread started");
            running = true;
            gameThread = new Thread(this, "Game Thread");
            gameThread.start();
        }

        @Override
        public void run() {
            // Game loop
            long sleepDurationMillis = 0;

            while (running) {
                long beforeFrameDraw = System.nanoTime();
                repaint();

                updateDurationMillis = (System.nanoTime() - beforeFrameDraw) / 1000000L;
                sleepDurationMillis = Math.max(2, TARGET_DRAW_TIME - updateDurationMillis);
                //System.out.println(getFPS());
                try {
                    Thread.sleep(sleepDurationMillis);
                } catch (InterruptedException e) {
                    System.out.println("Failed to sleep game loop!");
                    e.printStackTrace();
                }
            }
            // quit when not running
            System.exit(0);
        }
    }
