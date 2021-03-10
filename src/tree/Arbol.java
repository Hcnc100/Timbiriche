package tree;

import Controller.Constantes;
import com.company.VarShared;
import view.Arista;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Arbol implements Constantes {

    private Cuadro stateInit;
    private Cuadro stateFinish;
    private Node raiz;
    private VarShared varShared;
    private ArrayList<Cuadro> camino;


    public Arbol() {}

    public ArrayList<Cuadro> getCamino() {
        return camino;
    }
    public boolean findStateFinish(){
        return varShared.isFind;
    }

    public void setStates(int[][] stateInit, int[][] stateFin){
        this.stateInit = new Cuadro(stateInit);
        this.stateFinish = new Cuadro(stateFin);
        this.raiz = new Node(this.stateInit);
        this.camino = new ArrayList<>();
    }

    public int nivelNodeFinal(){
        return varShared.nodoEncontrado.nivel;
    }
    private Thread getThreadCountTime(UpdateInterface updateTime) {
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


    public void stopThreads() {
        varShared.isRunning = false;
    }

    private void fillArrayCamino(Node nodo) {
        Node nodoActual = nodo;
        while (nodoActual != null) {
            camino.add(nodoActual.arista);
            nodoActual = nodoActual.parent;
        }
        Collections.reverse(camino);
    }

    public void printCamino(Node nodo) {
        Node nodoActual = nodo;
        while (nodoActual != null) {
            System.out.println(nodoActual);
            nodoActual = nodoActual.parent;
        }
    }


    public void startAlgoritmoProfundidad() {
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
    }

    public Node startAlgoritmoAnchuraLineal() {

        if (stateInit.equals(stateFinish)) {
            return raiz;
        }
        int nNodosCreados = 0;
        AtomicBoolean findCuadro = new AtomicBoolean(false);
        AtomicReference<Node> nodoEncontrado = new AtomicReference<>();
        Queue<Node> cola = new LinkedList<>();
        ArrayList<Node> nodosVisitados = new ArrayList<>();
        cola.add(raiz);
        ArrayList<Arista> verticesNoVisitados=new  ArrayList<>();
        Collections.copy(verticesNoVisitados,varShared.verticesNoVisitados);
        while (!cola.isEmpty()) {
            Node nodoActual = cola.poll();
            nodosVisitados.add(nodoActual);
            List<Node> movimientosValidos = verticesNoVisitados.stream().map(arista -> {
                        ArrayList<Arista> arrayListAux=new ArrayList<>();
                        Collections.copy(arrayListAux,nodoActual.aristas);
                        arrayListAux.add(arista);
                        Node nodoAux = new Node(
                                arrayListAux,nodoActual,nodoActual.nivel+1,10
                        );
                    }
            ).filter(node -> !nodosVisitados.contains(node)
            ).collect(Collectors.toList());
            nodoActual.hijos.addAll(movimientosValidos);
            cola.addAll(movimientosValidos);
            nNodosCreados += movimientosValidos.size();
            if (findCuadro.get()) {
                break;
            }
        }
        System.out.println("Total de nodos creados :" + nNodosCreados);
        return nodoEncontrado.get();
    }

    public void printAnchura() {
        Queue<Node> cola = new LinkedList<>();
        cola.add(raiz);
        while (!cola.isEmpty()) {
            Node nodoActual = cola.poll();
            System.out.println(nodoActual.arista);
            cola.addAll(nodoActual.hijos);
        }
    }

}
