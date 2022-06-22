import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class KeyChecker extends KeyAdapter {
    private String lastPressed;

    public KeyChecker() {
        lastPressed = "none";
    }

    public void keyPressed(KeyEvent event) {
        String[] direction = {"left", "up", "right", "down"};
        if (event.getKeyCode() >=37 && event.getKeyCode() <=40) {
            lastPressed = direction[event.getKeyCode() - 37];
        }
    }

    public String getLastPressed() {
        return lastPressed;
    }
}