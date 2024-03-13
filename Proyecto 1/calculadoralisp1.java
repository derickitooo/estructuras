import java.util.*;
import java.util.ArrayList;
public class calculadoralisp1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la expresión en notación postfija:");
        String expresion = scanner.nextLine();

        // Separar la expresión en elementos
        String[] elementos = expresion.split("+" );

        // Realizar operaciones en la expresión postfija
        int resultado = resolverPostfija(elementos);

        System.out.println("Resultado: " + resultado);
    }

    public static int resolverPostfija(String[] elementos) {
        //List<Integer> numeros = new ArrayList<>();

        // Realizar operaciones en la expresión postfija
        for (String elemento : elementos) {
            if (elemento.equals("+")) {
                // Sumar los dos últimos números y agregar el resultado
                int operand2;
                operand2 = elementos.removeLast();
                int operand1;
                operand1 = elementos.remove(elementos.size() - 1);
                int resultado;
                resultado = operand1 + operand2;
                elementos.add(resultado);
            }
        }

        // El resultado final estará en la última posición de la lista
        return numeros.getFirst();
    }
}
