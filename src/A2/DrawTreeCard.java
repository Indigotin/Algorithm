package A2;

import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawTreeCard extends JFrame{
    private Tree tree = new Tree();
    private JButton jb = new JButton("Increase");

    public static void main(String[] args) {
        DrawTreeCard f = new DrawTreeCard();
        f.setTitle("分形树");
        f.setSize(1100,900);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使用 System exit 方法退出应用程序
        f.setVisible(true);
    }


    public DrawTreeCard(){
        this.add(tree);
        JPanel panel = new JPanel();
        panel.add(jb);
        this.add(panel,BorderLayout.SOUTH);
        jb.addActionListener(e->{
            tree.run();
        });
    }

}


