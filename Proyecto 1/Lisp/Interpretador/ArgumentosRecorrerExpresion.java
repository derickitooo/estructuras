package Interpretador;

import java.util.function.Function;

/**
 * Clase que representa un mini parser para recorrer una expresión.
 */
public class ArgumentosRecorrerExpresion {
    private Function<Character, Boolean> tipoParse;
    private Function<String, RecorrerExpresion> index;

    /**
     * Constructor de una instancia de un mini parser.
     * 
     * @param Parseo Este lambda indica a quien esté utilizando este mini parser que
     *                      encontró algo que puede parsear.
     * @param rastrear      Este lambda procede a parsear realmente el token.
     */
    public ArgumentosRecorrerExpresion(Function<Character, Boolean> Parseo, Function<String, RecorrerExpresion> recorrer) {
        tipoParse = Parseo;
        index = recorrer;
    }

    /**
     * Se utiliza para verificar si este parser puede parsear la entrada dada.
     * 
     * @param ch el carácter de inicio que necesita el parser.
     * @return true si puede, false en caso contrario.
     */
    public boolean sePuedeRastrear(char ch) {
        return tipoParse.apply(ch);
    }

    /**
     * Realmente parsea el token de la expresión. Necesita un índice para comenzar
     * desde una posición de caracteres personalizada. Esto significa que no
     * necesariamente comienza desde el principio de la cadena.
     * 
     * @param exp La expresión a parsear.
     * @param i   Desde qué índice comenzar.
     * @return un RecorrerExpresion que contiene información adicional sobre el
     *         parsing y el token.
     */
    public RecorrerExpresion rastrear(String exp, int i) {
        return index.apply(exp.substring(i));
    }
}
