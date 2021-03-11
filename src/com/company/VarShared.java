package com.company;

import Controller.Constantes;
import tree.Cuadro;
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
    public Integer userPoints=0;
    public Integer iaPoints=0;
    public  VarShared(){}
}
