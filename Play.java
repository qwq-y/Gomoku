import edu.princeton.cs.algs4.StdDraw;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

/*
棋盘边界下棋会数组越界
再次开始时玩家一二不会重新开始算
超时的时候谁赢
计时不会显示噢
人机傻傻的
选电脑先手会越界。。
点了一下 Settings 之后关不了了。。
 */

public class Play extends Component {
    int n;
    int minute, pause, totalPause;
    double frame, size, a;

    double x, y;
    int h, v;

    boolean isBlack, canPlay, pressed;

    String message;

    int[][] allChess;

    UndoPosition[] undoPositions;
    int totalCount;
    int undoCount1, undoCount2;

    long[] time;
    long totalTime;

    boolean playWithMe;

    public Play() {
        this(14, 30,  120);
    }

    public Play(int n, int minute, int pause) {
        this.n = n;
        this.minute = minute;
        this.pause = pause;
        totalPause = 60 * minute;

        frame = 40;
        size = 700;
        a = (size - 2 * frame) / n;

        allChess = new int[n + 2][n + 2];
        undoPositions = new UndoPosition[(n + 2) * (n + 2)];
        for (int i = 0; i < undoPositions.length; i++) {
            undoPositions[i] = new UndoPosition();
        }
        undoCount1 = 0;
        undoCount2 = 0;

        isBlack = true;
        canPlay = false;

        time = new long[(n + 2) * (n + 2)];
        totalTime = 0;

        playWithMe = false;
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
                    if (allChess[h][v] == 0 && isForbiddenMove() == false) {
                        UndoPosition undoPosition = new UndoPosition(h, v);
                        undoPositions[totalCount] = undoPosition;
                        totalCount++;

                        // record time
                        long currentLong = new Date().getTime();
                        time[totalCount - 1] = currentLong;
                        System.out.println(currentLong);

                        // judge time
                        if (totalCount > 1) {
                            long secondDiff = (time[totalCount - 1] - time[totalCount - 2]) / 1000;
                            System.out.println("diff: " + (time[totalCount - 1] - time[totalCount - 2]));
                            System.out.println("spent " + secondDiff + " seconds");
                            totalTime += secondDiff;
                            if (secondDiff > pause) {
                                System.out.println("turn time up: " + secondDiff);
                                canPlay = false;
                                if (isBlack) {
                                    JOptionPane.showMessageDialog(null, "Overtime, Player1 wins!");
                                }
                                else {
                                    JOptionPane.showMessageDialog(null, "Overtime, Player2 wins!");
                                }
                            }
                        }

                        if (totalTime > totalPause) {
                            System.out.println("total time up: " + totalTime);
                            canPlay = false;
                            JOptionPane.showMessageDialog(null, "Time is up! The game ends in a draw.");
                        }

                        // place pieces
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
                    if (playWithMe) {
                        computerPlay();
                    }
                }

                // click introduction
                else if (720 <= x && x <= 880 && 450 <= y && y <= 510) {
                    System.out.println("introduction");
                    String string = "balabalabala~";
                    JOptionPane.showMessageDialog(null, string, "", JOptionPane.PLAIN_MESSAGE);
                }

                // click play
                else if (720 <= x && x <= 880 && 370 <= y && y <= 430) {
                    System.out.println("play");
                    int play = JOptionPane.showConfirmDialog(null, "A new start?");
                    int withWho = JOptionPane.showConfirmDialog(null, "Play with a goofy computer?");

                    // new start
                    if (play == 0) {
                        if (withWho == 0) {
                            playWithMe = true;
                            int whoFirst = JOptionPane.showConfirmDialog(null, "You go first (player1)?");
                            if (whoFirst == 1) {
                                computerPlay();
                            }
                        }
                        else if (withWho == 1) {
                            playWithMe = false;
                        }

                        for (int i = 0; i < allChess.length; i++) {
                            for (int j = 0; j < allChess[i].length; j++) {
                                allChess[i][j] = 0;
                            }
                        }
                        StdDraw.clear();
                        showBoard();
                        drawPieces();
                        totalCount = 0;
                        totalTime = 0;
                        canPlay = true;
                    }

                    // load
                    if (play == 1) {
                        if (withWho == 0) {
                            playWithMe = true;
                        }
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
                else if (720 <= x && x <= 880 && 290 <= y && y <= 350) {
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
                else if (720 <= x && x <= 880 && 210 <= y && y <= 270) {
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
                else if (720 <= x && x <= 880 && 130 <= y && y <= 190) {
                    String boardSize = JOptionPane.showInputDialog("Input N to creat an NxN board (14 by default): ");
                    String minuteSetting = JOptionPane.showInputDialog("Input the total time limit in minutes (30 by default): ");
                    String pauseSetting = JOptionPane.showInputDialog("Input the time limit every turn in seconds (120 by default): ");

                    n = Integer.parseInt(boardSize);
                    allChess = new int[n + 2][n + 2];
                    a = (size - 2 * frame) / n;
                    minute = Integer.parseInt(minuteSetting);
                    pause = Integer.parseInt(pauseSetting);
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
                    StdDraw.setPenColor(255, 204, 102);
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

    public void computerPlay() {
        int comX, comY;
        do {
            do {
                comX = (int) (Math.random() * (n + 2));
                comY = (int) (Math.random() * (n + 2));
            } while (allChess[comX][comY] != 0);
        } while (isForbiddenMove() == true);
        System.out.println("computer: " + comX + " " + comY);

        totalCount++;
        long currentLong = new Date().getTime();
        time[totalCount - 1] = currentLong;

        if (isBlack) {
            allChess[comX][comY] = 1;
            isBlack = false;
            message = "Player2' turn";
        }
        else {
            allChess[comX][comY] = 2;
            isBlack = true;
            message = "Player1's turn";
        }

        UndoPosition undoPosition = new UndoPosition(comX, comY);
        undoPositions[totalCount] = undoPosition;
        drawPieces();
    }

    public void showBoard() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(940, 700);
        StdDraw.setXscale(0.0, 940.0);
        StdDraw.setYscale(0.0, 700.0);

        // picture
        StdDraw.picture(400, 350, "bg.jpg", 940, 700);
        StdDraw.picture(640, 625, "up.jpg", 530, 180);
        StdDraw.picture(100, 100, "4.jpg", 180, 180);

        // board
        StdDraw.setPenColor(175, 167, 255);
        double a = (size - 2 * frame) / n;
        double i = frame;
        while (i <= size - frame + 1) {
            StdDraw.line(i, frame, i, size - frame);
            StdDraw.line(frame, i, size - frame, i);
            i += a;
        }

        // "Introduction" button
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.filledRectangle(804, 476, 80, 30);
        StdDraw.setPenColor(201, 241, 255);
        StdDraw.filledRectangle(800, 480, 80, 30);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 25));
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.text(800, 475, "Introduction");

        // "Play" button
        StdDraw.setPenColor(255, 161, 84);
        StdDraw.filledRectangle(804, 396, 80, 30);
        StdDraw.setPenColor(255, 235, 166);
        StdDraw.filledRectangle(800, 400, 80, 30);
        StdDraw.setPenColor(255, 161, 84);
        StdDraw.text(800, 395, "Play");

        // "Undo" button
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.filledRectangle(804, 316, 80, 30);
        StdDraw.setPenColor(201, 241, 255);
        StdDraw.filledRectangle(800, 320, 80, 30);
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.text(800, 315, "Undo");

        // "Stop" button
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.filledRectangle(804, 236, 80, 30);
        StdDraw.setPenColor(255, 206, 245);
        StdDraw.filledRectangle(800, 240, 80, 30);
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.text(800, 235, "Stop");

        // "Settings" button
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.filledRectangle(804, 156, 80, 30);
        StdDraw.setPenColor(201, 241, 255);
        StdDraw.filledRectangle(800, 160, 80, 30);
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.text(800, 155, "Settings");

        StdDraw.show();
    }
}