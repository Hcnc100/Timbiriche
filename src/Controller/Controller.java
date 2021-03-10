package Controller;

import com.company.VarShared;
import tree.Cuadro;
import view.MyPoints;
import view.Arista;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static java.lang.Thread.sleep;

public class Controller implements Constantes {
    private VarShared varShared;

    public Controller() {
        varShared = new VarShared();
        createDrawables();
    }

    public void clickInPoint(Point point, Graphics graphics) {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints; c++) {
                if (varShared.puntos[f][c].contains(point)) {
                    if (varShared.pointSelected == null) {
                        varShared.pointSelected = varShared.puntos[f][c];
                        varShared.pointSelected.setSelect(graphics);
                    } else {
                        if (Arista.isLineValide(varShared.pointSelected, varShared.puntos[f][c])) {
                            Arista auxArista=getArista(varShared.pointSelected, varShared.puntos[f][c]);
                            auxArista.isVisible=true;

                            ArrayList<Cuadro> cuadrosNuevosCompletos = Cuadro.getCuadros(varShared.cuadros);
                            if (cuadrosNuevosCompletos.size() != varShared.cuadrosSeleccionadosActuales.size()) {
                                ArrayList<Cuadro> auxArray= new ArrayList<>(cuadrosNuevosCompletos);
                                auxArray.removeAll(varShared.cuadrosSeleccionadosActuales);
                                if(auxArray.size()!=0){
                                    auxArray.get(0).paintComplete(graphics);
                                    varShared.cuadrosSeleccionadosActuales.add(auxArray.get(0));
                                }
                            }
                            auxArista.paint(graphics);
                            varShared.puntos[f][c].setSelect(graphics);
                            try {
                                sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            varShared.pointSelected.setDeselect(graphics);
                            varShared.puntos[f][c].setDeselect(graphics);
                            varShared.pointSelected = null;

                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Los puntos deben estar alineados\n(vertical u horizontalmente)",
                                    "Error", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        }
    }

    private Arista getArista(MyPoints a, MyPoints b) {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                if (varShared.verticesHorizontales[f][c].contains(a, b)) {
                    return varShared.verticesHorizontales[f][c];
                }
            }
        }

        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints; c++) {
                if (varShared.verticesVerticales[f][c].contains(a, b)) {
                    return varShared.verticesVerticales[f][c];
                }
            }
        }
        return new Arista();
    }

    public void createDrawables() {
        varShared.puntos = new MyPoints[nPoints][nPoints];
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints; c++) {
                varShared.puntos[f][c] = new MyPoints(f, c);
            }
        }
        varShared.verticesHorizontales = new Arista[nPoints][nPoints - 1];
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                varShared.verticesHorizontales[f][c] = new Arista(varShared.puntos[f][c], varShared.puntos[f][c + 1]);
            }
        }

        varShared.verticesVerticales = new Arista[nPoints - 1][nPoints];
        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints; c++) {
                varShared.verticesVerticales[f][c] = new Arista(varShared.puntos[f][c], varShared.puntos[f + 1][c]);
            }
        }

        varShared.cuadros = new Cuadro[nCuadros][nCuadros];
        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                Cuadro aux= new Cuadro(
                        varShared.puntos[f][c],
                        varShared.puntos[f][c + 1],
                        varShared.puntos[f + 1][c],
                        varShared.puntos[f + 1][c + 1]
                );
                setVertices(aux);
                varShared.cuadros[f][c] = aux;
            }
        }

    }

    private void setVertices(Cuadro cuadro){
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
               if(varShared.verticesHorizontales[f][c].contains(cuadro.pointTopL, cuadro.pointTopR)){
                    cuadro.aristaTop=varShared.verticesHorizontales[f][c];
               }else if(varShared.verticesHorizontales[f][c].contains(cuadro.pointButtonL, cuadro.pointButtonR)){
                   cuadro.aristaBotton=varShared.verticesHorizontales[f][c];
               }
            }
        }

        for (int f = 0; f < nPoints-1; f++) {
            for (int c = 0; c < nPoints; c++) {
                if(varShared.verticesVerticales[f][c].contains(cuadro.pointTopL, cuadro.pointButtonL)){
                    cuadro.aristaLeft=varShared.verticesVerticales[f][c];
                }else if(varShared.verticesVerticales[f][c].contains(cuadro.pointTopR, cuadro.pointButtonR)){
                    cuadro.aristaRigth=varShared.verticesVerticales[f][c];
                }
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
                varShared.puntos[f][c].updateCoordenadas(
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
        paintCuadros(panelDrawable.getGraphics());
        paintAristasHorizontales(panelDrawable.getGraphics());
        paintAristasVerticales(panelDrawable.getGraphics());
        paintPoints(panelDrawable.getGraphics());
    }
    private void paintCuadros(Graphics graphics) {
        for (int f = 0; f < nPoints-1; f++) {
            for (int c = 0; c < nPoints-1; c++) {
                varShared.cuadros[f][c].paint(graphics);
            }
        }

    }
    private void paintAristasVerticales(Graphics graphics) {
        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints; c++) {
                varShared.verticesVerticales[f][c].paint(graphics);
            }
        }

    }

    private void paintAristasHorizontales(Graphics graphics) {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                varShared.verticesHorizontales[f][c].paint(graphics);
            }
        }
    }

    private void paintPoints(Graphics graphics) {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints; c++) {
                varShared.puntos[f][c].paint(graphics);
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