public class Main {

    public static void main(String[] args) {
        Board b = new Board();
        Grid g = new Grid(10,10);
        Snake sid = new Snake(g);
        b.addSnake(sid);
    }
}
