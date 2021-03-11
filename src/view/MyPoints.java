package view;

import Controller.Constantes;

import java.awt.*;

import static java.lang.Thread.sleep;

public class MyPoints implements Constantes {
    private final int diametro=11;
    private final int radio=diametro/2;
    private final Rectangle rectangle;
    public int x;
    public int y;
    public Point coordenadasMatrix;

    public MyPoints(int f,int c) {
        coordenadasMatrix= new Point(f,c);
        rectangle = new Rectangle(0,0,diametro,diametro);
    }

    public void updateCoordenadas(int x, int y){
        this.x=x;
        this.y=y;
        rectangle.setLocation(x-radio,y-radio);
    }
    public void paint(Graphics panelDrawable) {
        if(DEBUG){
            panelDrawable.setColor(Color.GREEN);
            panelDrawable.drawRect(x-radio,y-radio,diametro,diametro);
        }
        panelDrawable.setColor(Color.BLACK);
        panelDrawable.fillOval(x-radio,y-radio, diametro, diametro);
    }

    public void setSelect(Graphics panelDrawable,int timeSleep){
        if (timeSleep!=0){
            try {
                sleep(timeSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        panelDrawable.setColor(Color.orange);
        panelDrawable.fillOval(x-radio,y-radio, diametro, diametro);
    }

    public void setDeselect(Graphics panelDrawable){
        panelDrawable.setColor(Color.black);
        panelDrawable.fillOval(x-radio,y-radio, diametro, diametro);
    }
    public Point getPoint(){
        return new Point(x,y);
    }

    public boolean contains(Point point){
        return rectangle.contains(point);
    }
}
