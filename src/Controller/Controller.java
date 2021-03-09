package Controller;

import view.MyPoints;
import view.Vertice;

import javax.swing.*;
import java.awt.*;

public class Controller implements Constantes{
    private MyPoints[][] puntos;
    private Vertice[][] verticesHorizontales;
    private Vertice[][] verticesVerticales;
    private MyPoints pointSelected;

    public Controller() {
        createDrawables();
    }

    public void clickInPoint(Point point, Graphics graphics) {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints; c++) {
                if (puntos[f][c].contains(point)) {
                    if (pointSelected==null) {
                        pointSelected=puntos[f][c];
                        pointSelected.setSelect(graphics);
                    } else {
                        if (Vertice.isLineValide(pointSelected, puntos[f][c])) {
                            getArista(pointSelected,puntos[f][c]).setSelected(graphics);
                            puntos[f][c].setSelect(graphics);
                            pointSelected=null;
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Los puntos deben estar alineados (vertical u horizontalmente)",
                                    "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        }
    }
    private Vertice getArista(MyPoints a,MyPoints b){
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                if (verticesHorizontales[f][c].contains(a,b)){
                    return  verticesHorizontales[f][c];
                }
            }
        }

        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints; c++) {
                if (verticesVerticales[f][c].contains(a,b)){
                    return  verticesHorizontales[f][c];
                }
            }
        }
        return null;
    }

    public void createDrawables() {
        puntos = new MyPoints[nPoints][nPoints];
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints; c++) {
                puntos[f][c] = new MyPoints();
            }
        }
        verticesHorizontales = new Vertice[nPoints][nPoints - 1];
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                verticesHorizontales[f][c] = new Vertice(puntos[f][c], puntos[f][c + 1]);
            }
        }

        verticesVerticales = new Vertice[nPoints - 1][nPoints];
        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints; c++) {
                verticesVerticales[f][c] = new Vertice(puntos[f][c], puntos[f + 1][c]);
            }
        }
    }

    public void assigPositionDrawables(JPanel panelDrawable) {
        final Dimension size = panelDrawable.getSize();
        final int widthWithMargin = (size.width - (margin * 2));
        final int heigthWithMargin = (size.height - (margin * 2));
        final int distanceX = widthWithMargin / (nPoints - 1);
        final int distanceY = heigthWithMargin / (nPoints - 1);
        for (int c = 0; c < nPoints; c++) {
            for (int f = 0; f < nPoints; f++) {
                puntos[f][c].updateCoordenadas(
                        (c * distanceX) + margin,
                        (f * distanceY) + margin
                );
            }
        }
    }

    public void paintDrawables(JPanel panelDrawable) {
        if (DEBUG) {
            paintContainerPoints(panelDrawable);
        }
        paintAristasHorizontales(panelDrawable.getGraphics());
        paintAristasVerticales(panelDrawable.getGraphics());
        paintPoints(panelDrawable.getGraphics());
    }

    private void paintAristasVerticales(Graphics graphics) {
        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints; c++) {
                verticesVerticales[f][c].paint(graphics);
            }
        }

    }

    private void paintAristasHorizontales(Graphics graphics) {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                verticesHorizontales[f][c].paint(graphics);
            }
        }
    }

    private void paintPoints(Graphics graphics) {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints; c++) {
                puntos[f][c].paint(graphics);
            }
        }
    }

    private void paintContainerPoints(JPanel panel) {
        final Dimension size = panel.getSize();
        Graphics panelDrawable = panel.getGraphics();
        panelDrawable.setColor(Color.BLACK);
        panelDrawable.drawRect(
                margin,
                margin,
                size.width - (margin * 2),
                size.height - (margin * 2));
    }

}