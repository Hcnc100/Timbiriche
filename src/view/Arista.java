package view;

import Controller.Constantes;

import java.awt.*;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Arista implements Constantes {
    public MyPoints pointA;
    public MyPoints pointB;
    private boolean visible;
    private Color color;
    private int orientation;

    public Arista() {
    }

    public void setOrientacion(int orientacion){
        this.orientation=orientacion;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setAristaVisible(Color color,Graphics graphics,int timeSleep) {
        this.color = color;
        this.visible=true;
        try {
            sleep(timeSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        paint(graphics);
    }

    public void setAristaVisible() {
        this.visible = true;
    }

    public Arista(MyPoints pointA, MyPoints pointB, boolean isVisible) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.visible = isVisible;
        this.color = colorInvisible;
    }

    public Arista(MyPoints pointA, MyPoints pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.visible = false;
        this.color = colorInvisible;
    }


    public void paint(Graphics panelDrawable) {
        if (isVisible()){
            if(orientation==Horizontal){
                panelDrawable.setColor(Color.black);
                panelDrawable.fillRect(pointA.x, pointA.y-3, (pointB.x-pointA.x),6);
                panelDrawable.setColor(color);
                panelDrawable.fillRect(pointA.x, pointA.y-2, (pointB.x-pointA.x),4);
            }else{
                panelDrawable.setColor(Color.black);
                panelDrawable.fillRect(pointA.x-3, pointA.y, 6,(pointB.y-pointA.y));
                panelDrawable.setColor(color);
                panelDrawable.fillRect(pointA.x-2, pointA.y, 4,(pointB.y-pointA.y));
            }
        }
    }

    public Arista getCopy() {
        return new Arista(
                this.pointA,
                this.pointB,
                this.visible
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arista arista = (Arista) o;
        return contains(arista.pointA, arista.pointB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointA, pointB);
    }

    public boolean contains(MyPoints a, MyPoints b) {
        return a == pointA && b == pointB || b == pointA && a == pointB;
    }

    public static boolean isLineValide(MyPoints pointA, MyPoints pointB) {
        return pointA.x == pointB.x || pointA.y == pointB.y;
    }
}
