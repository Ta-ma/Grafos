package grafos;

import java.io.File;
import java.io.FileNotFoundException;

public class Test {

    public static void main(String[] args) throws Exception {
	File dir = new File(".//IN//");
	System.out.println(dir.getName());
	File[] archs = dir.listFiles();

	for (File arch : dir.listFiles()) {
	    GrafoD grafo = new GrafoD(arch);

	    grafo.coloreoM();
	    /*try {
		grafo.prim(4);
	    } catch (Exception e) {

	    }*/

	    // grafo.mostrar();
	    // grafo.dijkstra(1, 3);
	    // grafo.floyd();
	}

    }

}
