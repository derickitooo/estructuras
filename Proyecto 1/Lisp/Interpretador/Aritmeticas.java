package Interpretador;

import java.util.List;
import java.util.function.BooleanSupplier;


/**
 * Clase que representa valores de Lisp que pueden ser números, booleanos, cadenas o listas.
 */
public class Aritmeticas implements BooleanSupplier{
    private Object siguiente = null;

    /**
     * Crea un valor Lisp que es un número.
     * 
     * @param n el número interno en Java
     * @return el valor Lisp de tipo número.
     */
    public static Aritmeticas nValor(float n) {
        return new Aritmeticas(n);
    }

    /**
     * Crea un valor Lisp que es un booleano.
     * 
     * @param b el booleano interno en Java
     * @return el valor Lisp de tipo booleano.
     */
    public static Aritmeticas valorBooleano(boolean b) {
        if (b) {
            return new Aritmeticas(b);
        } else {
            return Aritmeticas.lispNull();
        }
    }

    /**
     * 
     * @return un valor Lisp de tipo nil. Nil en Lisp es tanto nulo como falso.
     */
    public static Aritmeticas lispNull() {
        return new Aritmeticas(null);
    }

    /**
     * Crea un valor Lisp de tipo lista.
     * 
     * @param args todos los otros valores Lisp que son miembros de esta lista.
     * @return el valor Lisp de tipo lista.
     */
    public static Aritmeticas valorString(String s) {
        return new Aritmeticas(s);
    }

    /**
     * Creates a lisp value of type list.
     * 
     * @param args all the other lisp values that are members of this list.
     * @return the lisp value of type list.
     */
    public static Aritmeticas Listas(List<Aritmeticas> args) {
        return new Aritmeticas(args);
    }

    /**
     * Constructor
     * @param valor
     */
    private Aritmeticas(Object valor) {
        siguiente = valor;
    }

     /**
     * 
     * @return El objeto interno en Java que guarda el valor de este valor Lisp.
     */
    public Object getSiguiente() {
        return siguiente;
    }

    /**
     * Convierte el objeto interno en Java al tipo suministrado y lo devuelve. Esto puede
     * ser útil para reducir el boilerplate. Ten en cuenta que esto no comprueba si el
     * valor se puede convertir, por lo que fallará en caso de una conversión inválida.
     * 
     * @param <T> El tipo al que se convertirá el valor interno.
     * @return El valor interno convertido al tipo dado.
     */
    public <T> T get() {
        return (T) siguiente;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Aritmeticas) {
            Object loQueSigue = ((Aritmeticas) obj).getSiguiente();
            if (loQueSigue == null && siguiente == null) {
                return true;
            }
            if (siguiente.equals(loQueSigue)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return siguiente.toString();
    }

    /**
     * Suma dos valores Aritmeticas.
     * 
     * @param value el valor a sumar.
     * @return el valor Lisp resultante.
     */
    public Aritmeticas mas(Aritmeticas valor) {
        float a = this.get();
        float b = valor.get();

        return Aritmeticas.nValor(a + b);
    }

    /**
     * Resta dos valores Aritmeticas.
     * 
     * @param value el valor a restar.
     * @return el valor Lisp resultante.
     */
    public Aritmeticas menos(Aritmeticas valor) {
        float a = this.get();
        float b = valor.get();

        return Aritmeticas.nValor(a - b);
    }

    /**
     * Divide dos valores Lisp.
     * 
     * @param value el valor por el cual dividir.
     * @return el valor Lisp resultante.
     */
    public Aritmeticas dividir(Aritmeticas valor) {
        float a = this.get();
        float b = valor.get();

        return Aritmeticas.nValor(a / b);
    }

    /**
     * Multiplica dos valores Lisp.
     * 
     * @param value el valor por el cual multiplicar.
     * @return el valor Lisp resultante.
     */
    public Aritmeticas multiplicacion(Aritmeticas valor) {
        float a = this.get();
        float b = valor.get();

        return Aritmeticas.nValor(a * b);
    }

    /**
     * Comprueba si el valor Lisp actual es menor que el valor suministrado.
     * 
     * @param value el valor a comparar
     * @return un valor Aritmeticas verdadero si es menor que el otro
     */
    public Aritmeticas menorQue(Aritmeticas valor) {

        float a = this.get();
        float b = valor.get();

        var isLess = a < b;

        return Aritmeticas.valorBooleano(isLess);

    }

    /**
     * Comprueba si este valor Lisp es un átomo.
     * 
     * @return un valor Aritmeticas verdadero si es un átomo, un valor Aritmeticas falso en caso contrario
     */
    public Aritmeticas Atomo() {

        var tipoLista = siguiente instanceof List;

        return Aritmeticas.valorBooleano(!tipoLista);

    }

    /**
     * Comprueba si los dos valores Lisp son listas y si son iguales entre sí.
     * 
     * @param otroValor el otro valor Aritmeticas a comprobar
     * @return verdadero si son iguales, falso en caso contrario.
     */
    public Aritmeticas listasIguales(Aritmeticas otroValor) {

        if (siguiente instanceof List && otroValor.getSiguiente() instanceof List) {
            List<Aritmeticas> a = (List<Aritmeticas>) siguiente;
            List<Aritmeticas> b = (List<Aritmeticas>) otroValor.getSiguiente();
            
            return Aritmeticas.valorBooleano(a.equals(b));
        } else {
            return Aritmeticas.valorBooleano(false);
        }
    }

    @Override
    public boolean getAsBoolean() {
        return get();
    }

    /**
     * Comprueba si el valor actual es menor o igual que el valor suministrado.
     * 
     * @param value el valor a comparar
     * @return un valor Aritmeticas verdadero si es menor o igual, falso en caso contrario.
     */
    public Aritmeticas menorIgual(Aritmeticas value) {
        float a = this.get();
        float b = value.get();
        float diff = b - a;

        boolean isGreaterThanOrEqualToZero = diff >= 0;

        Aritmeticas result = Aritmeticas.valorBooleano(isGreaterThanOrEqualToZero);

        return result;
    }

    /**
     * Comprueba si el valor actual es mayor o igual que el valor suministrado.
     * 
     * @param value el valor a comparar
     * @return un valor Aritmeticas verdadero si es mayor o igual, falso en caso contrario.
     */
    public Aritmeticas mayorIgual(Aritmeticas value) {
        float a = this.get();
        float b = value.get();
        
        int comparisonResult = Float.compare(a, b);

        return Aritmeticas.valorBooleano(comparisonResult >= 0);
    }
}
