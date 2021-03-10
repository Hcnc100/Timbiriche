package tree;

import Controller.Constantes;
import view.Arista;
import view.MyPoints;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Cuadro implements Constantes {
    public Color color = Color.white;
    public MyPoints pointTopL;
    public MyPoints pointTopR;
    public MyPoints pointButtonL;
    public MyPoints pointButtonR;
    public Arista aristaTop;
    public Arista aristaBotton;
    public Arista aristaLeft;
    public Arista aristaRigth;

    public Cuadro(MyPoints pointTopL, MyPoints pointTopR, MyPoints pointButtonL, MyPoints pointButtonR) {
        this.pointTopL = pointTopL;
        this.pointTopR = pointTopR;
        this.pointButtonL = pointButtonL;
        this.pointButtonR = pointButtonR;
    }

    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(pointTopL.x, pointTopL.y, (pointTopR.x- pointTopL.x), (pointButtonL.y- pointTopL.y));
    }

    public void paintComplete(Graphics graphics) {
        color=Color.red;
        paint(graphics);
    }

    public static ArrayList<Cuadro> getCuadros(Cuadro[][] cuadros) {
        ArrayList<Cuadro> listCuadrosSelected = new ArrayList<>();
        for (int f = 0; f < nCuadros-1; f++) {
            for (int c = 0; c < nCuadros-1; c++) {
                if(cuadros[f][c].aristaTop.isVisible && cuadros[f][c].aristaBotton.isVisible &&
                        cuadros[f][c].aristaLeft.isVisible && cuadros[f][c].aristaRigth.isVisible){
                    listCuadrosSelected.add(cuadros[f][c]);
                }
            }
        }
        return listCuadrosSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuadro cuadro = (Cuadro) o;
        return Objects.equals(pointTopL, cuadro.pointTopL) && Objects.equals(pointTopR, cuadro.pointTopR) && Objects.equals(pointButtonL, cuadro.pointButtonL) && Objects.equals(pointButtonR, cuadro.pointButtonR);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointTopL, pointTopR, pointButtonL, pointButtonR);
    }
}