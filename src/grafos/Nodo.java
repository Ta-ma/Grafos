package grafos;

import java.util.Arrays;

public class Nodo implements Comparable<Nodo>{
    private Arista[] aristas;
    private int nombre;
    
    public Nodo (int num, Arista[] aristas) {
	this.nombre = num;
	this.aristas = aristas;
    }

    public Arista[] getAristas() {
        return aristas;
    }

    public void setAristas(Arista[] aristas) {
        this.aristas = aristas;
    }

    @Override
    public int compareTo(Nodo o) {
	return ((Integer)this.aristas.length).compareTo(o.aristas.length);
    }

    @Override
    public String toString() {
	return "Nodo [aristas=" + Arrays.toString(aristas) + ", num=" + nombre + "]";
    }

    /**
     * @return the nombre
     */
    public int getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
    
    // boludo

}
