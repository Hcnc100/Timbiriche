package tree;

import Controller.Constantes;
import com.company.VarShared;
import view.Arista;

import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.stream.Collectors;

public class Arbol {

    public Node startAlgoritmoAnchuraLineal(Node inicio,int profundidad,VarShared varShared) {
        int nNodosCreados = 0;
        int profundidadActual=0;
        Node nodoEncontrado=inicio;
        Queue<Node> cola = new LinkedList<>();
        ArrayList<Node> nodosVisitados = new ArrayList<>();
        cola.add(inicio);
        while (!cola.isEmpty() && profundidadActual<profundidad) {
            Node nodoActual = cola.poll();
            nodosVisitados.add(nodoActual);
            List<Node> movimientosValidos = nodoActual.aristasNoVisibles.stream().map(arista -> {
                        ArrayList<Arista> auxAristasVisibles = new ArrayList<>(nodoActual.aristasActuales);
                        ArrayList<Arista> auxAristasNoVisibles = new ArrayList<>(nodoActual.aristasNoVisibles);
                        auxAristasNoVisibles.remove(arista);
                        Arista copyArista=arista.getCopy();
                        copyArista.setAristaVisible();
                        auxAristasVisibles.add(copyArista);
                        Node nodoAux = new Node(
                                auxAristasVisibles,
                                auxAristasNoVisibles,
                                nodoActual.nivel + 1,
                                Cuadro.getCuadrosFromPoints(varShared.cuadros, auxAristasVisibles).size() - varShared.userPoints,
                                nodoActual,
                                copyArista
                        );
                        return nodoAux;
                    }
            ).filter(node -> !nodosVisitados.contains(node)
            ).sorted(
                    (o1, o2) -> Integer.compare(o2.puntuacion, o1.puntuacion)
            ).collect(Collectors.toList());
            if (movimientosValidos.size()!=0){
                if(movimientosValidos.get(0).puntuacion== inicio.puntuacion){
                    Collections.shuffle(movimientosValidos);
                }
                if (movimientosValidos.get(0).puntuacion>nodoEncontrado.puntuacion || nodoEncontrado == inicio){
                    nodoEncontrado=movimientosValidos.get(0);
                }
            }
            nodoActual.hijos.addAll(movimientosValidos);
            cola.addAll(movimientosValidos);
            nNodosCreados += movimientosValidos.size();
            profundidadActual++;
        }
        System.out.println("Total de nodos creados :" + nNodosCreados);
        return getFirstMovimiuento(nodoEncontrado,inicio);
    }


    private Node getFirstMovimiuento(Node nodo,Node raiz){
        Node aux=nodo;
        while (aux.parent!=null && aux.parent!=raiz){
            aux=aux.parent;
        }
        return aux;
    }


}
