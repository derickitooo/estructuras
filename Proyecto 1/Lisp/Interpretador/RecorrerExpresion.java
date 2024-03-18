package Interpretador;

/**
 * La clase RecorrerExpresion representa el resultado de recorrer una expresión durante el análisis de tokens.
 */
public class RecorrerExpresion {
    private String resultado;
    private int cantidadDatosLeidos;

    /**
     * Constructor RecorrerExpresion.
     * 
     * @param consumedLength La longitud que se avanzó en la expresión para analizar el token.
     * @param result         El token analizado.
     */
    public RecorrerExpresion(int CantidadAvanzada, String token) {
        cantidadDatosLeidos = CantidadAvanzada;
        resultado = token;
    }

    /**
     * Obtiene la longitud que se avanzó en la expresión para analizar el token.
     * 
     * @return La longitud que se avanzó en la expresión.
     */
    public int getCantidadDatosLeidos() {
        return cantidadDatosLeidos;
    }

   /**
     * Obtiene el token analizado.
     * 
     * @return El token analizado.
     */
    public String getResult() {
        return resultado;
    }
}
