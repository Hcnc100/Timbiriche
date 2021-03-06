package Controller;

import com.company.VarShared;
import tree.Arbol;
import tree.Cuadro;
import tree.Node;
import view.MyPoints;
import view.Arista;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Controller implements Constantes {
    private VarShared varShared;
    private Arbol arbol;
    private MyPoints pointSelected;

    public Controller() {
        varShared = new VarShared();
        arbol = new Arbol();
        pointSelected=null;
        createDrawables();
    }

    private MyPoints getPointFromClick(Point point) {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints; c++) {
                if (varShared.puntos[f][c].contains(point)) {
                    return varShared.puntos[f][c];
                }
            }
        }
        return null;
    }

    public void clickInPointHuman(Point point, Graphics graphics) {
        MyPoints clickPoint = getPointFromClick(point);
        if(clickPoint!=null){
            if (pointSelected == null) {
                pointSelected = clickPoint;
                pointSelected.setSelect(graphics,0);
            } else {
                if (Arista.isLineValide(pointSelected,clickPoint)) {
                    Arista auxArista = getArista(pointSelected,clickPoint);
                    animationAristaVisible(graphics,auxArista,colorUser,0,0);
                    updateCuadrosBeforeClick(auxArista,graphics,colorUser,varShared.userPoints);
                    pointSelected=null;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Los puntos deben estar alineados\n(vertical u horizontalmente)",
                            "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

    }

    private void animationAristaVisible(Graphics graphics, Arista arista, Color colorArista, int timeSleepPoints, int timeSleepArista) {
        arista.pointA.setSelect(graphics, timeSleepPoints);
        arista.pointB.setSelect(graphics, timeSleepPoints);
        arista.setAristaVisible(colorArista, graphics, timeSleepArista);
        arista.pointA.setDeselect(graphics);
        arista.pointB.setDeselect(graphics);
    }

    private void updateCuadrosBeforeClick(Arista arista, Graphics graphics, Color colorArista,Integer points) {
        varShared.aristasVisibles.add(arista);
        varShared.aristasNoVisibles.remove(arista);
        ArrayList<Cuadro> cuadrosNuevosCompletos = Cuadro.getCuadros(varShared.cuadros);
        if (cuadrosNuevosCompletos.size() != varShared.cuadrosActuales.size()) {
            ArrayList<Cuadro> auxArray = new ArrayList<>(cuadrosNuevosCompletos);
            auxArray.removeAll(varShared.cuadrosActuales);
            if (auxArray.size() != 0) {
                auxArray.get(0).setVisible(colorArista);
                varShared.cuadrosActuales.add(auxArray.get(0));
                paintDrawables(graphics);
                points++;
            }
        }
    }

    private void clickInPointIa(Arista arista, Graphics graphics) {
        animationAristaVisible(graphics, arista, colorIA, 100, 100);
        updateCuadrosBeforeClick(arista, graphics, colorIA,varShared.iaPoints);
    }

    public void initAlgoritmo(Graphics graphics) {
        Node nodoInicial = new Node(varShared.aristasVisibles, varShared.aristasNoVisibles, 0, varShared.iaPoints, null, null);
        Node siguinteMovimineto = arbol.startAlgoritmoAnchuraLineal(nodoInicial, (ProdundidadBusqueda * 2) - 1, varShared);
        if (siguinteMovimineto.aristaMod != null) {
            int indexReal=varShared.aristasNoVisibles.indexOf(siguinteMovimineto.aristaMod);
            Arista aristaReal=varShared.aristasNoVisibles.get(indexReal);
            clickInPointIa(aristaReal, graphics);
        }
    }

    private Arista getArista(MyPoints a, MyPoints b) {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                if (varShared.aristasHorizontales[f][c].contains(a, b)) {
                    return varShared.aristasHorizontales[f][c];
                }
            }
        }

        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints; c++) {
                if (varShared.aristasVerticales[f][c].contains(a, b)) {
                    return varShared.aristasVerticales[f][c];
                }
            }
        }
        return new Arista();
    }

    private MyPoints[][] createPoints() {
        MyPoints[][] puntos = new MyPoints[nPoints][nPoints];
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints; c++) {
                puntos[f][c] = new MyPoints(f, c);
            }
        }
        return puntos;
    }

    private Arista[][] createAristasHorizontales() {
        Arista[][] aristasHorizontales = new Arista[nPoints][nPoints - 1];
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                aristasHorizontales[f][c] = new Arista(varShared.puntos[f][c], varShared.puntos[f][c + 1]);
                aristasHorizontales[f][c].setOrientacion(Horizontal);
            }
        }
        return aristasHorizontales;
    }

    private Arista[][] createAristaVertical() {
        Arista[][] aristasVerticales = new Arista[nPoints - 1][nPoints];
        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints; c++) {
                aristasVerticales[f][c] = new Arista(varShared.puntos[f][c], varShared.puntos[f + 1][c]);
                aristasVerticales[f][c].setOrientacion(Vertical);
            }
        }
        return aristasVerticales;
    }

    private Cuadro[][] createCuadros(MyPoints[][] puntos) {
        Cuadro[][] cuadros = new Cuadro[nCuadros][nCuadros];
        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                Cuadro aux = new Cuadro(
                        puntos[f][c],
                        puntos[f][c + 1],
                        puntos[f + 1][c],
                        puntos[f + 1][c + 1]
                );
                setVertices(aux);
                cuadros[f][c] = aux;
            }
        }
        return cuadros;
    }

    public void createDrawables() {
        varShared.puntos = createPoints();
        varShared.aristasHorizontales = createAristasHorizontales();
        varShared.aristasVerticales = createAristaVertical();
        varShared.cuadros = createCuadros(varShared.puntos);
        setAristasStatesVisitadas();
    }

    private void setAristasStatesVisitadas() {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                if (varShared.aristasHorizontales[f][c].isVisible()) {
                    varShared.aristasVisibles.add(varShared.aristasHorizontales[f][c]);
                } else {
                    varShared.aristasNoVisibles.add(varShared.aristasHorizontales[f][c]);
                }
            }
        }

        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints; c++) {
                if (varShared.aristasVerticales[f][c].isVisible()) {
                    varShared.aristasVisibles.add(varShared.aristasVerticales[f][c]);
                } else {
                    varShared.aristasNoVisibles.add(varShared.aristasVerticales[f][c]);
                }
            }
        }
    }

    private void setVertices(Cuadro cuadro) {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                if (varShared.aristasHorizontales[f][c].contains(cuadro.pointTopL, cuadro.pointTopR)) {
                    cuadro.aristaTop = varShared.aristasHorizontales[f][c];
                } else if (varShared.aristasHorizontales[f][c].contains(cuadro.pointButtonL, cuadro.pointButtonR)) {
                    cuadro.aristaBotton = varShared.aristasHorizontales[f][c];
                }
            }
        }

        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints; c++) {
                if (varShared.aristasVerticales[f][c].contains(cuadro.pointTopL, cuadro.pointButtonL)) {
                    cuadro.aristaLeft = varShared.aristasVerticales[f][c];
                } else if (varShared.aristasVerticales[f][c].contains(cuadro.pointTopR, cuadro.pointButtonR)) {
                    cuadro.aristaRigth = varShared.aristasVerticales[f][c];
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

    public void paintDrawables(Graphics graphics) {
        paintCuadros(graphics);
        paintAristasHorizontales(graphics);
        paintAristasVerticales(graphics);
        paintPoints(graphics);
    }
    private void paintCuadros(Graphics graphics) {
        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                varShared.cuadros[f][c].paint(graphics);
            }
        }

    }

    private void paintAristasVerticales(Graphics graphics) {
        for (int f = 0; f < nPoints - 1; f++) {
            for (int c = 0; c < nPoints; c++) {
                varShared.aristasVerticales[f][c].paint(graphics);
            }
        }

    }

    private void paintAristasHorizontales(Graphics graphics) {
        for (int f = 0; f < nPoints; f++) {
            for (int c = 0; c < nPoints - 1; c++) {
                varShared.aristasHorizontales[f][c].paint(graphics);
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