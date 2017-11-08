package grafos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class GrafoD {
    private static final int NUMERO_GRANDE = 9999;

    int[][] matriz;

    public GrafoD(File archivo) throws FileNotFoundException {
	Scanner sc = new Scanner(archivo);
	int orden = sc.nextInt();
	int cantAristas = sc.nextInt();

	matriz = new int[orden][orden];

	for (int i = 0; i < orden; i++) {
	    for (int j = 0; j < orden; j++)
		matriz[i][j] = NUMERO_GRANDE;
	}

	for (int i = 0; i < cantAristas; i++) {
	    int origen = sc.nextInt();
	    int costo = sc.nextInt();
	    int destino = sc.nextInt();
	    matriz[origen - 1][destino - 1] = costo;
	}

	sc.close();
    }

    public void mostrar() {
	for (int i = 0; i < matriz.length; i++) {
	    System.out.print("Nodo " + (i + 1) + ": ");
	    for (int j = 0; j < matriz.length; j++) {
		if (matriz[i][j] != 0)
		    System.out.print((j + 1) + "(" + matriz[i][j] + ") ");
	    }
	    System.out.println();
	}
    }

    // tira las aristas de un nodo
    public Arista[] getAristas(int nodo) throws Exception {
	int tam = matriz.length;
	if (nodo >= tam)
	    throw new Exception("nodo no v�lido");

	List<Arista> aristas = new LinkedList<Arista>();
	Arista[] res;

	for (int i = 0; i < tam; i++) {
	    if (matriz[nodo][i] != NUMERO_GRANDE)
		aristas.add(new Arista(nodo, matriz[nodo][i], i));
	}

	res = new Arista[aristas.size()];
	res = aristas.toArray(res);

	/*
	 * for (int i = 0; i < res.length; i++) { System.out.println(res[i]); }
	 */

	return res;
    }
    
    public Nodo getNodo (int nodoIndex)
    {
	try {
	    return new Nodo(nodoIndex, getAristas(nodoIndex));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	return null;
    }
    
    public Nodo[] getNodos ()
    {
	int tam = matriz.length;
	Nodo[] nodos = new Nodo[tam];
	
	for(int i = 0; i < tam; i++)
	    nodos[i] = getNodo(i);
	
	return nodos;
    }

    public void dijkstra(int nodoPartida, int nodoDestino) throws Exception {
	nodoPartida--;
	nodoDestino--;
	int tam = matriz.length;

	if (nodoPartida < 0 || nodoPartida > tam - 1 || nodoDestino < 0 || nodoDestino > tam - 1
		|| nodoDestino == nodoPartida)
	    throw new Exception("nodo de partida inválido");

	int[] res = new int[tam];
	int[] camino = new int[tam];
	Set<Integer> nodosRestantes = new HashSet<Integer>(tam);

	// inicializo conjunto de nodos restantes
	for (int i = 0; i < tam; i++)
	    nodosRestantes.add(i);

	// saco el nodo de partida
	nodosRestantes.remove(nodoPartida);

	// seteo los costos del vector res en base a
	// los nodos adyacentes al nodo de partida
	for (int i = 0; i < tam; i++)
	    res[i] = matriz[nodoPartida][i];

	while (!nodosRestantes.isEmpty()) {
	    int nodoMenor = 0, valMenor = NUMERO_GRANDE;
	    // busco el nodo de menor costo
	    for (Integer i : nodosRestantes) {
		if (res[i] < valMenor) {
		    nodoMenor = i;
		    valMenor = res[i];
		}
	    }

	    // me fijo si hay un camino menos costoso
	    for (Integer i : nodosRestantes) {
		if (res[nodoMenor] + matriz[nodoMenor][i] < res[i]) {
		    res[i] = res[nodoMenor] + matriz[nodoMenor][i];
		    camino[i] = nodoMenor;
		}
	    }

	    nodosRestantes.remove(nodoMenor);
	}

	System.out.println("Vector de costos:");
	for (int i = 0; i < res.length; i++)
	    System.out.print(res[i] + " ");
	System.out.println();

	System.out.println("Vector de caminos:");
	for (int i = 0; i < camino.length; i++)
	    System.out.print(camino[i] + " ");
	System.out.println();

	List<Integer> listaFinal = new LinkedList<Integer>();
	int c = camino[nodoDestino];

	listaFinal.add(nodoDestino);
	while (c != nodoPartida) {
	    listaFinal.add(c);
	    c = camino[c];
	}
	listaFinal.add(nodoPartida);

	if (listaFinal.size() == 2)
	    System.out.println("El camino es directo");
	else {
	    System.out.println("El camino es: ");
	    for (int i = listaFinal.size() - 1; i >= 0; i--)
		System.out.print(listaFinal.get(i) + " ");
	    System.out.println();
	}
    }

    public void floyd() {
	int tam = matriz.length;
	int[][] matrizActual = matriz.clone();
	int[][] matrizPrevia = matriz.clone();
	int k = 0;

	while (k < tam) {
	    for (int i = 0; i < tam; i++) {
		for (int j = 0; j < tam; j++) {
		    // me aseguro de no modificar la columna y fila que coincide
		    // con k
		    if (i != k && j != k && i != j && matrizPrevia[i][k] + matrizPrevia[k][j] < matrizActual[i][j])
			matrizActual[i][j] = matrizPrevia[i][k] + matrizPrevia[k][j];
		}
	    }

	    matrizPrevia = matrizActual.clone();
	    k++;
	}

	// muestro floydcito
	System.out.println("Floyd:");
	for (int i = 0; i < tam; i++) {
	    for (int j = 0; j < tam; j++)
		System.out.print(matrizActual[i][j] + " ");
	    System.out.println();
	}
    }

    public void prim(int nodoOrigen) throws Exception {
	nodoOrigen--;
	Set<Integer> v = new HashSet<Integer>();
	Queue<Arista> cola = new PriorityQueue<Arista>();
	List<Arista> aristasFinales = new LinkedList<Arista>();
	int pesoTotal = 0;

	// saco el nodo de partida
	v.add(nodoOrigen);

	// acolo las aristas
	Arista[] aristas = getAristas(nodoOrigen);

	for (int i = 0; i < aristas.length; i++)
	    cola.add(aristas[i]);

	while (!cola.isEmpty()) {
	    Arista arista = cola.poll();
	    int nodoDestino = arista.getNodoDestino();
	    // pregunto si el nodo destino de la arista ya fue visitado
	    if (!v.contains(nodoDestino)) {
		aristasFinales.add(arista);
		pesoTotal += arista.getPeso();
		v.add(nodoDestino);
		// agrego aristas adyacentes
		aristas = getAristas(nodoDestino);

		for (int i = 0; i < aristas.length; i++)
		    cola.add(aristas[i]);
	    }
	}

	System.out.println("Peso total: " + pesoTotal);
	for (int i = 0; i < aristasFinales.size(); i++)
	    System.out.println(aristasFinales.get(i));
    }
    
    public void coloreoM ()
    {
	// secuencia de Matula, ordeno el vector de nodos con el de menor grado primero
	Nodo[] nodos = getNodos();
	Arrays.sort(nodos);
	
	coloreo(nodos);
    }
    
    public void coloreoWP ()
    {
	// secuencia de Welsh-Powell, ordeno el vector de nodos con el de mayor grado primero
	Nodo[] nodos = getNodos();
	Arrays.sort(nodos, Collections.reverseOrder());
	
	/*for (int i = 0; i < nodos.length; i++)
	    System.out.println("Nodo " + i + ": " + nodos[i]);*/
	coloreo(nodos);
    }
    
    public void coloreo (Nodo[] nodos)
    {
	int tam = nodos.length;
	
	// vector de colores
	int[] colores = new int[tam];
	// vector que indica que colores puedo pintar
	boolean[] puedoPintar = new boolean[tam];
	for (int i = 0; i < tam; i++)
	    puedoPintar[i] = true;
	
	// inicializo vectores, -1 es "sin color"
	for (int i = 1; i < tam; i++)
	{
	    colores[i] = -1;
	    puedoPintar[i] = true;
	}
	// asigno el color 0 al primer nodo
	colores[nodos[0].getNombre()] = 0;
	
	// arranco desde el 1 porque el primer nodo ya tiene asignado el 0
	for (int i = 1; i < tam; i++)
	{
	    // busca en las aristas adyacentes los nodos destino
	    // y marco sus colores como no pintables
	    for(Arista a : nodos[i].getAristas()) {
		int nodoDestino = a.getNodoDestino();
		if (colores[nodoDestino] != -1)
		    puedoPintar[colores[nodoDestino]] = false;
	    }
	    // busco algun color que pueda utilizar para pintar
	    // agarro el primero que encuentro
	    int j = 0;
	    while(!puedoPintar[j])
		j++;
	    
	    // j es el número de color que puedo utilizar, se lo asigno al
	    // vector de colores
	    colores[nodos[i].getNombre()] = j;
	    
	    // reinicio el vector puedoPintar para la próxima iteración
	    for (int k = 0; k < tam; k++)
		    puedoPintar[k] = true;
	}
	
	// muestro resultado
	System.out.println("Colores: ");
	for (int i = 0; i < tam; i++)
	    System.out.println("Nodo " + nodos[i].getNombre() + ", Color: " + colores[nodos[i].getNombre()]);
    }
}
