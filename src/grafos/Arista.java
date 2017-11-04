package grafos;

public class Arista implements Comparable<Arista> {
	private int nodoOrigen;
	private int nodoDestino;
	private int peso;
	
	public Arista(int nodoOrigen, int peso, int nodoDestino) {
		super();
		this.nodoOrigen = nodoOrigen;
		this.nodoDestino = nodoDestino;
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Arista [nodoOrigen=" + (nodoOrigen + 1) + ", nodoDestino=" + (nodoDestino + 1) + ", peso=" + peso + "]";
	}

	public int getNodoOrigen() {
		return nodoOrigen;
	}

	public void setNodoOrigen(int nodoOrigen) {
		this.nodoOrigen = nodoOrigen;
	}

	public int getNodoDestino() {
		return nodoDestino;
	}

	public void setNodoDestino(int nodoDestino) {
		this.nodoDestino = nodoDestino;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Arista o) {
		return this.peso - o.getPeso();
	}

}