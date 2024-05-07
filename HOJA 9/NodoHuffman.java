import java.io.Serializable;

public class NodoHuffman implements Serializable, Comparable<NodoHuffman> {
    char caracter;
    int frecuencia;
    NodoHuffman izquierdo;
    NodoHuffman derecho;

    NodoHuffman(char caracter, int frecuencia, NodoHuffman izquierdo, NodoHuffman derecho) {
        this.caracter = caracter;
        this.frecuencia = frecuencia;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    @Override
    public int compareTo(NodoHuffman otro) {
        return this.frecuencia - otro.frecuencia;
    }

    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }
}
