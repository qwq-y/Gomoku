import java.awt.*;
import java.util.TimerTask;
import edu.princeton.cs.algs4.StdDraw;

public class TurnTimer extends TimerTask {

    int pause;
    boolean turn;
    Play play = new Play();

    public TurnTimer() {

    }

    public TurnTimer(int pause) {
        this.pause = pause;
    }

    public boolean getTurn() {
        return turn;
    }

    public void run() {
        while (true) {
            if (play.getPressed()) {
                System.out.println("true");
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
                    if (i == 0) {
                        turn = true;
                        break;
                    }
                    StdDraw.pause(1000);
                }
            }
        }

    }
}
