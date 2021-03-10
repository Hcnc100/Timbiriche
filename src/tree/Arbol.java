package tree;

import Controller.Constantes;
import com.company.VarShared;
import view.Arista;

import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.stream.Collectors;

public class Arbol {

    /*
    public void setStates(int[][] stateInit, int[][] stateFin){
        this.stateInit = new Cuadro(stateInit);
        this.stateFinish = new Cuadro(stateFin);
        this.raiz = new Node(this.stateInit);
        this.camino = new ArrayList<>();
    }

     */


    /*private Thread getThreadCountTime(UpdateInterface updateTime) {
        return new Thread(() -> {
            int segundos = 0;
            int decisegundo = 0;
            do {
                try {
                    Thread.sleep(10);
                    decisegundo++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (decisegundo == 100) {
                    segundos++;
                    decisegundo = 0;
                }
                updateTime.updateValueString(String.format("%02d", segundos) + "." + String.format("%02d", decisegundo));
            } while (varShared.isRunning);
            varShared.isRunning = false;
        });
    }
*/



   /* private void fillArrayCamino(Node nodo) {
        Node nodoActual = nodo;
        while (nodoActual != null) {
            camino.add(nodoActual.arista);
            nodoActual = nodoActual.parent;
        }
        Collections.reverse(camino);
    }*/



    /*public void startAlgoritmoProfundidad() {
        if (stateInit.equals(stateFinish)) {
            return;
        }
        varShared = new VarShared(raiz);
        Stack<Node> pila = new Stack<>();

        varShared.isRunning = true;

        pila.add(raiz);
        while (!pila.isEmpty() && varShared.isRunning) {
            Node nodoActual = pila.peek();
            varShared.nodosVisistados.add(nodoActual);
            Point coordenadas = nodoActual.arista.getEmptySpace();
            ArrayList<Point> posiblesMovientos = Cuadro.getPosibleMoviminetos(coordenadas);
            List<Node> movimientosValidos = posiblesMovientos.stream().map(point ->
                    Cuadro.changePosition(coordenadas, point, nodoActual.arista)
            ).map(arrayChars -> {
                        Node nodoAux = new Node(new Cuadro(arrayChars), nodoActual, nodoActual.nivel + 1);
                        if (nodoAux.arista.equals(stateFinish)) {
                            varShared.setNodoAnswer(nodoAux);
                        }
                        return nodoAux;
                    }
            ).filter(node -> !varShared.nodosVisistados.contains(node)
            ).collect(Collectors.toList());
            if (movimientosValidos.isEmpty()) {
                pila.pop();
            } else {
                pila.push(movimientosValidos.get(0));
            }
            if (varShared.isFind) {
                break;
            }
        }


        System.out.println("Hilos terminados");
        //0System.out.println("Total de nodos creados :" + varShared.nodosCreados);
        System.out.println("Solucion econtrada:" + varShared.isFind);
        fillArrayCamino(varShared.nodoEncontrado);
    }*/

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
                        copyArista.isVisible=true;
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

    private ArrayList<Arista> getAristasNoVisitadas() {
        return new ArrayList<>();
    }

    private Node getFirstMovimiuento(Node nodo,Node raiz){
        Node aux=nodo;
        while (aux.parent!=null && aux.parent!=raiz){
            aux=aux.parent;
        }
        return aux;
    }

    /*public void printAnchura() {
        Queue<Node> cola = new LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            Node nodoActual = cola.poll();
            System.out.println(nodoActual.arista);
            cola.addAll(nodoActual.hijos);
        }
    }*/

}
