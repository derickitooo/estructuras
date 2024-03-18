package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Interpretador.Aritmeticas;
import Interpretador.Interpretador;

// Algoritmos y Estructuras de Datos
// Proyecto Interprete Lisp
// Derick Delva; Carné: 211669, Ian Castellanos; Carné: 22128

public class main {
    public static void main(String[] args) {
        Interpretador interpretador = new Interpretador();
        Scanner lector = new Scanner(System.in);

        List<String> lin = new ArrayList<String>();

        System.out.println("Por favor, ingrese linea por linea el codigo de lisp (escribe 'fin' para terminar):\nEjemplo:\n(setq x 10)\nx\nfin");
        while (true) {
            String linea = lector.nextLine();
            if (linea.equalsIgnoreCase("fin")) {
                break;
            }
            lin.add(linea);
        }

        List<String> lineas = new ArrayList<String>(); 

        String expresionActual = "";

        for (String linea : lin) {

            if (lin.contains(";")) {
                linea = linea.substring(0, linea.indexOf(";"));
            }
            linea = linea.trim();
            if (linea.isBlank() || linea.isEmpty()) {
                continue;
            }

            expresionActual += linea + "\n";

            if (expresionCompleta(expresionActual)) {
                lineas.add(expresionActual);
                expresionActual = "";
            }
        }

        if (!expresionActual.isEmpty()) {
            lineas.add(expresionActual.trim());
        }

        System.out.println("                                                                                                   \r\n" + 
                        "@@@@@@@   @@@@@@@@   @@@@@@   @@@  @@@  @@@       @@@@@@@   @@@@@@   @@@@@@@    @@@@@@    @@@@@@   \r\n" + 
                        "@@@@@@@@  @@@@@@@@  @@@@@@@   @@@  @@@  @@@       @@@@@@@  @@@@@@@@  @@@@@@@@  @@@@@@@@  @@@@@@@   \r\n" + 
                        "@@!  @@@  @@!       !@@       @@!  @@@  @@!         @@!    @@!  @@@  @@!  @@@  @@!  @@@  !@@       \r\n" + 
                        "!@!  @!@  !@!       !@!       !@!  @!@  !@!         !@!    !@!  @!@  !@!  @!@  !@!  @!@  !@!       \r\n" + 
                        "@!@!!@!   @!!!:!    !!@@!!    @!@  !@!  @!!         @!!    @!@!@!@!  @!@  !@!  @!@  !@!  !!@@!!    \r\n" + 
                        "!!@!@!    !!!!!:     !!@!!!   !@!  !!!  !!!         !!!    !!!@!!!!  !@!  !!!  !@!  !!!   !!@!!!   \r\n" + 
                        "!!: :!!   !!:            !:!  !!:  !!!  !!:         !!:    !!:  !!!  !!:  !!!  !!:  !!!       !:!  \r\n" + 
                        ":!:  !:!  :!:           !:!   :!:  !:!   :!:        :!:    :!:  !:!  :!:  !:!  :!:  !:!      !:!   \r\n" + 
                        "::   :::   :: ::::  :::: ::   ::::: ::   :: ::::     ::    ::   :::   :::: ::  ::::: ::  :::: ::   \r\n" + 
                        " :   : :  : :: ::   :: : :     : :  :   : :: : :     :      :   : :  :: :  :    : :  :   :: : :    \r\n" + 
                        "                                                                                                   ");
        lineas.stream()
            .map(expresion -> ArreglarExpresion(expresion))
            .forEach(expresionArreglada -> {
                try {
                    System.out.println("***************************************************************");
                    System.out.println("Expresion: "+expresionArreglada.trim());
                    
                    Aritmeticas valor = interpretador.evaluar(expresionArreglada);
                    var valorInterno = valor.getSiguiente();
                    if (valorInterno instanceof List) {
                        List<Aritmeticas> c = (List<Aritmeticas>) valorInterno;
                        System.out.println("(");
                        c.forEach(a -> System.out.println(a));
                        System.out.println(")");
                    } else {
                        System.out.println("Resultado: " + valorInterno);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        lector.close();
    }

    private static String ArreglarExpresion(String expresion) {
        String arreglo = "";

        arreglo = expresion.replace("\n", "");
        arreglo = arreglo.replace("\t", "");

        arreglo = arreglo.replace(") )", "))");
        arreglo = arreglo.replace(")(", ") (");
        while (arreglo.contains("  ")) {
            arreglo = arreglo.replace("  ", " ");
        }
        arreglo = arreglo.replace("( ", "(");

        return arreglo;
    }
    
    private static boolean expresionCompleta(String expresion) {
        int cont = 0;
        for (char character : expresion.toCharArray()) {
            if (character == '(') {
                cont++;
            } else if (character == ')') {
                cont--;
            }
        }
        return cont == 0;
    }
}
