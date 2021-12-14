import edu.princeton.cs.algs4.StdDraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Board implements MouseListener, Runnable {
    private int n;
    Pieces pieces;

    public Board() {
        this.n = 14;
    }
    public Board(int n) {
        this.n = n;
    }

    @Override
    public void run() {
        Settings settings = new Settings();
        String score1 = "0";
        String score2 = "0";
        int n = settings.getN();
        int hour = settings.getHour();
        int minute = settings.getMinute();
        int second = settings.getSecond();
        int pause = settings.getPause();
        int player1 = settings.getPlayer1();
        int player2 = settings.getPlayer2();

        showWindow();
        showPicture();
        showBoard();
        showScores(score1, score2);
        showButtons();
        showTurnTimer(pause);
        showTotalTimer(hour, minute, second);
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

    public boolean showTotalTimer(int hour, int minute, int second) {
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
            if (i == 0) {
                return true;
            }
            StdDraw.pause(1000);
        }
        return false;
    }

    public boolean showTurnTimer(int pause) {
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
                return true;
            }
            StdDraw.pause(1000);
        }
        return false;
    }

    public void showScores(String score1, String score2) {
        // squares
        StdDraw.setPenColor(255, 235, 166);
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
    }

    public void showButtons() {
        // "Undo" button
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.filledRectangle(804, 346, 80, 30);
        StdDraw.setPenColor(201, 241, 255);
        StdDraw.filledRectangle(800, 350, 80, 30);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 25));
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.text(800, 345, "Undo");
        // "Stop" button
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.filledRectangle(804, 266, 80, 30);
        StdDraw.setPenColor(255, 206, 245);
        StdDraw.filledRectangle(800, 270, 80, 30);
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.text(800, 265, "Stop");
    }

    public void showPicture() {
        StdDraw.picture(840, 100, "Picture.png", 200, 200);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        // Stop
        if ( 720 < x && x < 880 && 270 < y && y < 300) {
            Stop stop = new Stop();
            stop.ask();
        }
        // Undo
        if (720 < x && x < 880 && 320 < y && y < 380) {
            pieces.Undo();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}

class Stop {
    JFrame frame = new JFrame();
    JLabel label = new JLabel();
    JButton yes = new JButton("Yes");
    JButton no = new JButton("No");

    public void ask() {
        label.setText("Do you wanner save your game?");
        frame.add(label);
        frame.add(yes);
        frame.add(no);
        frame.setVisible(true);

        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
            }
        });
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Save save = new Save();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                // 还缺少游戏停止的命令
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                // 还缺少游戏停止的命令
            }
        });
    }
}

class Save {
    Pieces pieces;
    String name;
    Frame frame = new JFrame();
    JTextField text = new JTextField(20);
    JLabel label = new JLabel();
    JButton ok = new JButton();

    public String getName() {
        return name;
    }

    public void creatName() {
        label.setText("Creat a name to save: ");
        frame.add(label);
        frame.add(text);
        frame.add(ok);
        frame.setVisible(true);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = text.getText();
                try (PrintWriter save = new PrintWriter(name);) {
                    int[][] array = pieces.getAllChess();
                    for (int[] i : array) {
                        for (int j : i) {
                            save.print(j + " ");
                        }
                    }
                }catch (FileNotFoundException ex) {
                    System.out.println("fail to save");
                }
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }
}
