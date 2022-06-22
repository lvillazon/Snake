import java.util.ArrayList;

public class Snake {
    ArrayList<Segment> body;
    Segment head;

    public Snake(int startX, int startY) {
        body = new ArrayList<Segment>();
        head = new Segment(startX, startY);
        body.add(head);
        // add some starting segments
        for (int i=1; i<5; i++) {
            Segment s = new Segment(startX + i * 10, startY);
            body.add(s);
        }
    }

    public int getLength() {
        return body.size();
    }

    public Segment getSegment(int i) {
        return body.get(i);
    }

    public void left(int distance) {
        Segment tempSeg = new Segment(head.x, head.y);
        shuffleBody();
        head.x = tempSeg.x - distance;
    }

    public void right(int distance) {
        Segment tempSeg = new Segment(head.x, head.y);
        shuffleBody();
        head.x = tempSeg.x + distance;
    }

    public void up(int distance) {
        Segment tempSeg = new Segment(head.x, head.y);
        shuffleBody();
        head.y = tempSeg.y - distance;
    }

    public void down(int distance) {
        Segment tempSeg = new Segment(head.x, head.y);
        shuffleBody();
        head.y = tempSeg.y + distance;
    }

    private void shuffleBody() {
        if (body.size() > 1) {
            for (int i = 1; i < body.size()-1; i++) {
                body.get(i).x = body.get(i-1).x;
                body.get(i).y = body.get(i-1).y;
            }
        }
    }
}
