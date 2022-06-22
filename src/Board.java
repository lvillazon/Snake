import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;

public class Board extends JPanel implements Runnable{

        private final int SCREEN_HEIGHT = 480;
        private final int SCREEN_WIDTH = 640;
        private final int BOTTOM_MARGIN = -50;
        public final int TARGET_DRAW_TIME = 200; // notional time in ms for each frame update
        public final int GRID_SIZE = 10;

        private JFrame frame;
        private Thread gameThread;
        private volatile boolean running;   // volatile to preserve state across threads
        private long updateDurationMillis;
        private KeyChecker keys;

        private Snake snake;

        public Board() {
            frame = new JFrame("Snake");
            frame.setSize(640, 480);
            setBounds(0,0, 640, 480);
            setBackground(Color.black);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            snake = null;
            frame.add(this);
            frame.setVisible(true);
            setFocusable(true);
            keys = new KeyChecker();
            addKeyListener(keys);
        }

        public void addSnake(Snake s) {
            snake = s;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // draw the outline around the play area
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(new Color(0,128,0));
            g2.fillRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
            g.setColor(new Color(0,0,0));
            g2.fillRect(GRID_SIZE,GRID_SIZE, SCREEN_WIDTH-35, SCREEN_HEIGHT-60);

            // update and draw snake
            if (snake != null) {
                if (keys.getLastPressed() == "left") {
                    snake.left(GRID_SIZE);
                }
                if (keys.getLastPressed() == "right") {
                    snake.right(GRID_SIZE);
                }
                if (keys.getLastPressed() == "up") {
                    snake.up(GRID_SIZE);
                }
                if (keys.getLastPressed() == "down") {
                    snake.down(GRID_SIZE);
                }

                g.setColor(new Color(0,128,0));
                for (int i = 0; i < snake.getLength(); i++) {
                    g2.fillRect(snake.getSegment(i).x, snake.getSegment(i).y, GRID_SIZE, GRID_SIZE);
                }
            }
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
