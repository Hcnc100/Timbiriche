package tree;

import Controller.Constantes;
import view.Arista;
import view.MyPoints;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Cuadro implements Constantes {
    public Color color;
    public MyPoints pointTopL;
    public MyPoints pointTopR;
    public MyPoints pointButtonL;
    public MyPoints pointButtonR;
    public Arista aristaTop;
    public Arista aristaBotton;
    public Arista aristaLeft;
    public Arista aristaRigth;
    public boolean visible;

    public Cuadro(MyPoints pointTopL, MyPoints pointTopR, MyPoints pointButtonL, MyPoints pointButtonR) {
        this.pointTopL = pointTopL;
        this.pointTopR = pointTopR;
        this.pointButtonL = pointButtonL;
        this.pointButtonR = pointButtonR;
        this.color=colorInvisible;
        this.visible=false;
    }

    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(pointTopL.x, pointTopL.y, (pointTopR.x- pointTopL.x), (pointButtonL.y- pointTopL.y));
    }

    public void setVisible(Color color) {
        this.color=color;
    }

    public static ArrayList<Cuadro> getCuadros(Cuadro[][] cuadros) {
        ArrayList<Cuadro> listCuadrosSelected = new ArrayList<>();
        for (int f = 0; f < nCuadros; f++) {
            for (int c = 0; c < nCuadros; c++) {
                if(cuadros[f][c].aristaTop.isVisible() && cuadros[f][c].aristaBotton.isVisible() &&
                        cuadros[f][c].aristaLeft.isVisible() && cuadros[f][c].aristaRigth.isVisible()){
                    listCuadrosSelected.add(cuadros[f][c]);
                }
            }
        }
        return listCuadrosSelected;
    }

    public static ArrayList<Cuadro> getCuadrosFromPoints(Cuadro[][] cuadros,ArrayList<Arista> aristasVisibles) {
        ArrayList<Cuadro> listCuadrosSelected = new ArrayList<>();
        for (int f = 0; f < nCuadros; f++) {
            for (int c = 0; c < nCuadros; c++) {
                if(aristasVisibles.contains(cuadros[f][c].aristaTop) && aristasVisibles.contains(cuadros[f][c].aristaBotton)&&
                        aristasVisibles.contains(cuadros[f][c].aristaLeft) && aristasVisibles.contains(cuadros[f][c].aristaRigth)){
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