import edu.princeton.cs.algs4.StdDraw;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
现在斜着赢不算
棋盘边界下棋会数组越界
计时系统
人机
 */
public class Play extends Component {
    int n;
    int hour, minute, second;
    double frame, size, a;
    double x, y;
    int h, v;
    boolean isBlack, canPlay, pressed;
    String message;
    int[][] allChess;
    UndoPosition[] undoPositions;
    int totalCount;
    int undoCount1, undoCount2;

    public Play() {
        this.n = 14;
        hour = 0;
        minute = 15;
        second = 0;
        frame = 40;
        size = 700;
        a = (size - 2 * frame) / n;
        // 0 represents null, 1 represents black, 2 represents white
        allChess = new int[n + 2][n + 2];
        undoPositions = new UndoPosition[(n + 2) * (n + 2)];
        for (int i = 0; i < undoPositions.length; i++) {
            undoPositions[i] = new UndoPosition();
        }
        isBlack = true;
        canPlay = false;
        totalCount = 0;
        undoCount1 = 0;
        undoCount2 = 0;
    }

    public Play(int n, int hour, int minute, int second) {
        this.n = n;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        frame = 40;
        size = 700;
        a = (size - 2 * frame) / n;
        allChess = new int[n + 2][n + 2];
        undoPositions = new UndoPosition[(n + 2) * (n + 2)];
        for (int i = 0; i < undoPositions.length; i++) {
            undoPositions[i] = new UndoPosition();
        }
        isBlack = true;
        canPlay = false;
        undoCount1 = 0;
        undoCount2 = 0;
    }

    public boolean getPressed() {
        return pressed;
    }

