import javax.swing.JOptionPane;

public class Test {
    public static void main(String[] args){
        //MyChessFrame mf = new MyChessFrame();
        //JOptionPane.showMessageDialog(mf, "我的信息");
        /**
        int result = JOptionPane.showConfirmDialog(mf,"我的确认信息，现在开始游戏吗？");
        System.out.println("选择结果为：" + result);
        if(result == 0){
            JOptionPane.showMessageDialog(mf,"游戏开始");
        }
        else if (result == 1){
            JOptionPane.showMessageDialog(mf,"游戏结束");
        }
        else{
            JOptionPane.showMessageDialog(mf,"请从新选择");
        }
        String username = JOptionPane.showInputDialog("请输入你的姓名：");
        if(username != null) {
            System.out.println(username);
            JOptionPane.showMessageDialog(mf, "输入的姓名为：");
        }else{
            JOptionPane.showMessageDialog("请重新输入你的姓名！");
        }*/


        GomokuFrame gf = new GomokuFrame();
    }
}


