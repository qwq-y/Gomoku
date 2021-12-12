import edu.princeton.cs.algs4.StdDraw;
import javax.swing.*;
import java.awt.*;

public class StartGame {
    public void showWindow() {
        StdDraw.setCanvasSize(940,700);
        StdDraw.setXscale(0.0, 940.0);
        StdDraw.setYscale(0.0, 700.0);
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

//    public void isAction() {
//        double x = StdDraw.mouseX();
//        double y = StdDraw.mouseY();
//        while (true) {
//            if (StdDraw.isMousePressed()) {
//                if (350 <= x && x <= 590 && 240 <= y && y <= 360) {
//                    play();
//                }
//            }
//        }
//    }

    public boolean isPlay() {
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        while (true) {
            if (StdDraw.isMousePressed()) {
                if (350 <= x && x <= 590 && 240 <= y && y <= 360) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
    }

    public boolean isIntroduction() {
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        while (true) {
            if (StdDraw.isMousePressed()) {
                if (40 <= x && x <= 280 && 255 <= y && y <= 345) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
    }

    public boolean isSettings() {
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();
        while (true) {
            if (StdDraw.isMousePressed()) {
                if (660 <= x && x <= 900 && 255 <= y && y <= 345) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
    }

    public void introduction(boolean isIntroduction) {
        if (isIntroduction()) {

        }
    }

//    public void settings(boolean isSettings) {
//        if (isSettings()) {
//            Settings settings = new Settings();
////            settings.settingsWindow();
////            settings.listener();
//            Thread settingsThread = new Thread(settings);
//            settingsThread.start();
//        }
//    }
//
//    public void play(boolean isPlay) {
//// test
//        Settings settings = new Settings();
//        String score1 = "0";
//        String score2 = "0";
//        int n = settings.getN();
//        int hour = settings.getHour();
//        int minute = settings.getMinute();
//        int second = settings.getSecond();
//        int pause = settings.getPause();
//        int player1 = settings.getPlayer1();
//        int player2 = settings.getPlayer2();
////
//        if (isPlay()) {
//            StdDraw.clear();
//            Board board = new Board(n);
////            board.showWindow();
////            board.showPicture();
////            board.showBoard();
////            board.showScores(score1, score2);
////            board.showButtons();
////            board.showTurnTimer(pause);
////            board.showTotalTimer(hour, minute, second);
//            Thread playThread = new Thread(board);
//            playThread.start();
//        }
//    }
}