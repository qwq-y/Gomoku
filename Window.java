import edu.princeton.cs.algs4.StdDraw;
import javax.swing.*;
import java.awt.event.MouseEvent;

public class Window {
    JFrame frame = new JFrame();

    public void showWindow() {
//        frame.addMouseListener(this);
        StdDraw.setCanvasSize(940,700);
        StdDraw.setXscale(0.0, 940.0);
        StdDraw.setYscale(0.0, 700.0);
    }

//    public void mouseClicked(MouseEvent e) {
//        System.out.println("mouseClicked");
//    }
//    public void mouseEntered(MouseEvent e) {
//        System.out.println("mouseEntered");
//    }
//    public void mouseExited(MouseEvent e) {
//        System.out.println("mouseExited");
//    }
//    public void mousePressed(MouseEvent e) {
//        System.out.println("mousePressed");
//    }
//    public void mouseReleased(MouseEvent e) {
//        System.out.println("mouseReleased");
//    }
}
