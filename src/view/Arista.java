package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Arista {
    public MyPoints pointA;
    public MyPoints pointB;
    public boolean isVisible;

    public Arista() {
    }

    public Arista(MyPoints pointA, MyPoints pointB, boolean isVisible) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.isVisible = isVisible;
    }

    public Arista(MyPoints pointA, MyPoints pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.isVisible = false;
    }


    public void paint(Graphics panelDrawable) {
        if (isVisible) {
            panelDrawable.setColor(Color.black);
        } else {
            panelDrawable.setColor(Color.white);
        }
        panelDrawable.drawLine(pointA.x, pointA.y, pointB.x, pointB.y);
    }

    public Arista getCopy() {
        return new Arista(
                this.pointA,
                this.pointB,
                this.isVisible
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
        return Objects.hash(pointA, pointB, isVisible);
    }

    public boolean contains(MyPoints a, MyPoints b) {
        return a == pointA && b == pointB || b == pointA && a == pointB;
    }

    public static boolean isLineValide(MyPoints pointA, MyPoints pointB) {
        return pointA.x == pointB.x || pointA.y == pointB.y;
    }
}
