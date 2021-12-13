import edu.princeton.cs.algs4.StdDraw;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Pieces extends Board implements MouseListener, Runnable{
    double size, frame, a;
    Board board;
    Settings settings;
    int n;
    int x,y;
    int[][] allChess;
    boolean isBlack = true;
    boolean canPlay = true;
    int maxTime, blackTime, whiteTime;
    String message, blackMessage, whiteMessage;

    public Pieces() {
        super();
        size = 700.0;
        frame = 40;
        a = (size - 2*frame) / n;
        n = board.getN();
        allChess = new int[n + 2][n + 2];
        x = 0;
        y = 0;
        message = "Player1' turn";
    }

    public void drawPieces() {
        for(int i = 0; i < n ; i++){
            for(int j = 0; j < n ; j++){
                double tempX = frame + a * i;
                double tempY = frame + a * j;
                if (allChess[i][j] == 1) {
                    switch (settings.getPlayer1()) {
                        case 1:
                            StdDraw.setPenColor(Color.BLACK);
                        case 2 :
                            StdDraw.setPenColor(Color.BLACK);
                        default:
                            StdDraw.setPenColor(Color.BLACK);
                    }
                    StdDraw.filledCircle(tempX, tempY, 0.3 * a);
                }
                if (allChess[i][j] == 2) {
                    switch (settings.getPlayer1()) {
                        case 1:
                            StdDraw.setPenColor(Color.YELLOW);
                        case 2 :
                            StdDraw.setPenColor(Color.YELLOW);
                        default:
                            StdDraw.setPenColor(Color.YELLOW);
                    }
                    StdDraw.filledCircle(tempX, tempY, 0.3 * a);
                }
            }
        }
    }

    private int checkCount(int xChange, int yChange, int color){
        int count = 1;
        int tempX = xChange;
        int tempY = yChange;
        while(color == allChess[x + xChange][y + yChange] && x + xChange >=0 && x + xChange <= (n + 2) &&
                y + yChange >=0 && y + yChange <= (n + 2)){
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
        while(color == allChess[x - xChange][y - yChange]&& x - xChange >=0 && x - xChange <= (n + 2) &&
                y - yChange >=0 && y - yChange <= (n + 2)){
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
        int color = allChess[x][y];
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
            //横向
            int i = 1;
            while (color == allChess[x + i][y] && (x + i) < (n + 2)) {
                count1++;
                i++;
            }
            int j = 1;
            while (color == allChess[x - j][y] && (x - j) > 0 ) {
                count1++;
                j++;
            }
            if (count1 == 3 && allChess[x + i + 1][y] == 0 && allChess[x - j - 1][y] == 0) {
                countForbidden++;
            }
            //纵向
            i = 1;
            while (1 == allChess[x][y + i] && y + i < (n + 2)) {
                count2++;
                i++;
            }
            j = 1;
            while (color == allChess[x][y - j] && (y - j) > 0) {
                count2++;
                j++;
            }
            if (count2 == 3 && allChess[x][y+ i + 1] == 0 && allChess[x][y- j - 1] == 0) {
                countForbidden++;
            }
            //左斜
            i = 1;
            while (color == allChess[x + i][y + i] && x + i < (n + 2) && (y + i) < (n + 2)) {
                count3++;
                i++;
            }
            j = 1;
            while (color == allChess[x - j][y - j]&& (x - j) > 0 && (y - j) >0) {
                count3++;
                j++;
            }
            if (count3 == 3 && allChess[x + i + 1][y+ i + 1] == 0 && allChess[x -j - 1][y- j - 1] == 0) {
                countForbidden++;
            }
            //右斜
            i = 1;
            while (color == allChess[x - i][y + i] && (x - i) > 0 && (y + i) < (n + 2)) {
                count4++;
                i++;
            }
            j = 1;
            while (color == allChess[x + j][y - j] && (x + j) < (n + 2) &&  (y - j) > 0) {
                count4++;
                j++;
            }
            if (count4 == 3 && allChess[x -i - 1][y+ i + 1] == 0 && allChess[x +j + 1][y- j - 1] == 0) {
                countForbidden++;
            }

            if(countForbidden >= 2){
                F = true;
            }
        }
        return F;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (canPlay) {
            double x = e.getX();
            double y = e.getY();
            if (frame <= x && x <=  size - frame && frame <= y && y <= size - frame) {
                int h = (int)Math.round((x - frame) / a);
                int v = (int)Math.round((y - frame) / a);
                if (allChess[h][v] == 0 && !isForbiddenMove()) {
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
                            Win1 win1 = new Win1();
                            win1.showWin1();
                            canPlay = false;
                        }
                        if (allChess[h][v] == 2) {
                            Win2 win2 = new Win2();
                            win2.showWin2();
                            canPlay = false;
                        }
                    }
                }
                else if (allChess[h][v] == 0 && isForbiddenMove()) {
                    Forbidden forbidden = new Forbidden();
                    forbidden.showForbidden();
                }
                else {
                    Invalid invalid = new Invalid();
                    invalid.showInvalid();
                }
            }
        }
    }

    @Override
    public void run() {
        if (board.showTotalTimer(settings.getHour(), settings.getMinute(), settings.getSecond())) {
            TimerOver timerOver = new TimerOver();
            timerOver.showOver();
        }
        if (board.showTurnTimer(settings.getPause())) {
            if (isBlack) {
                Win2 win2 = new Win2();
                win2.showWin2();
                canPlay = false;
            }
            else {
                Win1 win1 = new Win1();
                win1.showWin1();
                canPlay = false;
            }
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

class Win1 {
    JFrame frame1 = new JFrame();
    JLabel label1 = new JLabel();
    public void showWin1() {
        label1.setText("Player1 wins!");
        frame1.add(label1);
        frame1.setVisible(true);
    }
}
class Win2 {
    JFrame frame2 = new JFrame();
    JLabel label2 = new JLabel();
    public void showWin2() {
        label2.setText("Player2 wins!");
        frame2.add(label2);
        frame2.setVisible(true);
    }
}
class Invalid {
    JFrame frame0 = new JFrame();
    JLabel label0 = new JLabel();
    public void showInvalid() {
        label0.setText("invalid movement");
        frame0.add(label0);
        frame0.setVisible(true);
    }
}
class Forbidden {
    JFrame frame0 = new JFrame();
    JLabel label0 = new JLabel();
    public void showForbidden() {
        label0.setText("33 forbidden movement");
        frame0.add(label0);
        frame0.setVisible(true);
    }
}
class TimerOver {
    JFrame frameOver = new JFrame();
    JLabel labelOver = new JLabel();
    public void showOver() {
        labelOver.setText("Time is up! Game over!");
        frameOver.add(labelOver);
        frameOver.setVisible(true);
    }
}