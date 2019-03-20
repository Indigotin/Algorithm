package A2;

import javax.swing.*;
import java.awt.*;

public class Tree extends JPanel {

    private int n = 0;
    private double A,B,C;
    private double PI = Math.PI;


    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        //	g.setColor(Color.red);
        /*Point p1 = new Point(this.getWidth()/2,this.getHeight()-10);
        Point p2 = new Point(this.getWidth()/2,this.getHeight()/2);*/
        Point p1 = new Point(this.getWidth()/2,this.getHeight()/2+400);
        Point p2 = new Point(this.getWidth()/2,this.getHeight()/2+200);
        display(g,n,p1,p2);
    }

    public void run(){
        n++;
        repaint();//重绘
    }

    private void display(Graphics g,int n,Point p1,Point p2){

        if (n>=0){
            if(n==0) {
                g.setColor(Color.RED);
            }
            else {
                g.setColor(Color.BLACK);
            }
            Graphics2D g2 = (Graphics2D)g;
            Stroke stroke = new BasicStroke(2.0f*n);//使每次递归后树枝都会变细
            g2.setStroke(stroke);
            g2.drawLine(p1.x, p1.y, p2.x, p2.y);

            Point p3 = mid1(p1,p2);
            Point p4 = mid2(p1,p2);
            display(g2, n-1, p2, p3);//左
            display(g2, n-1, p2, p4);//右
        }
    }

    //右
    private Point mid1(Point p1,Point p2){
        Point p = new Point();
        A = Math.atan2(p2.x-p1.x,p1.y-p2.y);   //上一个枝丫的偏转角度
        B = A - PI/5;//这一次的偏转角度
        C = Math.sqrt((p2.x-p1.x)*(p2.x-p1.x)+(p2.y-p1.y)*(p2.y-p1.y))/4*3; //这次的枝丫为上一段枝丫的多少
        p.x= (int)(p2.x + C*Math.sin(B));
        p.y= (int)(p2.y - C*Math.cos(B));
        return p;
    }
    
    //左
    private Point mid2(Point p1,Point p2){
        Point p = new Point();
        A = Math.atan2(p2.x-p1.x,p1.y-p2.y);//上一个枝丫的偏转角度
        B = A + PI/5;//这一次的偏转角度
        C = Math.sqrt((p2.x-p1.x)*(p2.x-p1.x)+(p2.y-p1.y)*(p2.y-p1.y))/4*3;
        p.x= (int)(p2.x + C*Math.sin(B));
        p.y= (int)(p2.y - C*Math.cos(B));
        if (p.x==0){
            System.out.println(p1.x+" "+p1.y+" "+p2.x+" "+p2.y+" "+A);
        }
        return p;
    }
}
