import edu.princeton.cs.algs4.StdDraw;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.*;
import java.awt.event.MouseEvent;

//public class StartGame extends Window implements MouseAdapter {
//    public void mouseClick(MouseEvent event) {
//        isPlay();
//    }

//public class StartGame extends Window {
//    public StartGame() {
//        addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent event) {
//                isPlay(event);
//            }
//        });
//    }

//public class StartGame extends Window implements MouseListener{
//    JPanel panel = new JPanel();
//
//    public StartGame() {
//        panel.addMouseListener(this);
//    }

public class StartGame extends Window implements MouseListener{
    Frame frame = new Frame();

    public StartGame() {
        frame.addMouseListener(this);
        frame.setSize(940, 700);
        frame.setVisible(true);
        frame.setLocation(0, 0);
    }

    public static void main(String[] args) {
        new StartGame();
    }

    public boolean isPlay(MouseEvent event) {
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        if (350 <= x && x <= 590 && 240 <= y && y <= 360) {
            return true;
        }
        else {
            return false;
        }
    }

    public void play(boolean isPlay, MouseEvent event) {
// test
        String score1 = "0";
        String score2 = "0";
        int hour = 0;
        int minute = 1;
        int second = 16;
        int pause = 8;
//
        if (isPlay(event)) {
            StdDraw.clear();
            Board board = new Board();
            board.showWindow();
            board.showPicture();
            board.showBoard();
            board.showScores(score1, score2);
            board.showButtons();
            board.showTurnTimer(pause);
            board.showTotalTimer(hour, minute, second);
        }
    }

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
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.text(470, 295, "Play");
        // "Introduction"
        StdDraw.setPenColor(255, 161, 84);
        StdDraw.filledRectangle(165, 295, 120, 45);
        StdDraw.setPenColor(255, 235, 166);
        StdDraw.filledRectangle(160, 300, 120, 45);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 36));
        StdDraw.setPenColor(255, 161, 84);
        StdDraw.text(160, 295, "Introduction");
        // "Settings"
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.filledRectangle(785, 295, 120, 45);
        StdDraw.setPenColor(201, 241, 255);
        StdDraw.filledRectangle(780, 300, 120, 45);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 36));
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

    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked");
    }
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered");
    }
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited");
    }
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");
    }
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
    }
}
