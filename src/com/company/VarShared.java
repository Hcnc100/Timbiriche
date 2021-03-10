package com.company;

import Controller.Constantes;
import tree.Cuadro;
import tree.Node;
import view.MyPoints;
import view.Arista;

import java.util.ArrayList;

public class VarShared implements Constantes {
   public MyPoints[][] puntos;
    public Arista[][] verticesHorizontales;
    public Arista[][] verticesVerticales;
    public MyPoints pointSelected;
    public boolean isFind=false;
    public Node nodoEncontrado;
    public boolean isRunning=false;
    public ArrayList<Node> nodosVisistados;
    public ArrayList<Arista> verticesVisitados = new ArrayList<>();
    public ArrayList<Arista> verticesNoVisitados = new ArrayList<>();
    public Cuadro[][] cuadros;
    public ArrayList<Cuadro> cuadrosSeleccionadosActuales = new ArrayList<>();
    public int userPoints=0;
    public int iaPoints=0;

    public VarShared(Node raiz) {
        
    }
    
    public  VarShared(){}

    public void setNodoAnswer(Node nodoAux) {
    }
}
