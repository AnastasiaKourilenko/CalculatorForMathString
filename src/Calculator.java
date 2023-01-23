import java.util.Stack;

public class Calculator {
    public static int calculate(String expression) {
        String reversePolishNotation = transferToReversePolishNotation(expression);
        String[] operandsAndOperations = reversePolishNotation.split(" ");
        Stack<String> operands = new Stack<>();
        for (String s : operandsAndOperations) {
            if (s.matches("-?\\d+")) {
                operands.push(s);
            } else {
                int res;
                int secondOperand = Integer.parseInt(operands.pop());
                int firstOperand = Integer.parseInt(operands.pop());
                switch (s) {
                    case ("+"):
                        res = firstOperand + secondOperand;
                        operands.push(String.valueOf(res));
                        break;
                    case ("-"):
                        res = firstOperand - secondOperand;
                        operands.push(String.valueOf(res));
                        break;
                    case ("*"):
                        res = firstOperand * secondOperand;
                        operands.push(String.valueOf(res));
                        break;
                    case ("/"):
                        res = firstOperand / secondOperand;
                        operands.push(String.valueOf(res));
                        break;
                }
            }
        }
        System.out.println(reversePolishNotation);
        return Integer.parseInt(operands.pop());
    }


    private static String transferToReversePolishNotation(String expression) {
        StringBuilder reversePolishNotation = new StringBuilder();
        Stack<Character> operationsStack = new Stack<>();
        char previousChar = '?';
        for (int i = 0; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            if (Character.isDigit(currentChar)) {
                reversePolishNotation.append(currentChar);
                previousChar = currentChar;
            } else if (Character.isLetter(currentChar)) {
                throw new RuntimeException("Неверно заданое выражение");
            } else if (Character.isSpaceChar(currentChar)) {
                continue;
            } else if (currentChar == '(') {
                operationsStack.push(currentChar);
                previousChar = currentChar;
            } else if (currentChar == ')') {
                while (operationsStack.peek() != '(') {
                    reversePolishNotation.append(" ").append(operationsStack.pop());
                }
                operationsStack.pop();
            } else {
                if (isAddition(currentChar)) {
                    if (currentChar == '-' && !Character.isDigit(previousChar)) {
                        reversePolishNotation.append(currentChar);
                    } else {
                        reversePolishNotation.append(" ");
                        while (!operationsStack.isEmpty() && isMultiplication(operationsStack.peek())) {
                            reversePolishNotation.append(operationsStack.pop()).append(" ");
                        }
                        operationsStack.push(currentChar);
                    }
                } else {
                    reversePolishNotation.append(" ");
                    operationsStack.push(currentChar);
                }
            }
        }
        while (!operationsStack.isEmpty()) {
            reversePolishNotation.append(" ").append(operationsStack.pop());
        }
        return reversePolishNotation.toString();
    }

    private static boolean isAddition(char c) {
        String additionOperations = "+-";
        return additionOperations.contains(String.valueOf(c));
    }

    private static boolean isMultiplication(char c) {
        String multiplicationOperations = "*/";
        return multiplicationOperations.contains(String.valueOf(c));
    }
}
