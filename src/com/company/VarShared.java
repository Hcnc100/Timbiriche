package com.company;

import Controller.Constantes;
import tree.Cuadro;
import tree.Node;
import view.MyPoints;
import view.Arista;

import java.util.ArrayList;

public class VarShared implements Constantes {
   public MyPoints[][] puntos;
    public Arista[][] aristasHorizontales;
    public Arista[][] aristasVerticales;
    public Cuadro[][] cuadros;
    public ArrayList<Cuadro> cuadrosActuales = new ArrayList<>();
    public ArrayList<Arista> aristasVisibles = new ArrayList<>();
    public ArrayList<Arista> aristasNoVisibles = new ArrayList<>();
    public MyPoints pointSelected;
    public int userPoints=0;
    public int iaPoints=0;
    public  VarShared(){}
}
