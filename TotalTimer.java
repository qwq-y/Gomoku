import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class TotalTimer extends TimerTask {

    int hour, minute, second;
    boolean total;

    public TotalTimer() {

    }

    public TotalTimer(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public boolean getTotal() {
        return total;
    }

    public void run() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 30));
        int t = 3600 * hour + 60 * minute + second;
        for (int i = t; i >= 0; i--) {
            StdDraw.setPenColor(Color.white);
            StdDraw.filledRectangle(745, 640, 60, 30);
            int s = i % 60;
            int m = (i / 60) % 60;
            int h = i / 3600;
            String time = String.format("%02d:%02d:%02d", h, m, s);
            StdDraw.setPenColor(145, 133, 255);
            StdDraw.textLeft(685, 640, time);
            StdDraw.show();
            if (i == 0) {
                total = true;
            }
            StdDraw.pause(1000);
        }
    }
}
