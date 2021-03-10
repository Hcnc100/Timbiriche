package tree;

import view.Arista;

import java.util.ArrayList;
import java.util.Objects;


public class Node{
    public ArrayList<Arista> aristasActuales;
    public ArrayList<Arista> aristasNoVisibles;
    public Arista aristaMod;
    public int nivel = 0;
    public int puntuacion=0;
    final public Node parent;
    final public ArrayList<Node> hijos = new ArrayList<>();

    public Node(ArrayList<Arista> aristasActuales, ArrayList<Arista> aristasNoVisitadas, int nivel, int puntuacion, Node parent,Arista arista) {
        this.aristasActuales = aristasActuales;
        this.aristasNoVisibles = aristasNoVisitadas;
        this.nivel = nivel;
        this.puntuacion = puntuacion;
        this.parent = parent;
        this.aristaMod=arista;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return puntuacion == node.puntuacion && Objects.equals(aristasActuales, node.aristasActuales) && Objects.equals(aristasNoVisibles, node.aristasNoVisibles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aristasActuales, aristasNoVisibles, puntuacion);
    }
}
