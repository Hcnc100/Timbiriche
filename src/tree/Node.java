package tree;

import view.Arista;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class Node{
    public ArrayList<Arista> aristas;
    public int nivel = 0;
    public int puntuacion=0;
    final public Node parent;
    final public ArrayList<Node> hijos = new ArrayList<>();

    public Node(ArrayList<Arista> aristas, Node parent, int nivel,int puntuacion) {
        this.aristas = aristas;
        this.parent = parent;
        this.nivel = nivel;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Node) {
            Node aux = (Node) object;
            return aux.aristas.equals(this.aristas) && puntuacion == aux.puntuacion;
        }
        return false;
    }
}
