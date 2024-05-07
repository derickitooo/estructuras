import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class HuffmanMain {
    public static void main(String[] args) {
        String rutaTexto = "texto.txt";
        try {
            String texto = new String(Files.readAllBytes(Paths.get(rutaTexto)));
            ArbolHuffman arbol = Huffman.construirArbol(texto);
            Map<Character, String> codigos = Huffman.generarCodigos(arbol.raiz);
            String textoComprimido = Huffman.comprimir(texto, codigos);

            guardarArbolHuffman(arbol, "arbol.tree");
            guardarTextoComprimido(textoComprimido, "texto.huff");

            System.out.println("Compresión completada. Archivos 'texto.huff' y 'arbol.tree' creados.");
        } catch (IOException e) {
            System.err.println("Error al procesar los archivos: " + e.getMessage());
        }
    }

    public static void guardarArbolHuffman(ArbolHuffman arbol, String rutaArchivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            out.writeObject(arbol);
        }
    }

    public static void guardarTextoComprimido(String textoComprimido, String rutaArchivo) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(rutaArchivo)) {
            byte b = 0;
            int bitCount = 0;
            for (int i = 0; i < textoComprimido.length(); i++) {
                b <<= 1;
                if (textoComprimido.charAt(i) == '1') {
                    b |= 1;
                }
                bitCount++;
                if (bitCount == 8) {
                    fos.write(b);
                    bitCount = 0;
                    b = 0;
                }
            }
            if (bitCount > 0) {
                b <<= (8 - bitCount);
                fos.write(b);
            }
        }
    }
}
