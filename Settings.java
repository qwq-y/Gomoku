import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Settings {
    private int n, player1, player2, hour, minute, second, pause;
    private JFrame frame;
    private Box vBox;
    private JRadioButton button1_1, button1_2, button1_3;
    private JPanel boardPanel;
    private JRadioButton button2_1, button2_2, button2_3;
    private JRadioButton button3_1, button3_2, button3_3;
    private ButtonGroup boardGroup, colorGroup_1, colorGroup_2;
    private JPanel colorPanel, colorPanel_1, colorPanel_2;
    private JTextField totalHour, totalMinute, totalSecond;
    private JTextField turnSecond;
    private JPanel timePanel, timePanel_1, timePanel_2;
    private JButton cancel, ok;
    private JPanel confirmPanel;

    public Settings() {
        n = 14;
        player1 = 0;
        player2 = 0;
        hour = 0;
        minute = 30;
        second = 0;
        pause = 60;
        frame = new JFrame("Settings");
        vBox = Box.createVerticalBox();
        button1_1 = new JRadioButton("15 x 15");
        button1_2 = new JRadioButton("17 x 17");
        button1_3 = new JRadioButton("19 x 19");
        boardPanel = new JPanel();
        button2_1 = new JRadioButton("black");
        button2_2 = new JRadioButton("blue");
        button2_3 = new JRadioButton("green");
        button3_1 = new JRadioButton("white");
        button3_2 = new JRadioButton("pink");
        button3_3 = new JRadioButton("yellow");
        boardGroup = new ButtonGroup();
        colorGroup_1 = new ButtonGroup();
        colorGroup_2 = new ButtonGroup();
        colorPanel = new JPanel();
        colorPanel_1 = new JPanel();
        colorPanel_2 = new JPanel();
        totalHour = new JTextField(3);
        totalMinute = new JTextField(3);
        totalSecond = new JTextField(3);
        turnSecond = new JTextField(5);
        timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timePanel_1 = new JPanel();
        timePanel_2 = new JPanel();
        cancel = new JButton("Cancel");
        ok = new JButton("Ok");
        confirmPanel = new JPanel();
    }

    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    public int getSecond() {
        return second;
    }
    public int getPause() {
        return pause;
    }

    public void settingsWindow() {
        // "board size"
        boardPanel.setBorder(BorderFactory.createTitledBorder("board size: "));
        boardGroup.add(button1_1);
        boardGroup.add(button1_2);
        boardGroup.add(button1_3);
        boardPanel.add(button1_1);
        boardPanel.add(button1_2);
        boardPanel.add(button1_3);
        boardPanel.setPreferredSize(new Dimension(280, 100));
        // "time limit"
        timePanel.setBorder(BorderFactory.createTitledBorder("time: "));
        timePanel_1.setBorder(BorderFactory.createTitledBorder("total time"));
        timePanel_1.add(new JLabel("hour"));
        timePanel_1.add(totalHour);
        timePanel_1.add(new JLabel("minute"));
        timePanel_1.add(totalMinute);
        timePanel_1.add(new JLabel("second"));
        timePanel_1.add(totalSecond);
        timePanel_2.setBorder(BorderFactory.createTitledBorder("every turn"));
        timePanel_2.add(new JLabel("second"));
        timePanel_2.add(turnSecond);
        timePanel.add(timePanel_1);
        timePanel.add(timePanel_2);
        timePanel.setPreferredSize(new Dimension(280, 250));
        // "pieces colors"
        colorPanel.setBorder(BorderFactory.createTitledBorder("pieces colors: "));
        colorPanel_1.setBorder(BorderFactory.createTitledBorder("player 1"));
        colorPanel_1.setLayout(new GridLayout(3,1));
        colorGroup_1.add(button2_1);
        colorGroup_1.add(button2_2);
        colorGroup_1.add(button2_3);
        colorPanel_1.add(button2_1);
        colorPanel_1.add(button2_2);
        colorPanel_1.add(button2_3);
        colorPanel_2.setBorder(BorderFactory.createTitledBorder("player 2"));
        colorPanel_2.setLayout(new GridLayout(3,1));
        colorGroup_2.add(button3_1);
        colorGroup_2.add(button3_2);
        colorGroup_2.add(button3_3);
        colorPanel_2.add(button3_1);
        colorPanel_2.add(button3_2);
        colorPanel_2.add(button3_3);
        colorPanel.add(colorPanel_1);
        colorPanel.add(colorPanel_2);
        confirmPanel.setPreferredSize(new Dimension(280, 300));
        // "confirm"
        confirmPanel.add(cancel);
        confirmPanel.add(ok);
        confirmPanel.setPreferredSize(new Dimension(280, 100));
        // add to frame
        vBox.add(boardPanel);
        vBox.add(timePanel);
        vBox.add(colorPanel);
        vBox.add(confirmPanel);
        frame.setContentPane(vBox);
        this.frame.setVisible(true);
        this.frame.setSize(300,500);
    }

    public void listener() {
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
            }
        });
        // set board size
//        button1_1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                n = 14;
//            }
//        });
        button1_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                n = 16;
            }
        });
        button1_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                n = 18;
            }
        });
        // set pieces colors
        button2_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1 = 1;
            }
        });
        button2_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1 = 2;
            }
        });
        button3_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player2 = 1;
            }
        });
        button3_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player2 = 2;
            }
        });
        // "ok"
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hour = Integer.valueOf(totalHour.getText());
                minute = Integer.valueOf(totalMinute.getText());
                second = Integer.valueOf(totalSecond.getText());
                pause = Integer.valueOf(turnSecond.getText());
//                System.out.println(hour);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        // "cancel"
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//                totalHour.setText("");
//                totalMinute.setText("");
//                totalSecond.setText("");
//                turnSecond.setText("");
            }
        });
    }
}
