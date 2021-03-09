package view;

import Controller.Constantes;

import java.awt.*;

public class MyPoints implements Constantes {
    private final int diametro=10;
    private final int radio=diametro/2;
    private final Rectangle rectangle;
    public int x;
    public int y;

    public MyPoints() {
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

    public void setSelect(Graphics panelDrawable){
        panelDrawable.setColor(Color.orange);
        panelDrawable.fillOval(x-radio,y-radio, diametro, diametro);
    }

    public boolean contains(Point point){
        return rectangle.contains(point);
    }
}
