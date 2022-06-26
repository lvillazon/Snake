public class Main {

    public static void main(String[] args) {
        Grid g = new Grid(30,30);
        Snake sid = new Snake(g);
        Board b = new Board(g, sid);
    }
}
