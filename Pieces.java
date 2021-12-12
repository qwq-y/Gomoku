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
        while(color == allChess[x + xChange][y + yChange] && x + xChange >=0 && x + xChange <= 18 &&
                y + yChange >=0 && y + yChange <= 18){
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
        while(color == allChess[x - xChange][y - yChange]&& x - xChange >=0 && x - xChange <= 18 &&
                y - yChange >=0 && y - yChange <= 18){
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

    @Override
    public void mousePressed(MouseEvent e) {
        if (canPlay) {
            double x = e.getX();
            double y = e.getY();
            if (frame <= x && x <= frame + size && frame <= y && y <= frame + size) {
                int h = (int)Math.round((x - frame) / a);
                int v = (int)Math.round((y - frame) / a);
                if (allChess[h][v] == 0) {
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
//                        JOptionPane.showMessageDialog(this, "Gamer over"
//                                + (allChess[h][v] == 1 ? "Player1" : "Player2") + "wins");
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
                } else {
                    Invalid invalid = new Invalid();
                    invalid.showInvalid();
//                    JOptionPane.showMessageDialog(this, "invalid movement");
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

//        if(maxTime > 0){
//            while(true){
//                if(isBlack){
//                    blackTime--;
//                    if(blackTime == 0){
//                        JOptionPane.showMessageDialog(this,"黑方超时，游戏结束！");
//                    }
//                }else{
//                    whiteTime--;
//                    if(whiteTime == 0){
//                        JOptionPane.showMessageDialog(this,"白方超时，游戏结束！");
//                    }
//                }
//                blackMessage = (blackTime / 3600) + ":" + ((blackTime / 60) -(blackTime / 3600)* 60)
//                        + ":" + (blackTime - (blackTime /60) * 60 );
//                whiteMessage = (whiteTime / 3600) + ":" + ((whiteTime / 60) -(whiteTime / 3600)* 60)
//                        + ":" + (whiteTime - (whiteTime /60) * 60 );
//                this.repaint();
//                try{
//                    Thread.sleep(1000);
//                }catch(InterruptedException ex){
//                    ex.printStackTrace();
//                }
//
//                System.out.println(blackTime + "-----" + whiteTime);
//            }
//        }
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
    class TimerOver {
        JFrame frameOver = new JFrame();
        JLabel labelOver = new JLabel();
        public void showOver() {
            labelOver.setText("Time is up! Game over!");
            frameOver.add(labelOver);
            frameOver.setVisible(true);
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
