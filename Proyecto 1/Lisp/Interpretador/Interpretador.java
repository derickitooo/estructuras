package Interpretador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import Interfaces.IMetodos;
import Interfaces.IInterpretador;
import metodos.MetodosUsuario;


/**
 * La clase Interpretador implementa la interfaz IInterpretador y proporciona
 * métodos para evaluar expresiones en Lisp.
 */
public class Interpretador implements IInterpretador {

    IMetodos contexto = Metodos.Argumentos(new ArrayList<>(), new ArrayList<>());
    ArgumentosRecorrerExpresion rastreadorParentesis = new ArgumentosRecorrerExpresion(c -> c == '(', Interpretador::argumentosParentesis);

    ArrayList<ArgumentosRecorrerExpresion> rastreadores = new ArrayList<>(Arrays.asList(
        new ArgumentosRecorrerExpresion(Character::isDigit, cadena -> new RecorrerExpresion(obtenerStringHasta(cadena, "[ \\(\\)]").length(), obtenerStringHasta(cadena, "[ \\(\\)]"))),
        rastreadorParentesis,
        new ArgumentosRecorrerExpresion(c -> c == '"', cadena -> {
            boolean finalCadena = false;
            for (int i = 1; i < cadena.length(); i++) {
                char ch = cadena.charAt(i);
                if (ch == '"') {
                    finalCadena = true;
                }
                if (finalCadena) {
                    return new RecorrerExpresion(i + 1, cadena.substring(0, i + 1));
                }
            }
            return new RecorrerExpresion(cadena.length(), cadena);
        }),
        new ArgumentosRecorrerExpresion(c -> true, cadena -> new RecorrerExpresion(obtenerStringHasta(cadena, " ").length(), obtenerStringHasta(cadena, " ")))
    ));