    public void run() {
        System.out.println("entered");
        showBoard();
        while (true) {
            if (StdDraw.isMousePressed()) {
                pressed = true;
                StdDraw.pause(500);
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                h = (int) Math.round((x - frame) / a);
                v = (int) Math.round((y - frame) / a);
                System.out.println("pressed1");
                // click chess
                if (frame <= x && x <=  size - frame && frame <= y && y <= size - frame && canPlay) {
//                if (0 <= x && x <= size && 0 <= y && y <= size && canPlay) {
                    if (allChess[h][v] == 0 && isForbiddenMove() == false) {
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
                        drawPieces();
//                        StdDraw.pause(100);
                        if (checkWin()) {
                            System.out.println();
                            if (allChess[h][v] == 1) {
                                canPlay = false;
                                JOptionPane.showMessageDialog(null, "Player1 wins!");
                            }
                            if (allChess[h][v] == 2) {
                                canPlay = false;
                                JOptionPane.showMessageDialog(null, "Player2 wins!");
                            }
                        }
                    } else if (allChess[h][v] == 0 && isForbiddenMove()) {
                        JOptionPane.showMessageDialog(null, "A 33 forbidden move!");
                    } else if (allChess[h][v] != 0) {
                        JOptionPane.showMessageDialog(null, "There is already a chess! ");
                    }
                }
                // click play
                else if (720 <= x && x <= 880 && 400 <= y && y <= 460) {
                    System.out.println("play");
                    int play = JOptionPane.showConfirmDialog(null, "A new start?");
                    // new start
                    if (play == 0) {
                        for (int i = 0; i < allChess.length; i++) {
                            for (int j = 0; j < allChess[i].length; j++) {
                                allChess[i][j] = 0;
                            }
                        }
                        StdDraw.clear();
                        showBoard();
                        drawPieces();
                        totalCount = 0;
                        canPlay = true;
                    }
                    // load
                    if (play == 1) {
                        // choose one
                        String loadName = JOptionPane.showInputDialog("Recall a pre-saved game to load:");
                        File file = new File(loadName + ".txt");
                        int count1 = 0;
                        int count2 = 0;
                        try {
                            // show pieces
                            Scanner scanner = new Scanner(file);
                            while (scanner.hasNext()) {
                                for (int i = 0; i < allChess.length; i++) {
                                    for (int j = 0; j < allChess[i].length; j++) {
                                        allChess[i][j] = scanner.nextInt();
                                        if (allChess[i][j] == 1) {
                                            count1++;
                                        }
                                        if (allChess[i][j] == 2) {
                                            count2++;
                                        }
                                    }
                                }
                            }
                            scanner.close();
                            drawPieces();
                            // decide whose turn
                            if (count1 > count2) {
                                isBlack = false;
                            }
                            canPlay = true;
                        } catch (FileNotFoundException e) {
                            System.out.println("FileNotFound");
                            e.printStackTrace();
                        }
                    }
                }
                // click undo
                else if (720 <= x && x <= 880 && 320 <= y && y <= 380) {
                    if (isBlack) {
                        undoCount2++;
                        isBlack = false;
                    }
                    else if (isBlack == false) {
                        undoCount1++;
                        isBlack = true;
                    }
                    // should be less than 3 times each side
                    if ((isBlack == false && undoCount2 <= 3) || (isBlack && undoCount1 <= 3)) {
                        System.out.println("undo1");
                        int xPosition = undoPositions[totalCount - 1].pX;
                        int yPosition = undoPositions[totalCount - 1].pY;
//                    System.out.println(xPosition + " " + yPosition);
                        allChess[xPosition][yPosition] = 0;
                        totalCount--;
                        StdDraw.enableDoubleBuffering();
                        StdDraw.clear();
                        showBoard();
                        drawPieces();
                        System.out.println("undo2");
                        StdDraw.pause(300);
                    }
                    else if ((isBlack == false && undoCount2 > 3) || (isBlack && undoCount1 > 3)) {
                        JOptionPane.showMessageDialog(null, "You have already undone three times!");
                    }
                }
                // click stop
                else if (720 <= x && x <= 880 && 240 <= y && y <= 300) {
                    System.out.println("stop");
                    int stop = JOptionPane.showConfirmDialog(null, "Wanner save this game for the next time?");
                    // save
                    if (stop == 0) {
                        System.out.println("save");
                        String saveName = JOptionPane.showInputDialog("Create a name to save: ");
                        try (PrintWriter save = new PrintWriter(saveName + ".txt");) {
                            for (int[] i : allChess) {
                                for (int j : i) {
                                    save.print(j + " ");
                                }
                            }
                        } catch (FileNotFoundException ex) {
                            System.out.println("fail to save");
                        }
                        canPlay = false;
                    }
                    // unsave
                    if (stop == 1) {
                        System.out.println("unsave");
                        canPlay = false;
                    }
                }
                // click settings
                else if (720 <= x && x <= 880 && 160 <= y && y <= 220) {
                    String boardSize = JOptionPane.showInputDialog("Input N to creat an NxN board: ");
                    n = Integer.parseInt(boardSize);
                    allChess = new int[n + 2][n + 2];
                    a = (size - 2 * frame) / n;
                    undoPositions = new UndoPosition[(n + 2) * (n + 2)];
                    isBlack = true;
                    showBoard();
                }
                // other click
                else {
                    System.out.println("NoAnswer~");
                }
            }
        }
    }

    public int[][] getAllChess() {
        return allChess;
    }

