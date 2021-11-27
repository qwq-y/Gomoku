import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;

public class StartGame extends Window {
    public void welcomePage() {
        // "GOMOKU"
        StdDraw.setPenColor(145, 133, 255);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 150));
        StdDraw.text(473, 503, "GOMOKU");
        StdDraw.setPenColor(194, 188, 255);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 150));
        StdDraw.text(467, 497, "GOMOKU");
        // "Play"
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.filledRectangle(475, 295, 120, 60);
        StdDraw.setPenColor(255, 206, 245);
        StdDraw.filledRectangle(470, 300, 120, 60);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 60));
//        StdDraw.setPenColor(247, 247, 247);
//        StdDraw.text(473, 292, "Play");
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.text(470, 295, "Play");
        // "Introduction"
        StdDraw.setPenColor(255, 161, 84);
        StdDraw.filledRectangle(165, 295, 120, 45);
        StdDraw.setPenColor(255, 235, 166);
        StdDraw.filledRectangle(160, 300, 120, 45);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 36));
//        StdDraw.setPenColor(255, 136, 39);
//        StdDraw.text(163, 292, "Introduction");
        StdDraw.setPenColor(255, 161, 84);
        StdDraw.text(160, 295, "Introduction");
        // "Settings"
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.filledRectangle(785, 295, 120, 45);
        StdDraw.setPenColor(201, 241, 255);
        StdDraw.filledRectangle(780, 300, 120, 45);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 36));
//        StdDraw.setPenColor(247, 247, 247);
//        StdDraw.text(783, 292, "Settings");
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.text(780, 295, "Settings");
        // pictures
        StdDraw.picture(110, 140, "1.jpg", 140, 140);
        StdDraw.picture(230, 140, "2.jpg", 140, 140);
        StdDraw.picture(350, 140, "3.jpg", 140, 140);
        StdDraw.picture(470, 140, "4.jpg", 140, 140);
        StdDraw.picture(590, 140, "5.jpg", 140, 140);
        StdDraw.picture(710, 140, "6.jpg", 140, 140);
        StdDraw.picture(830, 140, "7.jpg", 140, 140);
    }
}
