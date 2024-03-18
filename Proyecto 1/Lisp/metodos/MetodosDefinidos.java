package metodos;


import java.util.List;
import java.util.function.Function;

import Interfaces.IMetodos;
import Interpretador.Aritmeticas;
import Interpretador.Metodos;


/**
 * FundamentalMethod
 * 
 * Represents a method that comes already on Lisp when we first start it. Like
 * addition and substraction.
 */
public class MetodosDefinidos {

    private final Function<IMetodos, Aritmeticas> internal;
    private final List<String> argsNames;

    /**
     * Constructs an instance of a FundamentalMethod.
     * 
     * @param argsNames A list with all the parameter names for this method to work.
     * @param internal  The internal java method that executes this functionality.
     *                  The supplied context should already have all parameters
     *                  binded in the context to the name provided above.
     */
    public MetodosDefinidos(List<String> argsNames, Function<IMetodos, Aritmeticas> internal) {
        this.argsNames = argsNames;
        this.internal = internal;
    }

    /**
     * Executes the method with the given values as parameters.
     * 
     * @param args The values to bind as parameters.
     * @return The result value of this method.
     */
    public Aritmeticas apply(List<Aritmeticas> args) {
        var local = Metodos.Argumentos(argsNames, args);
        return internal.apply(local);
    }

    /**
     * Get the argument names.
     * 
     * @return List of argument names.
     */
    public List<String> getArgsNames() {
        return argsNames;
    }
}
