import edu.princeton.cs.algs4.StdDraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Play extends Component implements Runnable, MouseListener {
    int n;
    int hour, minute, second;
    double frame, size, a;
    double x, y;
    int h, v;
    boolean isBlack, canPlay;
    String message;
    int[][] allChess;
    UndoPosition[] undoPositions = new UndoPosition[(n + 2) * (n + 2)];
    int totalCount = 0;

    public Play() {
        this.n = 14;
        hour = 0;
        minute = 15;
        second = 0;
        frame = 40;
        size = 700;
        a = (size - 2*frame) / n;
        h = (int)Math.round((x - frame) / a);
        v = (int)Math.round((y - frame) / a);
        allChess = new int[n + 2][n + 2];
    }

    public Play(int n, int hour, int minute, int second) {
        this.n = n;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        frame = 40;
        size = 700;
        a = (size - 2*frame) / n;
        h = (int)Math.round((x - frame) / a);
        v = (int)Math.round((y - frame) / a);
        allChess = new int[n + 2][n + 2];
        showBoard();
    }

    @Override
    public void run() {
        System.out.println("thread started");
        while (true) {
            if (StdDraw.isMousePressed() ) {
                x = StdDraw.mouseX();
                y = StdDraw.mouseY();
                StdDraw.pause(100);

                if (frame <= x && x <=  size - frame && frame <= y && y <= size - frame) {
                    if (allChess[h][v] == 0 && !isForbiddenMove()) {
                        UndoPosition undoPosition = new UndoPosition(h, v);
                        undoPositions[totalCount] = undoPosition;
                        totalCount++;
                        if (isBlack) {
                            allChess[h][v] = 1;
                            isBlack = false;
                            message = "Player2' turn";
                        } else {
                            allChess[h][v] = 2;
                            isBlack = true;
                            message = "Player1's turn";
                        }
                        if (this.checkWin()) {
                            if (allChess[h][v] == 1) {
                                canPlay = false;
                            }
                            if (allChess[h][v] == 2) {
                                canPlay = false;
                            }
                        }
                    }
                    else if (allChess[h][v] == 0 && isForbiddenMove()) {
                        JOptionPane.showMessageDialog(null,"A 33 forbidden move!");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "There is already a chess! ");
                    }
                    drawPieces();
                }
            }
        }
    }

    public int[][] getAllChess() {
        return allChess;
    }

    public void drawPieces() {
        for(int i = 0; i < n ; i++){
            for(int j = 0; j < n ; j++){
                double tempX = frame + a * i;
                double tempY = frame + a * j;
                if (allChess[i][j] == 1) {
//                    switch (settings.getPlayer1()) {
//                        case 1:
//                            StdDraw.setPenColor(Color.BLACK);
//                        case 2 :
//                            StdDraw.setPenColor(Color.BLACK);
//                        default:
//                            StdDraw.setPenColor(Color.BLACK);
//                    }
                    StdDraw.setPenColor(Color.BLACK);
                    StdDraw.filledCircle(tempX, tempY, 0.3 * a);
                }
                if (allChess[i][j] == 2) {
//                    switch (settings.getPlayer1()) {
//                        case 1:
//                            StdDraw.setPenColor(Color.YELLOW);
//                        case 2 :
//                            StdDraw.setPenColor(Color.YELLOW);
//                        default:
//                            StdDraw.setPenColor(Color.YELLOW);
//                    }
                    StdDraw.setPenColor(Color.YELLOW);
                    StdDraw.filledCircle(tempX, tempY, 0.3 * a);
                }
            }
        }
        StdDraw.show();
    }

    private int checkCount(int xChange, int yChange, int color){
        int count = 1;
        int tempX = xChange;
        int tempY = yChange;
        while(color == allChess[h + xChange][v + yChange] && (h + xChange) >= 0 && (x + xChange) <= (n + 2) &&
                (y + yChange) >= 0 && (y + yChange) <= (n + 2)){
            count ++;
            if(xChange!=0){
                if(xChange > 0) {
                    xChange++;
                }else{
                    xChange--;
                }
            }
            if(yChange != 0){
                if(yChange > 0) {
                    yChange++;
                }else{
                    yChange--;
                }
            }
        }
        xChange = tempX;
        yChange = tempY;
        while(color == allChess[h - xChange][v - yChange]&& (h - xChange) >= 0 && (h - xChange) <= (n + 2) &&
                (v - yChange) >= 0 && (v - yChange) <= (n + 2)){
            count ++;
            if(xChange!=0){
                if(xChange > 0) {
                    xChange++;
                }else{
                    xChange--;
                }
            }
            if(yChange != 0){
                if(yChange > 0) {
                    yChange++;
                }else{
                    yChange--;
                }
            }
        }
        return count;
    }

    private boolean checkWin() {
        boolean flag = false;
        int count = 1;
        int color = allChess[h][v];
        count = this.checkCount(1, 0, color);
        if (count == 5) {
            flag = true;
        }
        else {
            count = this.checkCount(0,1,color);
            if(count == 5) {
                flag = true;
            }else{
                count = this.checkCount(1,-1,color);
                if(count == 5){
                    flag = true;
                }else{
                    count = this.checkCount(1,1,color);
                    if(count == 5){
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    private boolean isForbiddenMove() {
        boolean F = false;
        if (isBlack == true) {
            int count1 = 1;
            int count2 = 1;
            int count3 = 1;
            int count4 = 1;
            int countForbidden = 0;
            int color = 1;
            // horizontal
            int i = 1;
            while (color == allChess[h + i][v] && (h + i) < (n + 2)) {
                count1++;
                i++;
            }
            int j = 1;
            while (color == allChess[h - j][v] && (h - j) > 0 ) {
                count1++;
                j++;
            }
            if (count1 == 3 && allChess[h + i + 1][v] == 0 && allChess[h - j - 1][v] == 0) {
                countForbidden++;
            }
            // vertical
            i = 1;
            while (1 == allChess[h][v + i] && (v + i) < (n + 2)) {
                count2++;
                i++;
            }
            j = 1;
            while (color == allChess[h][v - j] && (v - j) > 0) {
                count2++;
                j++;
            }
            if (count2 == 3 && allChess[h][v + i + 1] == 0 && allChess[h][v - j - 1] == 0) {
                countForbidden++;
            }
            // upper right
            i = 1;
            while (color == allChess[h + i][v + i] && (h + i) < (n + 2) && (v + i) < (n + 2)) {
                count3++;
                i++;
            }
            j = 1;
            while (color == allChess[h - j][v - j]&& (h - j) > 0 && (v - j) >0) {
                count3++;
                j++;
            }
            if (count3 == 3 && allChess[h + i + 1][v + i + 1] == 0 && allChess[h -j - 1][v - j - 1] == 0) {
                countForbidden++;
            }
            // upper left
            i = 1;
            while (color == allChess[h - i][v + i] && (h - i) > 0 && (v + i) < (n + 2)) {
                count4++;
                i++;
            }
            j = 1;
            while (color == allChess[h + j][v - j] && (h + j) < (n + 2) && (v - j) > 0) {
                count4++;
                j++;
            }
            if (count4 == 3 && allChess[h - i - 1][v + i + 1] == 0 && allChess[h + j + 1][v- j - 1] == 0) {
                countForbidden++;
            }

            if(countForbidden >= 2){
                F = true;
            }
        }
        return F;
    }

    public void Undo() {
        int xPosition = undoPositions[totalCount].pX;
        int yPosition = undoPositions[totalCount].pY;
        allChess[xPosition][yPosition] = 0;
        totalCount--;
    }

    public void showBoard() {
        StdDraw.setCanvasSize(940,700);
        StdDraw.setXscale(0.0, 940.0);
        StdDraw.setYscale(0.0, 700.0);
        // board
        StdDraw.setPenColor(175, 167, 255);
        double a = (size - 2*frame) / n;
        double i = frame;
        while (i <= size-frame + 1) {
            StdDraw.line(i, frame, i, size-frame);
            StdDraw.line(frame, i, size-frame, i);
            i += a;
        }
        StdDraw.filledCircle(n/2 * a + frame, n/2 * a +frame, 0.12 * a);
        StdDraw.show();
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
        // picture
        StdDraw.picture(840, 100, "Picture.png", 200, 200);
        // total timer
        StdDraw.enableDoubleBuffering();
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 30));
        int t = 3600 * hour + 60 * minute + second;
        for (int j = t; j >= 0; j--) {
            StdDraw.setPenColor(Color.white);
            StdDraw.filledRectangle(745, 640, 60, 30);
            int s = j % 60;
            int m = (j / 60) % 60;
            int h = j / 3600;
            String time = String.format("%02d:%02d:%02d", h, m, s);
            StdDraw.setPenColor(145, 133, 255);
            StdDraw.textLeft(685, 640, time);
            StdDraw.show();
            StdDraw.pause(1000);
        }
        // turn timer
    }

//    public boolean showTotalTimer(int hour, int minute, int second) {
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setFont(new Font("Arial", Font.PLAIN, 30));
//        int t = 3600 * hour + 60 * minute + second;
//        for (int i = t; i >= 0; i--) {
//            StdDraw.setPenColor(Color.white);
//            StdDraw.filledRectangle(745, 640, 60,30);
//            int s = i % 60;
//            int m = (i / 60) % 60;
//            int h = i / 3600;
//            String time = String.format("%02d:%02d:%02d", h, m, s);
//            StdDraw.setPenColor(145, 133, 255);
//            StdDraw.textLeft(685, 640, time);
//            StdDraw.show();
//            if (i == 0) {
//                return true;
//            }
//            StdDraw.pause(1000);
//        }
//        return false;
//    }

//    public boolean showTurnTimer(int pause) {
//        StdDraw.setPenColor(194, 188, 255);
//        StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
//        StdDraw.textLeft(685, 560, "this turn");
//        StdDraw.setFont(new Font("Arial", Font.PLAIN, 60));
//        StdDraw.enableDoubleBuffering();
//        for (int i = pause; i >= 0; i--) {
//            StdDraw.setPenColor(Color.white);
//            StdDraw.filledRectangle(835, 560, 80,40);
//            int s = i % 60;
//            int m = (i / 60) % 60;
//            String limit = String.format("%02d:%02d", m, s);
//            StdDraw.setPenColor(194, 188, 255);
//            StdDraw.text(835, 560, limit);
//            StdDraw.show();
//            if (i == 0) {
//                return true;
//            }
//            StdDraw.pause(1000);
//        }
//        return false;
//    }

//    public void showScores(String score1, String score2) {
//        // squares
//        StdDraw.setPenColor(255, 235, 166);
//        StdDraw.filledSquare(735, 455, 50);
//        StdDraw.filledSquare(865, 455, 50);
//        // digits
//        StdDraw.setPenColor(255, 161, 84);
//        StdDraw.setFont(new Font("Arial", Font.PLAIN, 75));
//        StdDraw.text(735, 445, score1);
//        StdDraw.text(865, 445, score2);
//        // "VS"
//        StdDraw.setFont(new Font("Arial", Font.PLAIN, 35));
//        StdDraw.text(800, 435, "VS");
//    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = StdDraw.mouseX();
        y = StdDraw.mouseY();
        StdDraw.pause(100);

        if (frame <= x && x <=  size - frame && frame <= y && y <= size - frame) {
            if (allChess[h][v] == 0 && !isForbiddenMove()) {
                UndoPosition undoPosition = new UndoPosition(h, v);
                undoPositions[totalCount] = undoPosition;
                totalCount++;
                if (isBlack) {
                    allChess[h][v] = 1;
                    isBlack = false;
                    message = "Player2' turn";
                } else {
                    allChess[h][v] = 2;
                    isBlack = true;
                    message = "Player1's turn";
                }
                if (this.checkWin()) {
                    if (allChess[h][v] == 1) {
                        canPlay = false;
                    }
                    if (allChess[h][v] == 2) {
                        canPlay = false;
                    }
                }
            }
            else if (allChess[h][v] == 0 && isForbiddenMove()) {
                JOptionPane.showMessageDialog(null,"A 33 forbidden move!");
            }
            else {
                JOptionPane.showMessageDialog(null, "There is already a chess! ");
            }
            drawPieces();
        }
//        double x = e.getX();
//        double y = e.getY();
//        // Stop
//        if ( 720 < x && x < 880 && 270 < y && y < 300) {
//            Stop stop = new Stop();
//            stop.ask();
//        }
//        // Undo
//        if (720 < x && x < 880 && 320 < y && y < 380) {
//            Undo();
//        }
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

//class Stop {
//    JFrame frame = new JFrame();
//    JLabel label = new JLabel();
//    JButton yes = new JButton("Yes");
//    JButton no = new JButton("No");
//
//    public void ask() {
//        label.setText("Do you wanner save your game?");
//        frame.add(label);
//        frame.add(yes);
//        frame.add(no);
//        frame.setVisible(true);
//
//        this.frame.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowOpened(WindowEvent e) {
//                super.windowOpened(e);
//            }
//        });
//        yes.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Save save = new Save();
//                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//                //
//            }
//        });
//        no.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//                //
//            }
//        });
//    }
//}
//
//class Save {
//    Play board;
//    String name;
//    Frame frame = new JFrame();
//    JTextField text = new JTextField(20);
//    JLabel label = new JLabel();
//    JButton ok = new JButton();
//
//    public String getName() {
//        return name;
//    }
//
//    public void creatName() {
//        label.setText("Creat a name to save: ");
//        frame.add(label);
//        frame.add(text);
//        frame.add(ok);
//        frame.setVisible(true);
//
//        ok.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                name = text.getText();
//                try (PrintWriter save = new PrintWriter(name);) {
//                    int[][] array = board.getAllChess();
//                    for (int[] i : array) {
//                        for (int j : i) {
//                            save.print(j + " ");
//                        }
//                    }
//                }catch (FileNotFoundException ex) {
//                    System.out.println("fail to save");
//                }
//                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//            }
//        });
//    }
//}

class UndoPosition {
    int pX, pY;
    public UndoPosition(int pX, int pY) {
        this.pX = pX;
        this.pY = pY;
    }
}
