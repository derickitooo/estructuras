package Interpretador;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import Interfaces.IMetodos;
import metodos.MetodosDefinidos;
import metodos.MetodosUsuario;
import metodos.InformacionMetodos;

/**
 * La clase Metodos implementa la interfaz IMetodos y proporciona métodos para el manejo de métodos y valores en Lisp.
 */
public class Metodos implements IMetodos {

    private HashMap<String, MetodosDefinidos> fundamentalMethods;
    private HashMap<String, Aritmeticas> values = new HashMap<>();
    private HashMap<String, MetodosUsuario> userMethods = new HashMap<>();
    
    /**
     * Crea un contexto de métodos basado en los nombres de argumentos y los valores dados.
     * 
     * @param argsNames los nombres de los argumentos
     * @param args los valores de los argumentos
     * @return un objeto IMetodos que representa el contexto de métodos creado
     */
    public static IMetodos Argumentos(List<String> argsNames, List<Aritmeticas> args) {
        IMetodos metodos = metodosLisp();

        if (argsNames.isEmpty()) {
            return metodos;
        }

        if (argsNames.get(0).startsWith("..")) {
            metodos.agregarValor(argsNames.get(0), Aritmeticas.Listas(args));
        } else {
            IntStream.range(0, argsNames.size())
                    .forEach(i -> metodos.agregarValor(argsNames.get(i), args.get(i)));
        }
        return metodos;
    }

    /** 
     * Constructor privado utilizado para inicializar la clase con los métodos fundamentales de Lisp.
     * 
     * @param methods los métodos fundamentales de Lisp
     */
    private Metodos(HashMap<String, MetodosDefinidos> methods) {
        this.fundamentalMethods = methods;
    }

    @Override
    public boolean isMetodo(String procedure) {
        return this.fundamentalMethods.containsKey(procedure);
    }

    @Override
    public MetodosDefinidos obtenerMetodos(String procedure) {
        return this.fundamentalMethods.get(procedure);
    }

    @Override
    public void agregarValor(String valueName, Aritmeticas Aritmeticas) {
        this.values.put(valueName, Aritmeticas);
    }

    @Override
    public MetodosUsuario metodosCreadosUsuario(String procedureName) {
        return this.userMethods.get(procedureName);
    }

    @Override
    public Aritmeticas obtenerValor(String valueName) {
        return this.values.get(valueName);
    }

    @Override
    public void agregarMetodoUsuario(String procedureName, MetodosUsuario procedure) {
        this.userMethods.put(procedureName, procedure);

    }

    @Override
    public Iterable<Entry<String, Aritmeticas>> obtenerValores() {
        return this.values.entrySet();
    }

    @Override
    public Iterable<Entry<String, MetodosUsuario>> obtenerMetodosCreadosUsuario() {
        return this.userMethods.entrySet();
    }

    @Override
    public IMetodos agregarMetodos(IMetodos otro) {
        for (var entry : otro.obtenerMetodosCreadosUsuario()) {
            this.userMethods.put(entry.getKey(), entry.getValue());
        }

        return this;
    }

    /**
     * Crea un contexto de métodos con los métodos definidos de Lisp.
     * 
     * @return un objeto IMetodos que representa el contexto de métodos con los métodos fundamentales de Lisp
     */
    private static IMetodos metodosLisp() {
        List<InformacionMetodos> informacionMetodos = Arrays.asList(
            new InformacionMetodos("+", Arrays.asList("a", "b"), metodos -> metodos.obtenerValor("a").mas(metodos.obtenerValor("b"))),
            new InformacionMetodos("-", Arrays.asList("a", "b"), metodos -> metodos.obtenerValor("a").menos(metodos.obtenerValor("b"))),
            new InformacionMetodos("*", Arrays.asList("a", "b"), metodos -> metodos.obtenerValor("a").multiplicacion(metodos.obtenerValor("b"))),
            new InformacionMetodos("/", Arrays.asList("a", "b"), metodos -> metodos.obtenerValor("a").dividir(metodos.obtenerValor("b"))),
            new InformacionMetodos("=", Arrays.asList("a", "b"), metodos -> Aritmeticas.valorBooleano(metodos.obtenerValor("a").equals(metodos.obtenerValor("b")))),
            new InformacionMetodos("<", Arrays.asList("a", "b"), metodos -> metodos.obtenerValor("a").menorQue(metodos.obtenerValor("b"))),
            new InformacionMetodos(">", Arrays.asList("a", "b"), metodos -> metodos.obtenerValor("b").menorQue(metodos.obtenerValor("a"))),
            new InformacionMetodos("print", Collections.singletonList("value"), metodos -> {
                System.out.println(metodos.obtenerValor("value"));
                return Aritmeticas.lispNull();
            }),
            new InformacionMetodos("atom", Collections.singletonList("value"), metodos -> metodos.obtenerValor("value").Atomo()),
            new InformacionMetodos("list", Collections.singletonList("..values"), metodos -> metodos.obtenerValor("..values")),
            new InformacionMetodos("<=", Arrays.asList("a", "b"), metodos -> metodos.obtenerValor("a").menorIgual(metodos.obtenerValor("b"))),
            new InformacionMetodos(">=", Arrays.asList("a", "b"), metodos -> metodos.obtenerValor("a").mayorIgual(metodos.obtenerValor("b"))),
            new InformacionMetodos("equal", Arrays.asList("a", "b"), metodos -> {
                return Aritmeticas.valorBooleano(metodos.obtenerValor("a").equals(metodos.obtenerValor("b"))
                        || metodos.obtenerValor("a").listasIguales(metodos.obtenerValor("b")).getAsBoolean());
            })
        );

        HashMap<String, MetodosDefinidos> metodos = new HashMap<>();
        for (InformacionMetodos info : informacionMetodos) {
            metodos.put(info.getOperadores(), new MetodosDefinidos(info.getNombreArgumentos(), info.getInterno()));
        }

        return new Metodos(metodos);
    }
}