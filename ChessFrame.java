import edu.princeton.cs.algs4.StdDraw;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChessFrame extends Component implements MouseListener ,Runnable {

    int n ;
    int temp = 100;

    //保存棋子坐标
    double x = 0, y = 0;
    int intX, intY;

    //保存之前下过棋子的坐标，0表示无子，1表示黑子，2表示白子
    int[][] allChess = new int[20][20];

    //标识下一个棋子的颜色
    boolean isBlack = true;

    //标识游戏是否可以继续
    boolean canPlay = true;

    //保存提示信息
    String message = " Black's Turn";

    //拥有时间
    int maxTime = 0;

    //做倒计时线程
    //对窗体进行线程操作
    Thread t = new Thread(this);
    //保存黑方和白方的时间
    int blackTime = 0;
    int whiteTime = 0;

    //保存双方剩余时间提示信息
    String blackMessage = "Unlimited";
    String whiteMessage = "Unlimited";


    public ChessFrame() {
        n = 15;
        StdDraw.enableDoubleBuffering();

        StdDraw.setCanvasSize(1000, 1000);
        StdDraw.setXscale(0, 1200);
        StdDraw.setYscale(0, 1200);
        StdDraw.setPenColor(Color.BLACK);
        for (int i = 0; i < n; i++) {
            StdDraw.line(i * 50 + 100, 100, i * 50 + 100, (n - 1) * 50 + 100);
            StdDraw.line(100, i * 50 + 100, (n - 1) * 50 + 100, i * 50 + 100);
        }


        StdDraw.show();
    }

    public ChessFrame(int n) {
        this.n = n;
        this.paint();
        t.start();
        t.suspend();


        while (true) {
            if (StdDraw.isMousePressed() ) {
                x = StdDraw.mouseX();
                y = StdDraw.mouseY();
                StdDraw.pause(100);


                //点击Play
                if(x >=350 && x <= 590 && y >=1060 && y <= 1180) {

                    this.play();

                }

                //点击Setting
                // "Settings"

                if(x > 660 && x < 900 && y > 1075 && y <1165){
                    this.setting();
                }
                //Introduction
                StdDraw.setPenColor(255, 161, 84);
                StdDraw.filledRectangle(175, 1125, 120, 45);
                StdDraw.setPenColor(255, 235, 166);
                StdDraw.filledRectangle(170, 1120, 120, 45);
                StdDraw.setFont(new Font("Arial", Font.PLAIN, 36));
                StdDraw.setPenColor(255, 161, 84);
                StdDraw.text(170, 1120, "Introduction");
                if (x >= 50 && x <= 290 && y >= 1080 && y <= 1165) {

                    this.introduct();
                }


                if(x >= temp && x <= (n-1) * 50 + temp && y >=temp &&  y <= (n-1)* 50 + temp &&canPlay == true){
                    intX = (int)Math.round(( x - temp)/50);
                    intY = (int)Math.round((y - temp)/50);
                    if(allChess[intX][intY] == 0 && isForbiddenMove() == false){
                        //判断当前是什么颜色
                        if(isBlack == true){
                            allChess[intX][intY] = 1;
                            isBlack = false;
                            message = " White's Turn";
                        }else{
                            allChess[intX][intY] = 2;
                            isBlack = true;
                            message = " Black's Turn";
                        }
                        //判断是否结束
                        if(this.checkWin()){
                            //结束
                            canPlay = false;
                            //
                        }
                    }else if(allChess[intX][intY] == 0 && isForbiddenMove() == true){
                        //禁手提示
                        JOptionPane.showMessageDialog(null,"A 33 forbidden move!");
                    }else{
                        //有棋子提示
                        JOptionPane.showMessageDialog(null, "There is already a chess! ");
                    }
                    //this.repaint();
                    paintChess();
                }


            }
        }
    }

    private void paint(){
        StdDraw.enableDoubleBuffering();

        //画布大小和坐标设置
        StdDraw.setCanvasSize(1000, 1000);
        StdDraw.setXscale(0, 1200);
        StdDraw.setYscale(0, 1200);
        StdDraw.setPenColor(Color.BLACK);

        //画棋盘

        if (n == 19) {
            temp = 50;
        }

        for (int i = 0; i < n; i++) {
            StdDraw.line(i * 50 + temp, temp, i * 50 + temp, (n - 1) * 50 + temp);
            StdDraw.line(temp, i * 50 + temp, (n - 1) * 50 + temp, i * 50 + temp);
        }

        //加入鼠标监听


        // "Play"
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.filledRectangle(475, 1125, 120, 60);
        StdDraw.setPenColor(255, 206, 245);
        StdDraw.filledRectangle(470, 1120, 120, 60);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 60));
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.text(470, 1120, "Play");

        // "Stop" button
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.filledRectangle(1075, 275, 80, 30);
        StdDraw.setPenColor(255, 206, 245);
        StdDraw.filledRectangle(1070, 270, 80, 30);
        StdDraw.setPenColor(255, 109, 197);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 25));
        StdDraw.text(1070, 270, "Stop");

        //Undo
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.filledRectangle(1075, 355, 80, 30);
        StdDraw.setPenColor(201, 241, 255);
        StdDraw.filledRectangle(1070, 350, 80, 30);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 25));
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.text(1070, 350, "Undo");


        // "Introduction"
        StdDraw.setPenColor(255, 161, 84);
        StdDraw.filledRectangle(175, 1125, 120, 45);
        StdDraw.setPenColor(255, 235, 166);
        StdDraw.filledRectangle(170, 1120, 120, 45);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 36));
        StdDraw.setPenColor(255, 161, 84);
        StdDraw.text(170, 1120, "Introduction");

        // "Settings"
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.filledRectangle(785, 1125, 120, 45);
        StdDraw.setPenColor(201, 241, 255);
        StdDraw.filledRectangle(780, 1120, 120, 45);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 36));
        StdDraw.setPenColor(0, 187, 255);
        StdDraw.text(780, 1120, "Settings");

        StdDraw.picture(1087, 950, "src/MyGomoku/Picture.png", 250, 250);
        //线程挂起

        StdDraw.show();
    }


    //按play按钮
    private void play(){

            int result = JOptionPane.showConfirmDialog(null,"Replay?");
            if(result == 0){
                StdDraw.enableDoubleBuffering();
                //重开
                        /*1.清空棋盘，allChess归零
                          2.游戏信息重置回到开始位置
                          3.isBlack改回true
                         */
                for(int i = 0; i < n; i++){
                    for( int j =0 ; j< n; j++){
                        allChess[i][j] = 0;
                    }
                }

                canPlay = true;
                message = "Black's Turn";
                isBlack = true;
                blackTime = maxTime;
                whiteTime = maxTime;
                if( maxTime > 0){
                    blackMessage = (maxTime / 3600) + ":" + ((maxTime / 60)-(maxTime / 3600) * 60)
                            + ":" + (maxTime - (maxTime / 60) * 60);
                    whiteMessage = (maxTime / 3600) + ":" + ((maxTime / 60)-(maxTime / 3600) * 60)
                            + ":" + (maxTime - (maxTime / 60) * 60);
                    t.resume();
                }else {
                    blackMessage = "Unlimited";
                    whiteMessage = "Unlimited";
                }
                StdDraw.clear();

                this.paint();



            }
        }


        //Setting
        private void setting(){
            String input = JOptionPane.showInputDialog("Please input the maximum time " +
                    "(Unit is minute and 0 means no limitation): ");
            String stringN = JOptionPane.showInputDialog("Please input the size of " +
                    "checkboard(15 ,17 or 19 is admitted)" + ": ");
            try{
                int numN = Integer.parseInt(stringN);
                setN( numN );
                maxTime = Integer.parseInt(input) * 60;
                if(maxTime < 0){
                    JOptionPane.showMessageDialog(null, "Please don't input negative");
                }
                if(maxTime == 0){
                    int result = JOptionPane.showConfirmDialog(null, "Setting complete!");
                    if(result == 0){
                        this.play();
                    }
                }
                if(maxTime > 0){
                    int result = JOptionPane.showConfirmDialog(null, "Setting complete!");
                    if(result == 0) {
                        this.play();
                    }
                }
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null,"Please input correct message!");
            }
        }

        //introduction
        private void introduct(){

        }





    private void paintChess () {
        StdDraw.enableDoubleBuffering();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (allChess[i][j] == 1) {
                    //画黑子(此处temp是边框）
                    int tempX = i * 50 + temp;
                    int tempY = j * 50 + temp;
                    StdDraw.setPenColor(Color.BLACK);
                    StdDraw.filledCircle(tempX, tempY, 25);
                } else if (allChess[i][j] == 2) {
                    //白子
                    int tempX = i * 50 + temp;
                    int tempY = j * 50 + temp;
                    StdDraw.setPenColor(Color.BLUE);
                    StdDraw.filledCircle(tempX, tempY, 25);
                }
            }
        }
        //StdDraw.pause(100);
        StdDraw.show();
    }


    public int getN () {
            return n;
    }
    //判断禁手
    private boolean isForbiddenMove(){
        boolean F = false;
        int count1 = 1;
        int count2 = 1;
        int count3 = 1;
        int count4 = 1;
        int countForbidden = 0;
        int color = 1;


        //横向
        int i = 1;
        while (color == allChess[intX + i][intY] && intX + i < n ) {
            count1++;
            i++;
        }
        int j = 1;
        while (color == allChess[intX - j][intY] && intX - j > 0 ) {
            count1++;
            j++;
        }
        if (count1 == 3 && allChess[intX + i + 1][intY] == 0 && allChess[intX - j - 1][intY] == 0) {
            countForbidden++;
        }

        //纵向

        i = 1;
        while (1 == allChess[intX][intY + i] && intY + i < n) {
            count2++;
            i++;
        }
        j = 1;
        while (color == allChess[intX][intY - j] && intY - j > 0) {
            count2++;
            j++;
        }
        if (count2 == 3 && allChess[intX ][intY + i + 1] == 0 && allChess[intX ][intY- j - 1] == 0) {
            countForbidden++;
        }
        //左斜

        i = 1;
        while (color == allChess[intX + i][intY + i] && intX  + i < n && intY + i <n) {
            count3++;
            i++;
        }
        j = 1;
        while (color == allChess[intX - j][intY - j]&& intX - j > 0 && intY - j >0) {
            count3++;
            j++;
        }
        if (count3 == 3 && allChess[intX + i + 1][intY+ i + 1] == 0 && allChess[intX -j - 1][intY- j - 1] == 0) {
            countForbidden++;
        }

        //右斜
        i = 1;
        while (color == allChess[intX - i][intY + i] && intX - i > 0 && intY + i < n) {
            count4++;
            i++;
        }
        j = 1;
        while (color == allChess[intX + j][intY - j] && intX + j < 18 &&  intY - j > 0) {
            count4++;
            j++;
        }
        if (count4 == 3 && allChess[intX -i - 1][intY+ i + 1] == 0 && allChess[intX +j + 1][intY- j - 1] == 0) {
            countForbidden++;
        }

        if(countForbidden >= 2){
            F = true;
        }
        return F;

    }

    //判断是否结束
    private boolean checkWin(){
        boolean flag = false;

        int color = allChess[intX][intY];
        //保存共有多少相同颜色的棋子
        int count = 1;


        //判断横向
        count = this.checkCount(1,0,color);
        if(count == 5){
            flag = true;
        }else{
            //判断纵向
            count = this.checkCount(0,1,color);
            if(count == 5){
                flag = true;
            }else{
                //判断右斜
                count = this.checkCount(1,-1,color);
                if(count == 5){
                    flag = true;
                }else{
                    //判断左斜
                    count = this.checkCount(1,1,color);
                    if(count == 5){
                        flag = true;
                    }
                }
            }
        }
        return flag;

    }


    private int checkCount(int a, int b, int color){
        int count = 1; //保存连接的数量
        int a0 = a, b0 = b;  //暂时存储a , b 的值
        while(color == allChess[intX + a][intY + b] && intX + a >= 0 && intX + a <=n
                && intY + b >=0 && intY + b <= n){
            count++;
            if(a != 0) {
                if (a != 0) {
                    a++;
                } else {
                    a--;
                }
            }
            if(b != 0){
                if( b >= 0){
                    b++;
                }else{
                    b--;
                }
            }
        }
        a = a0;
        b = b0;

        while(color == allChess[intX - a][intY - b] && intX - a >=0 && intX - a <=n
                && intY - b >=0&& intY -b <=n){
            count++;
            if(a != 0) {
                if (a != 0) {
                    a++;
                } else {
                    a--;
                }
            }
            if(b != 0){
                if( b >= 0){
                    b++;
                }else{
                    b--;
                }
            }
        }
        return count;

    }
    public void setN(int n){
        this.n= n;
    }


    @Override
    public void mouseClicked (MouseEvent e){

    }

    @Override
    public void mousePressed (MouseEvent e){

    }

    @Override
    public void mouseReleased (MouseEvent e){

    }

    @Override
    public void mouseEntered (MouseEvent e){

    }

    @Override
    public void mouseExited (MouseEvent e){

    }

    @Override
    public void run () {

    }
}


