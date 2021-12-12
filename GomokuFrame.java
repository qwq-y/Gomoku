import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GomokuFrame extends JFrame implements MouseListener, Runnable{
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    //背景图片
    BufferedImage bgImage = null;

    //保存棋子坐标
    int x = 0;
    int y = 0;

    //保存之前下过的所有棋子的坐标,
    // 其中0表示没有棋子。
    //1表示黑子， 2表示白子。
    int[][] allChess= new int[19][19];

    //标识下一个棋子颜色
    boolean isBlack = true;

    //标识当前游戏是否可以继续
    boolean canPlay = true;

    //保存显示的提示信息
    String message = "黑方先行";

    //保存最多拥有多少时间（秒）
    int maxTime = 0;
    //做倒计时线程类
    Thread t = new Thread(this);  //对当前窗体进行线程操作
    //保存黑方与白方的剩余时间
    int blackTime = 0;
    int whiteTime = 0;

    //保存双方剩余时间的显示信息
    String blackMessage = "无限制";
    String whiteMessage = "无限制";

    public GomokuFrame(){
        //设置标题
        this.setTitle("Gomoku");
        //设置窗体大小
        this.setSize(500,500);
        //设置窗体出现位置
        this.setLocation((width - 500) / 2, (height - 500) / 2);
        //设置窗体大小不可改变
        this.setResizable(false);
        //将窗体的关闭方式设置为默认关闭后程序结束
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //加入监听器械
        this.addMouseListener(this);

        //将窗体显示出来

        t.start();      // 线程挂起
        t.suspend();

        //刷新屏幕，防止游戏时出现无法显示的情况
        this.repaint();

        try {
            bgImage = ImageIO.read(new File("C:/water.jpg"));
        }catch(IOException e){
            e.printStackTrace();


        }
        this.setVisible(true);



    }

    public void paint(Graphics g){
        //双缓冲技术，防止屏幕闪烁
        BufferedImage bi = new BufferedImage(500,500,BufferedImage.TYPE_INT_ARGB);
        Graphics g2= bi.createGraphics();

        //绘制背景
        g2.drawImage(bgImage,3,20, this);
        //输出标题信息
        g2.setFont(new Font("黑体",Font.BOLD,20));
        g2.drawString("游戏信息：" + message,120,60);
        //输出时间信息
        g2.setFont(new Font("宋体",0,12));
        g2.drawString("黑方时间：" + blackMessage,30,470);
        g2.drawString("白方时间：" + whiteMessage,260,470);

        //绘制棋盘
        for(int i = 0; i < 19; i++ ) {
            g2.drawLine(10, 70 + 20 * i, 370, 70 + 20 * i);
            g2.drawLine(10 + 20 * i, 70, 10 + 20 * i, 430);
        }

        //标注点类
        g2.fillOval(68,128,4,4);
        g2.fillOval(308,128,4,4);
        g2.fillOval(308,368,4,4);
        g2.fillOval(68,368,4,4);
        g2.fillOval(308,248,4,4);
        g2.fillOval(188,128,4,4);
        g2.fillOval(68,248,4,4);
        g2.fillOval(188,368,4,4);
        g2.fillOval(188,248,4,4);

        //绘制按钮
        g2.setColor(Color.PINK);
        g2.fillRect(390,70,80,30);
        g2.fillRect(390, 130,80,30);
        g2.fillRect(390,190,80,30);
        g2.fillRect(390,250,80,30);
        g2.fillRect(390,310,80,30);
        g2.fillRect(390,370,80,30);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("黑体",0,20));
        g2.drawString("Start",400,90);
        g2.drawString("set",410,150);
        g2.drawString("说 明",400,210);
        g2.drawString("认 输",400,270);
        g2.drawString("关 于",400,330);
        g2.drawString("退 出",400,390);

        //绘制棋子
        //g2.fillOval(x-5,y-5,10,10);
        for(int i = 0; i < 19 ; i++){
            for(int j = 0; j < 19 ; j++){
                if(allChess[i][j] == 1){
                    //黑子
                    int tempX = i * 20 + 10;
                    int tempY = j * 20 + 70;
                    g2.setColor(Color.BLACK);
                    g2.fillOval(tempX - 7, tempY - 7, 14, 14);

                }else if( allChess[i][j] == 2){
                    //白子
                    int tempX = i * 20 + 10;
                    int tempY = j * 20 + 70;
                    g2.setColor(Color.YELLOW);
                    g2.fillOval(tempX - 7, tempY - 7, 14, 14);
                }
            }
        }
        g.drawImage(bi,0,0,this);
    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        /*
        System.out.println(e.getX());
        System.out.println(e.getY());*/
        if(canPlay == true) {
            x = e.getX();
            y = e.getY();
            if (x > 10 && x < 370 && y > 70 && y < 430) {
                //System.out.println("在棋盘范围");
                x = (x - 10) / 20;   //离那里更近
                y = (y - 70) / 20;   //离那里更近
                if (allChess[x][y] == 0) {
                    //判断当前下什么颜色棋子
                    if (isBlack == true) {
                        allChess[x][y] = 1;
                        isBlack = false;
                        message = "轮到白方";
                    } else {
                        allChess[x][y] = 2;
                        isBlack = true;
                        message = "轮到黑方";
                    }
                    //判断棋子是否和其他棋子连成五个，即判断游戏是否结束
                    if (this.checkWin()) {
                        JOptionPane.showMessageDialog(this, "游戏结束"
                                + (allChess[x][y] == 1 ? "黑方" : "白方") + "获胜");
                        canPlay = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "当前位置已经有棋子，请重新落子");
                }


                this.repaint();

            }
        }
        //System.out.println(e.getX() + "-----" + e.getY());

        //点击开始游戏按钮
        if(e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 70 && e.getY() <= 100){
            int result = JOptionPane.showConfirmDialog(this, "是否重新开始游戏");
            if( result == 0){
                //现在从新开始游戏
                /*重开需要的操作：1.清空棋盘，allChess数据归零。
                               2. 游戏信息显示重置显示改回到开始位置
                               3.isBlack改回true
                 */
                for(int i = 0; i < 19; i++){
                    for( int j = 0; j < 19 ; j++){
                        allChess[i][j] = 0;
                    }
                }
                //另一种allChess[19][19] = 0;

                message = "黑方先行";
                isBlack = true;
                blackTime = maxTime;
                whiteTime = maxTime;
                if(maxTime > 0) {
                    blackMessage = (maxTime / 3600) + ":" + ((maxTime / 60) - (maxTime / 3600) * 60)
                            + ":" + (maxTime - (maxTime / 60) * 60);
                    whiteMessage = (maxTime / 3600) + ":" + ((maxTime / 60) - (maxTime / 3600) * 60)
                            + ":" + (maxTime - (maxTime / 60) * 60);
                    t.resume();   //启动线程
                }else{
                    blackMessage = "无限制";
                    whiteMessage = "无限制";
                }
                this.repaint();//重新绘制窗体
            }
        }

        //点击游戏设置按钮
        if(e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 130 && e.getY() <= 160){
            String input = JOptionPane.showInputDialog("请输入游戏的最大时间（分钟），如果输入0，表示没有时间限制: ");
            try {
                maxTime = Integer.parseInt(input) * 60;
                if(maxTime < 0){
                    JOptionPane.showMessageDialog(this,"请勿输入负数");
                }
                if(maxTime == 0){
                    int result = JOptionPane.showConfirmDialog(this,"设置完成");
                    if(result == 0){
                        for(int i = 0; i < 19; i++){
                            for( int j = 0; j < 19 ; j++){
                                allChess[i][j] = 0;
                            }
                        }
                        //另一种allChess[19][19] = 0;

                        message = "黑方先行";
                        isBlack = true;
                        blackTime = maxTime;
                        whiteTime = maxTime;
                        blackMessage = "无限制";
                        whiteMessage = "无限制";
                        this.repaint();//重新绘制窗体
                    }
                }
                if(maxTime > 0){
                    int result = JOptionPane.showConfirmDialog(this,"设置完成");
                    if(result == 0){
                        for(int i = 0; i < 19; i++){
                            for( int j = 0; j < 19 ; j++){
                                allChess[i][j] = 0;
                            }
                        }
                        //另一种allChess[19][19] = 0;

                        message = "黑方先行";
                        isBlack = true;
                        blackTime = maxTime;
                        whiteTime = maxTime;
                        blackMessage = (maxTime / 3600) + ":" + ((maxTime / 60) -(maxTime / 3600)* 60)
                                 + ":" + (maxTime - (maxTime /60) * 60 );
                        whiteMessage = (maxTime / 3600) + ":" + ((maxTime / 60) -(maxTime / 3600)* 60)
                                + ":" + (maxTime - (maxTime /60) * 60 );

                        t.resume();
                        this.repaint();//重新绘制窗体
                    }

                }
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(this,"请正确输入信息");
            }
        }
        //游戏说明按钮
        if(e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 190 && e.getY() <= 220){
            JOptionPane.showMessageDialog(this, "游戏说明");

        }
        if(e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 250 && e.getY() <= 280){
            int result = JOptionPane.showConfirmDialog(this,"是否确认认输？");
            if(result == 0){
                if(isBlack) {
                    JOptionPane.showMessageDialog(this, "黑方认输，游戏结束");
                }else{
                    JOptionPane.showMessageDialog(this, "白方认输，游戏结束");
                }
                canPlay = false;
            }
        }
        if(e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 310 && e.getY() <= 340){
            JOptionPane.showMessageDialog(this, "关于");
        }
        if(e.getX() >= 400 && e.getX() <= 470 && e.getY() >= 370 && e.getY() <= 400){
            JOptionPane.showMessageDialog(this, "游戏结束");
            System.exit(0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    private boolean checkWin(){
        boolean flag = false;
        //保存共有多少相同颜色的棋子相连
        int count = 1;
        //判断横向是否有五个棋子相连，特点：y轴坐标相同，即allChess[x][y]的y相同
        int color = allChess[x][y];
        /*if(color == allChess[x+1][y]){
            count ++;
            if( color == allChess[x+2][y]){
                count ++;
                if(color == allChess[x + 3][y]){
                    count++;
                }
            }
        }*/
        //需要通过循环做判断
        /**
        //横向
        int i = 1;
        while(color == allChess[x + i][y]){
            count++;
            i++;
        }
        i = 1;
        while(color == allChess[x -i][y]){
            count++;
            i++;
        }
        if(count == 5){
            flag = true;
        }

        //纵向
        int i2 = 1;
        int count2 = 1;
        while(color == allChess[x ][y + i2]){
            count2++;
            i2++;
        }
        i2 = 1;
        while(color == allChess[x ][y- i2]){
            count2++;
            i2++;
        }
        if(count2== 5){
            flag = true;
        }

        //右斜向
        int i3 = 1;
        int count3 = 1;
        while(color == allChess[x + i3 ][y - i3]){
            count3++;
            i3++;
        }
        i3 = 1;
        while(color == allChess[x - i3 ][y+ i3]){
            count3++;
            i3++;
        }
        if(count3== 5){
            flag = true;
        }

        //左斜向
        int i4 = 1;
        int count4 = 1;
        while(color == allChess[x + i4][y + i4]){
            count4++;
            i4++;
        }
        i4 = 1;
        while(color == allChess[x - i4][y - i4]){
            count4++;
            i4++;
        }
        if(count4 == 5){
            flag = true;
        }
        */


        //判断横向
        count = this.checkCount(1,0,color);
        if(count == 5){
            flag = true;
        }else{
            //判断纵向
            count = this.checkCount(0,1,color);
            if(count == 5) {
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








    private int checkCount(int xChange, int yChange, int color){
        int count = 1;//保存连接的数量
        int tempX = xChange, tempY = yChange;
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
        yChange = tempY;//返回xChange,yChange

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

    @Override
    public void run() {
        //判断是否有时间限制
        if(maxTime > 0){
            while(true){
                if(isBlack){
                    blackTime--;
                    if(blackTime == 0){
                        JOptionPane.showMessageDialog(this,"黑方超时，游戏结束！");
                    }
                }else{
                    whiteTime--;
                    if(whiteTime == 0){
                        JOptionPane.showMessageDialog(this,"白方超时，游戏结束！");
                    }
                }
                blackMessage = (blackTime / 3600) + ":" + ((blackTime / 60) -(blackTime / 3600)* 60)
                        + ":" + (blackTime - (blackTime /60) * 60 );
                whiteMessage = (whiteTime / 3600) + ":" + ((whiteTime / 60) -(whiteTime / 3600)* 60)
                        + ":" + (whiteTime - (whiteTime /60) * 60 );
                this.repaint();
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }

                System.out.println(blackTime + "-----" + whiteTime);
            }
        }
    }
}
