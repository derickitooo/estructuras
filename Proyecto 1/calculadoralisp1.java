import java.util.Scanner;
import java.util.Stack;

//Usar Arrays para todo esto
public class calculadoralisp1 {

    public static int evaluate(String expression) {
        Stack<String> stack = new Stack<>();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '(') {
                stack.push("(");
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                stack.push(Character.toString(c));
            } else if (Character.isDigit(c)) {
                currentNumber.setLength(0);
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    currentNumber.append(expression.charAt(i));
                    i++;
                }
                stack.push(currentNumber.toString());
                i--;
            } else if (c == ')') {
                int result = 0;
                String operator = stack.pop();
                while (!operator.equals("(")) {

                    int num = Integer.parseInt(stack.pop());
                    if (operator.equals("+")) {
                        result += num;
                    } else if (operator.equals("-")) {
                        result -= num;
                    } else if (operator.equals("*")) {
                        result *= num;
                    } else if (operator.equals("/")) {
                        result /= num;
                    }
                    operator = stack.pop();
                }
                stack.push(Integer.toString(result));
            }
        }

        return Integer.parseInt(stack.pop());
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Ingrese una expresión en LISP: ");
            String expression = scanner.nextLine();
            int result = evaluate(expression);
            System.out.println("Resultado: " + result);
        }
    }
}
