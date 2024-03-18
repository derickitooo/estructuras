package metodos;


import java.util.List;

import Interfaces.IMetodos;
import Interpretador.Metodos;
import Interpretador.Aritmeticas;


/**
 * UserDefinedMethod
 * 
 * Represents a user defined method. Contains all the necessary data to execute
 * one.
 */
public class MetodosUsuario {

    private String expression;
    private List<String> argsNames;

    /**
     * Constructs an instance of a UserDefinedMethod.
     * 
     * @param argsNames  a list of the names of the parameters that this method
     *                   needs.
     * @param expression the expression/body of the method already formatted.
     */
    public MetodosUsuario(List<String> argsNames, String expression) {
        this.argsNames = argsNames;
        this.expression = expression;
    }

    /**
     * 
     * @return The formatted body of the method.
     */
    public String getExpression() {
        return this.expression;
    }

    /**
     * Creates a custom context to execute this method on.
     * 
     * This custom context inherits every custom method from baseContext but not
     * it's values. This ensures that the method only has access to the arguments
     * supplied here.
     * 
     * @param args        the list of values that must be passed as parameters to
     *                    the method.
     * @param baseContext the base context in which this function was called in
     *                    lisp.
     * @return the local context of the method.
     */
    public IMetodos createContext(List<Aritmeticas> args, IMetodos baseContext) {
        return Metodos.Argumentos(this.argsNames, args).agregarMetodos(baseContext);
    }
}
