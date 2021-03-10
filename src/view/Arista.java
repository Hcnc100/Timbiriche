package view;

import java.awt.*;
import java.util.ArrayList;

public class Arista {
    private MyPoints pointA;
    private MyPoints pointB;
    public boolean isVisible;

    public Arista(){}

    public Arista(MyPoints pointA, MyPoints pointB){
        this.pointA=pointA;
        this.pointB=pointB;
        this.isVisible=false;
    }


    public void paint(Graphics panelDrawable){
        if (isVisible){
            panelDrawable.setColor(Color.black);
        }else{
            panelDrawable.setColor(Color.white);
        }
        panelDrawable.drawLine(pointA.x, pointA.y, pointB.x, pointB.y);
    }

    public boolean contains(MyPoints a,MyPoints b){
        return a==pointA && b == pointB || b==pointA && a== pointB;
    }

    public static boolean isLineValide(MyPoints pointA,MyPoints pointB){
        return pointA.x== pointB.x || pointA.y== pointB.y;
    }
}
