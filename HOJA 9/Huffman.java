import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
    
    public static ArbolHuffman construirArbol(String texto) {
        int[] frecuencias = new int[256];
        for (char c : texto.toCharArray()) {
            frecuencias[c]++;
        }

        PriorityQueue<NodoHuffman> heap = new PriorityQueue<>();
        for (char i = 0; i < 256; i++) {
            if (frecuencias[i] > 0) {
                heap.add(new NodoHuffman(i, frecuencias[i], null, null));
            }
        }

        while (heap.size() > 1) {
            NodoHuffman izquierdo = heap.poll();
            NodoHuffman derecho = heap.poll();
            NodoHuffman padre = new NodoHuffman('\0', izquierdo.frecuencia + derecho.frecuencia, izquierdo, derecho);
            heap.add(padre);
        }

        return new ArbolHuffman(heap.poll());
    }

    public static Map<Character, String> generarCodigos(NodoHuffman raiz) {
        if (raiz == null) return null;
        Map<Character, String> codigos = new HashMap<>();
        generarCodigosRecursivo(raiz, "", codigos);
        return codigos;
    }

    private static void generarCodigosRecursivo(NodoHuffman nodo, String s, Map<Character, String> codigos) {
        if (!nodo.esHoja()) {
            generarCodigosRecursivo(nodo.izquierdo, s + '0', codigos);
            generarCodigosRecursivo(nodo.derecho, s + '1', codigos);
        } else {
            codigos.put(nodo.caracter, s);
        }
    }

    public static String comprimir(String texto, Map<Character, String> codigos) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : texto.toCharArray()) {
            stringBuilder.append(codigos.get(c));
        }
        return stringBuilder.toString();
    }

    public static String descomprimir(String textoBinario, ArbolHuffman arbol) {
        StringBuilder resultado = new StringBuilder();
        NodoHuffman nodoActual = arbol.raiz;
        for (int i = 0; i < textoBinario.length(); i++) {
            if (textoBinario.charAt(i) == '0') {
                nodoActual = nodoActual.izquierdo;
            } else {
                nodoActual = nodoActual.derecho;
            }
            if (nodoActual.esHoja()) {
                resultado.append(nodoActual.caracter);
                nodoActual = arbol.raiz;
            }
        }
        return resultado.toString();
    }
}