    public void drawPieces() {
        System.out.println("pieces!");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double tempX = frame + a * i;
                double tempY = frame + a * j;
                if (allChess[i][j] == 1) {
                    StdDraw.setPenColor(Color.CYAN);
                    StdDraw.filledCircle(tempX, tempY, 0.3 * a);
                }
                if (allChess[i][j] == 2) {
                    StdDraw.setPenColor(Color.ORANGE);
                    StdDraw.filledCircle(tempX, tempY, 0.3 * a);
                }
            }
        }
        StdDraw.show();
    }


    private int checkCount(int xChange, int yChange, int color) {
        int count = 1;
        int tempX = xChange;
        int tempY = yChange;

        while (color == allChess[h + xChange][v + yChange] && (h + xChange) >= 0 && (h + xChange) <= (n + 2) &&
                (v + yChange) >= 0 && (v + yChange) <= (n + 2)) {
            count++;
            if (xChange != 0) {
                if (xChange > 0) {
                    xChange++;
                } else {
                    xChange--;
                }
            }
            if (yChange != 0) {
                if (yChange > 0) {
                    yChange++;
                } else {
                    yChange--;
                }
            }
        }
        xChange = tempX;
        yChange = tempY;
        while (color == allChess[h - xChange][v - yChange] && (h - xChange) >= 0 && (h - xChange) <= (n + 2) &&
                (v - yChange) >= 0 && (v - yChange) <= (n + 2)) {
            count++;
            if (xChange != 0) {
                if (xChange > 0) {
                    xChange++;
                } else {
                    xChange--;
                }
            }
            if (yChange != 0) {
                if (yChange > 0) {
                    yChange++;
                } else {
                    yChange--;
                }
            }
        }
        return count;
    }

    private boolean checkWin() {
        TurnTimer turnT = new TurnTimer();
        TotalTimer totalT = new TotalTimer();
        boolean flag = false;
        int count = 1;
        int color = allChess[h][v];
        // five pieces
        count = this.checkCount(1, 0, color);
        if (count == 5) {
            flag = true;
        } else {
            count = this.checkCount(0, 1, color);
            if (count == 5) {
                flag = true;
            } else {
                count = this.checkCount(1, -1, color);
                if (count == 5) {
                    flag = true;
                } else {
                    count = this.checkCount(1, 1, color);
                    if (count == 5) {
                        flag = true;
                    }
                }
            }
        }
        // total time up
        if (totalT.getTotal()) {
            flag = true;
        }
        // turn time up
        if (turnT.getTurn()) {
            flag = true;
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
            while (color == allChess[h - j][v] && (h - j) > 0) {
                count1++;
                j++;
            }
            if (count1 >= 3 && allChess[h + i + 1][v] == 0 && allChess[h - j - 1][v] == 0) {
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
            if (count2 >= 3 && allChess[h][v + i + 1] == 0 && allChess[h][v - j - 1] == 0) {
                countForbidden++;
            }
            // upper right
            i = 1;
            while (color == allChess[h + i][v + i] && (h + i) < (n + 2) && (v + i) < (n + 2)) {
                count3++;
                i++;
            }
            j = 1;
            while (color == allChess[h - j][v - j] && (h - j) > 0 && (v - j) > 0) {
                count3++;
                j++;
            }
            if (count3 >= 3 && allChess[h + i + 1][v + i + 1] == 0 && allChess[h - j - 1][v - j - 1] == 0) {
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
            if (count4 >= 3 && allChess[h - i - 1][v + i + 1] == 0 && allChess[h + j + 1][v - j - 1] == 0) {
                countForbidden++;
            }

            if (countForbidden >= 2) {
                F = true;
            }
        }
        return F;
    }

    public void showBoard() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(940, 700);
        StdDraw.setXscale(0.0, 940.0);
        StdDraw.setYscale(0.0, 700.0);
        // board
        StdDraw.setPenColor(175, 167, 255);
        double a = (size - 2 * frame) / n;
        double i = frame;
        while (i <= size - frame + 1) {
            StdDraw.line(i, frame, i, size - frame);
            StdDraw.line(frame, i, size - frame, i);
            i += a;
        }
//        StdDraw.filledCircle(n / 2 * a + frame, n / 2 * a + frame, 0.12 * a);
        StdDraw.show();
        // "Play" button
        StdDraw.setPenColor(255, 161, 84);
        StdDraw.filledRectangle(804, 426, 80, 30);
        StdDraw.setPenColor(255, 235, 166);
        StdDraw.filledRectangle(800, 430, 80, 30);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 25));
        StdDraw.setPenColor(255, 161, 84);
        StdDraw.text(800, 425, "Play");
        // "Undo" button
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.filledRectangle(804, 346, 80, 30);
        StdDraw.setPenColor(201, 241, 255);
        StdDraw.filledRectangle(800, 350, 80, 30);
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.text(800, 345, "Undo");
        // "Stop" button
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.filledRectangle(804, 266, 80, 30);
        StdDraw.setPenColor(255, 206, 245);
        StdDraw.filledRectangle(800, 270, 80, 30);
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.text(800, 265, "Stop");
        // "Settings" button
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.filledRectangle(804, 186, 80, 30);
        StdDraw.setPenColor(201, 241, 255);
        StdDraw.filledRectangle(800, 190, 80, 30);
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.text(800, 185, "Settings");
        // picture
        //StdDraw.picture(840, 100, "Picture.png", 200, 200);
    }
}