    /** 
     * Método utilizado para obtener las expresiones Lisp contenidas entre paréntesis.
     * 
     * @param expression la expresión completa de Lisp
     * @return un objeto RecorrerExpresion que contiene la expresión Lisp obtenida.
     */
    private static RecorrerExpresion argumentosParentesis(String expression) {
        int contadorParentesisAbiertos = 0;
        int contadorParentesisCerrados = 0;
        int finIndex = expression.length();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                contadorParentesisAbiertos++;
            } else if (ch == ')') {
                contadorParentesisCerrados++;
                if (contadorParentesisAbiertos == contadorParentesisCerrados) {
                    finIndex = i + 1;
                    break;
                }
            }
        }

        return new RecorrerExpresion(finIndex, expression.substring(0, finIndex));
    }


    @Override
    public Aritmeticas evaluar(String expresionFormateada) throws Exception {
        return evaluar(expresionFormateada, contexto);
    }

     /**
     * Crea un objeto Aritmeticas a partir de una cadena.
     * 
     * @param expresionFormateada la cadena a convertir en objeto Aritmeticas
     * @return el objeto Aritmeticas creado a partir de la cadena
     */
    private Aritmeticas quote(String expresionFormateada) {
        expresionFormateada = arreglarFin(expresionFormateada);
        expresionFormateada = expresionFormateada.substring(6);
        return Aritmeticas.valorString(expresionFormateada);
    }

    /**
     * Maneja la expresión de asignación (setq) y actualiza el contexto con el nuevo valor.
     * 
     * @param expresionFormateada la expresión de asignación (setq)
     * @param ctx el contexto en el que se actualizará la asignación
     * @return el resultado de la evaluación de la expresión asignada
     */
    private Aritmeticas setq(String expresionFormateada, IMetodos ctx) {
        try{
            expresionFormateada = arreglarFin(expresionFormateada);
            expresionFormateada = expresionFormateada.substring(5);
            var spaceIndex = expresionFormateada.indexOf(" ");
            var name = expresionFormateada.substring(0, spaceIndex);
            var arg = expresionFormateada.substring(spaceIndex + 1);
            var resultado = evaluar(arg, ctx);
            ctx.agregarValor(name, resultado);
            return resultado;
        } catch (Exception e){
            e.printStackTrace();
            return Aritmeticas.valorBooleano(false);
        }
        
    }

    /**
     * Maneja la definición de funciones (defun) y agrega la nueva función al contexto.
     * 
     * @param expresionFormateada la expresión de definición de función (defun)
     * @param ctx el contexto en el que se agregará la nueva función
     * @return un objeto Aritmeticas nulo
     */
    private Aritmeticas defun(String expresionFormateada, IMetodos ctx) {
        var quitarPalabrasClaves = expresionFormateada.substring(7);
        var Index = quitarPalabrasClaves.indexOf(' ');
        var nombMetodo = quitarPalabrasClaves.substring(0, Index);
        var quitarNombreMetodo = quitarPalabrasClaves.substring(Index + 1);
        var argsEnd = quitarNombreMetodo.indexOf(')');
        var argsNombres = arreglarFin(quitarNombreMetodo.substring(0, argsEnd + 1)).split(",");
        var cuerpo = quitarNombreMetodo.substring(argsEnd + 2, quitarNombreMetodo.length() - 1);

        var userProc = new MetodosUsuario(Arrays.asList(argsNombres), cuerpo);
        ctx.agregarMetodoUsuario(nombMetodo, userProc);
        return Aritmeticas.lispNull();
    }


    /**
     * Maneja la expresión condicional (cond) y evalúa las condiciones hasta encontrar una verdadera.
     * 
     * @param expresionFormateada la expresión condicional (cond)
     * @param ctx el contexto en el que se evaluarán las condiciones
     * @return el resultado de la evaluación de la expresión que cumple la condición verdadera
     */
    private Aritmeticas cond(String expresionFormateada, IMetodos ctx) {
        try {
            String quitarPalabrasClaves = expresionFormateada.substring(6);
                HashMap<String, String> conditions = new HashMap<>();
                for (int i = 0; i < quitarPalabrasClaves.length(); i++) {
                    char ch = quitarPalabrasClaves.charAt(i);
                    if (rastreadorParentesis.sePuedeRastrear(ch)) {
                        var result = rastreadorParentesis.rastrear(quitarPalabrasClaves, i);
                        String quitarFinal = arreglarFin(result.getResult());
                        List<String> extraerArgumentos = obtnerArgumentos(quitarFinal);
                        conditions.put(extraerArgumentos.get(0), extraerArgumentos.get(1));
                        i += result.getCantidadDatosLeidos();
                    }
                }

                for (var condition : conditions.keySet()) {
                    var result = evaluar(condition, ctx);
                    if (Aritmeticas.valorBooleano(true).equals(result)) {
                        return evaluar(conditions.get(condition), ctx);
                    }
                }
                return Aritmeticas.lispNull();
        } catch (Exception e) {
            e.printStackTrace();
            return Aritmeticas.valorBooleano(false);
        }
    }


    /**
     * Maneja las llamadas a procedimientos y evalúa las expresiones contenidas.
     * 
     * @param expresionFormateada la expresión de llamada a procedimientos
     * @param ctx el contexto en el que se evaluarán las expresiones
     * @return el resultado de la evaluación de la expresión de procedimientos
     */
    private Aritmeticas procedimientos(String expresionFormateada, IMetodos ctx) {
        try {
            String exp = arreglarFin(expresionFormateada);
            String procedureName = nombreProcedimiento(exp);
            List<Aritmeticas> args = evaluarArgumentos(exp, ctx);
            if (ctx.isMetodo(procedureName)) {
                return ctx.obtenerMetodos(procedureName).apply(args);
            } else {
                MetodosUsuario method = ctx.metodosCreadosUsuario(procedureName);
                IMetodos subContext = method.createContext(args, ctx);
                return evaluar(method.getExpression(), subContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Aritmeticas.valorBooleano(false);
        }
    }


    @Override
    public Aritmeticas evaluar(String expresionFormateada, IMetodos ctx) throws Exception {
        try {
            Float number = Float.parseFloat(expresionFormateada);
            return Aritmeticas.nValor(number);
        } catch (Exception e) {

        }
        if (expresionFormateada.toLowerCase().equals("t")) {
            return Aritmeticas.valorBooleano(true);
        } else if (expresionFormateada.equals("()")) {
            return Aritmeticas.lispNull();
        } else if (expresionFormateada.startsWith("\"")) {
            var exp = arreglarFin(expresionFormateada);
            return Aritmeticas.valorString(exp);
        } else if (expresionFormateada.startsWith("(quote")) {
            return quote(expresionFormateada);
        } else if (expresionFormateada.startsWith("(setq")) {
            return setq(expresionFormateada, ctx);
        } else if (expresionFormateada.startsWith("(defun")) {
            return defun(expresionFormateada, ctx);
        } else if (expresionFormateada.startsWith("(cond")) {
            return cond(expresionFormateada, ctx);
        } else if (expresionFormateada.startsWith("(")) {
            return procedimientos(expresionFormateada, ctx);
        } else {
            return ctx.obtenerValor(expresionFormateada);
        }
    }

    /**
     * Obtiene una subcadena de la cadena dada hasta que se encuentra con el patrón especificado.
     * 
     * @param cadena la cadena de la que se obtendrá la subcadena
     * @param hasta el patrón de término para la búsqueda de la subcadena
     * @return la subcadena obtenida hasta que se encuentra el patrón especificado
     */
    private String obtenerStringHasta(String cadena, String hasta) {
        Pattern p = Pattern.compile(hasta);
        var m = p.matcher(cadena);
        if (m.find()) {
            var end = m.start();
            return cadena.substring(0, end);
        }

        return cadena;
    }

    /**
     * Obtiene los argumentos de la cadena de argumentos dada y los devuelve como una lista de cadenas.
     * 
     * @param stringOfArgs la cadena de argumentos de la que se extraerán los argumentos
     * @return una lista de cadenas que representan los argumentos extraídos
     */
    private List<String> obtnerArgumentos(String stringOfArgs) {
        ArrayList<String> args = new ArrayList<>();
        for (int i = 0; i < stringOfArgs.length(); i++) {
            char ch = stringOfArgs.charAt(i);
            for (ArgumentosRecorrerExpresion rastreador : rastreadores) {
                if (rastreador.sePuedeRastrear(ch)) {
                    RecorrerExpresion result = rastreador.rastrear(stringOfArgs, i);
                    i += result.getCantidadDatosLeidos();
                    args.add(result.getResult());
                    break;
                }
            }
        }

        return args;
    }

    /**
     * Evalúa los argumentos de la expresión dada y los devuelve como una lista de objetos Aritmeticas.
     * 
     * @param exp la expresión de la que se evaluarán los argumentos
     * @param ctx el contexto en el que se evaluarán los argumentos
     * @return una lista de objetos Aritmeticas que representan los argumentos evaluados
     * @throws Exception si ocurre un error durante la evaluación de los argumentos
     */
    private List<Aritmeticas> evaluarArgumentos(String exp, IMetodos ctx) throws Exception {
        var index = exp.indexOf(' ');
        ArrayList<Aritmeticas> args = new ArrayList<>();
        exp = exp.substring(index + 1);

        for (int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);
            for (var crawler : rastreadores) {
                if (crawler.sePuedeRastrear(ch)) {
                    var result = crawler.rastrear(exp, i);
                    i += result.getCantidadDatosLeidos();
                    args.add(evaluar(result.getResult(), ctx));
                    break;
                }
            }
        }

        return args;
    }

    /**
     * Obtiene el nombre del procedimiento de la expresión dada.
     * 
     * @param exp la expresión de la que se extraerá el nombre del procedimiento
     * @return el nombre del procedimiento
     */
    private String nombreProcedimiento(String exp) {
        var index = exp.indexOf(' ');
        return exp.substring(0, index);
    }

    /**
     * Elimina los caracteres de inicio y fin de la cadena dada.
     * 
     * @param cadena la cadena de la que se eliminarán los caracteres de inicio y fin
     * @return la cadena sin los caracteres de inicio y fin
     */
    private String arreglarFin(String cadena) {
        return cadena.substring(1, cadena.length() - 1);
    }
    
}
