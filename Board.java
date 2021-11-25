import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;

public class Board {
    private int n;

    public Board() {
        this.n = 14;
    }
    public Board(int n) {
        this.n = n;
    }
    public int getN() {return this.n;}
    public void setN(int n) {this.n = n;}

    public void showWindow() {
        StdDraw.setCanvasSize(940,700);
        StdDraw.setXscale(0.0, 940.0);
        StdDraw.setYscale(0.0, 700.0);
    }

    public void showBoard() {
        StdDraw.setPenColor(175, 167, 255);
        double size = 700.0;
        double frame = 40;
        double a = (size - 2*frame) / n;
        double i = frame;
        while (i <= size-frame + 1) {
            StdDraw.line(i, frame, i, size-frame);
            StdDraw.line(frame, i, size-frame, i);
            i += a;
        }
        StdDraw.filledCircle(n/2 * a + frame, n/2 * a +frame, 0.15 * a);
    }

    public void showTotalTimer(int hour, int minute, int second) {
        StdDraw.enableDoubleBuffering();
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 30));
        int t = 3600 * hour + 60 * minute + second;
        for (int i = t; i >= 0; i--) {
            StdDraw.setPenColor(Color.white);
            StdDraw.filledRectangle(745, 640, 60,30);
            int s = i % 60;
            int m = (i / 60) % 60;
            int h = i / 3600;
            String time = String.format("%02d:%02d:%02d", h, m, s);
            StdDraw.setPenColor(145, 133, 255);
            StdDraw.textLeft(685, 640, time);
            StdDraw.show();
            StdDraw.pause(1000);
        }
    }

    public void showTurnTimer(int pause) {
        StdDraw.setPenColor(194, 188, 255);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
        StdDraw.textLeft(685, 560, "this turn");
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 60));
        StdDraw.enableDoubleBuffering();
        for (int i = pause; i >= 0; i--) {
            StdDraw.setPenColor(Color.white);
            StdDraw.filledRectangle(835, 560, 80,40);
            int s = i % 60;
            int m = (i / 60) % 60;
            String limit = String.format("%02d:%02d", m, s);
            StdDraw.setPenColor(194, 188, 255);
            StdDraw.text(835, 560, limit);
            StdDraw.show();
            StdDraw.pause(1000);
        }
    }

    public void showScores(String score1, String score2) {
        // squares
        StdDraw.setPenColor(255, 227, 133);
        StdDraw.filledSquare(735, 455, 50);
        StdDraw.filledSquare(865, 455, 50);
        // digits
        StdDraw.setPenColor(255, 161, 84);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 75));
        StdDraw.text(735, 445, score1);
        StdDraw.text(865, 445, score2);
        // "VS"
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 35));
        StdDraw.text(800, 435, "VS");
//        StdDraw.filledCircle(800, 450, 4);
//        StdDraw.filledCircle(800, 430, 4);
    }

    public void showButtons() {
        // "Undo" button
        StdDraw.setPenColor(201, 241, 255);
        StdDraw.filledRectangle(800, 350, 80, 30);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 25));
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.text(800, 345, "Undo");
        // "Stop" button
        StdDraw.setPenColor(255, 206, 245);
        StdDraw.filledRectangle(800, 270, 80, 30);
        StdDraw.setPenColor(255, 0, 202);
        StdDraw.text(800, 265, "Stop");
    }

    public void showPicture() {
        StdDraw.picture(840, 100, "Picture.png", 200, 200);
    }
}

