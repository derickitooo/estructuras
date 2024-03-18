package Interfaces;

import java.util.Map.Entry;

import Interpretador.Aritmeticas;
import metodos.MetodosDefinidos;
import metodos.MetodosUsuario;

public interface IMetodos {

    /**
     * Verifica si un nombre de procedimiento coincide con uno de los procedimientos fundamentales.
     * 
     * @param nombreProcedimiento el nombre a verificar.
     * @return true si coincide con uno, false en caso contrario.
     */
    boolean isMetodo(String procedure);


    /**
     * Obtiene el procedimiento fundamental con el nombre especificado.
     * 
     * @param nombreProcedimiento el nombre del procedimiento.
     * @return el procedimiento fundamental si se encuentra, null en caso contrario.
     */
    MetodosDefinidos obtenerMetodos(String procedure);

    /**
     * Agrega un valor Lisp al contexto.
     * 
     * @param nombreValor el nombre para registrar el valor.
     * @param valor el valor a registrar.
     */
    void agregarValor(String valueName, Aritmeticas Aritmeticas);

    /**
     * Agrega un método definido por el usuario al contexto.
     * 
     * @param nombreProcedimiento el nombre del procedimiento.
     * @param procedimiento el procedimiento a registrar.
     */
    void agregarMetodoUsuario(String procedureName, MetodosUsuario procedure);

    /**
     * Obtiene el método definido por el usuario.
     * 
     * @param nombreProcedimiento el nombre a buscar.
     * @return el método definido por el usuario.
     */
    MetodosUsuario metodosCreadosUsuario(String procedureName);

    /**
     * Obtiene un valor definido en el contexto.
     * 
     * @param nombreValor el nombre del valor a obtener.
     * @return el valor registrado para el nombre dado.
     */
    Aritmeticas obtenerValor(String valueName);

    /**
     * 
     * @return todas las entradas de valores en el contexto.
     */
    Iterable<Entry<String, Aritmeticas>> obtenerValores();

    /**
     * 
     * @return todas las entradas de métodos definidos por el usuario en el contexto.
     */
    Iterable<Entry<String, MetodosUsuario>> obtenerMetodosCreadosUsuario();

    /**
     * Agrega todos los métodos definidos por el usuario desde el contexto especificado.
     * 
     * @param otro el contexto para extraer todos los métodos
     * @return el IFundamentos resultante
     */
    IMetodos agregarMetodos(IMetodos otro);

}
