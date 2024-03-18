package Interfaces;

import Interpretador.Aritmeticas;

public interface IInterpretador {
    /**
     * Procesa la expresión Lisp proporcionada y devuelve un valor de token.
     * 
     * @param expresionFormateada la expresión Lisp a procesar.
     * @return El valor resultante de la expresión Lisp.
     * @throws Exception si ocurre algún error durante el procesamiento.
     */
    Aritmeticas evaluar(String expresionFormateada) throws Exception;

    /**
     * Procesa la expresión Lisp proporcionada y devuelve un valor de token.
     * 
     * @param expresionFormateada la expresión Lisp a procesar.
     * @param ctx el contexto a utilizar para procesar la expresión.
     * @return El valor resultante de la expresión Lisp.
     * @throws Exception si ocurre algún error durante el procesamiento.
     */
    Aritmeticas evaluar(String expresionFormateada, IMetodos ctx) throws Exception;
}
