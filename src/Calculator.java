import java.util.ArrayList;

class Calculator {

    protected double convertData(String data) { // Конвертирование распознанного числа из String в double
        String[] splitData = new String[2];
        double result;
        if (data.contains(".")) {
            for (int i = 0; i < data.length(); i++) {
                char charAt = data.charAt(i);
                if (charAt == '.') {
                    splitData[0] = data.substring(0, i);
                    splitData[1] = data.substring(i + 1);
                }
            }
            result = convertInt(splitData[0]) + convertDouble(splitData[1]);
        } else {
            result = convertInt(data);
        }
        if (data.charAt(0) == '-') result *= -1;
        return result;
    }

    protected int convertInt(String part) {  // Конвертирование распознанного целого числа или целой части числа из String в int
        int intPart = 0;
        for (int i = 0; i < part.length(); i++) {
            char charAt = part.charAt(i);
            if (Character.isDigit(charAt)) {
                intPart = intPart * 10 + (charAt - '0');
            }
        }
        return intPart;
    }

    protected double convertDouble(String part) { // Конвертирование распознанной дробной части числа из String в double
        double converter;
        int end;
        if (part.length() == 1) {
            end = 1;
            converter = 0.1;
        } else if (part.length() == 2) {
            end = 2;
            converter = 0.01;
        } else {
            end = 2;
            converter = 0.01;
            if (Character.getNumericValue(part.charAt(2)) >= 5) {
                int intPart = convertInt(part.substring(0, 2));
                return (intPart + 1) * converter;
            }
        }
        int intPart = convertInt(part.substring(0, end));
        return intPart * converter;
    }

    protected double roundDouble(double number) { // округление числа до 2 знаков после запятой
        number *= 100;
        number = Math.round(number);
        return number / 100;
    }

    protected boolean isOperator(char c) { // распознавание операторов арифметических действий
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    protected String solveExpression(ArrayList<String> numbers, ArrayList<Character> operators) { // решение выражений в распзнанных списках
        double result = 0;
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i) == '*' || operators.get(i) == '/') {
                result = calculate(convertData(numbers.get(i)), convertData(numbers.get(i + 1)), operators.get(i));
                numbers.set(i, String.valueOf(result));
                numbers.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i) == '+' || operators.get(i) == '-') {
                result = calculate(convertData(numbers.get(i)), convertData(numbers.get(i + 1)), operators.get(i));
                numbers.set(i, String.valueOf(result));
                numbers.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }
        return String.valueOf(result);
    }

    protected double calculate(double firstNumber, double lastNumber, char operator) { // выполнение арифметических действий
        double sum = 0;
        if (operator == '+') {
            sum = firstNumber + lastNumber;
        } else if (operator == '-') {
            lastNumber = lastNumber * -1;
            sum = firstNumber + lastNumber;
        } else if (operator == '*') sum = firstNumber * lastNumber;
        else if (operator == '/') {
            if (lastNumber == 0) {
                throw new ArithmeticException("Division by zero is prohibited");
            } else sum = firstNumber / lastNumber;
        }
        sum = roundDouble(sum);
        return sum;
    }

    protected String recognizeExpression(String line) { // распознавание строки в списки чисел и знаков
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<Character> operators = new ArrayList<>();
        int end = line.length();
        for (int i = line.length() - 1; i >= 0; i--) {
            char charAt = line.charAt(i);
            if (isOperator(charAt) || i == 0) {
                if (charAt == '-' && (i == 0)) {
                    numbers.add(0, "-" + line.substring(i + 1, end));
                    break;
                }
                if (i == 0) {
                    numbers.add(0, line.substring(0, end));
                    break;
                } else if (charAt == '-' && isOperator(line.charAt(i - 1))) {
                    operators.add(0, line.charAt(i - 1));
                    numbers.add(0, line.substring(i, end));
                    i--;
                } else {
                    operators.add(0, charAt);
                    numbers.add(0, line.substring(i + 1, end));
                }
                end = i;
            }
        }
        return solveExpression(numbers, operators);
    }
/*
    protected void findBrackets(String line) {
        char bracket = ' ';
        int begin = 0;
        char currentChar;
        String result;
        for (int i = 0; i < line.length(); i++) {
            currentChar = line.charAt(i);
            if (currentChar == '(') {
                bracket = currentChar;
                begin = i;
            } else if (currentChar == ')' && bracket == '(') {
                result = recognizeExpression(line.substring(begin + 1, i));
                recognizeExpression(line.substring(0, begin) + result + line.substring(i + 1));
                bracket = currentChar;
            } else if (currentChar == ')') {
                bracket = currentChar;
            }
        }
    }

 */

    protected void start(String line) throws Analyzer.InputDataException {
        new Analyzer(line);
        System.out.println("RESULT: " + recognizeExpression(line));
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        try {
            calculator.start("2*2-2+2+2/6");
        } catch (Analyzer.InputDataException exception) {
            exception.printStackTrace();
        }
    }
}