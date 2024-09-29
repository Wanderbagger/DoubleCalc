import Exceptions.InputDataException;

import java.util.ArrayList;

class Calculator {

    public static void main(String[] args) throws InputDataException {
        Calculator calculator = new Calculator();
        calculator.start("2+2");
    }

    boolean isOperator(char c) { // распознавание операторов арифметических действий
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    double convertData(String data) { // Конвертирование распознанного числа из String в double
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
        if (data.charAt(0) == '-') {
            result *= -1;
        }
        return result;
    }

    int convertInt(String part) {  // Конвертирование распознанного целого числа или целой части числа из String в int
        int intPart = 0;
        for (int i = 0; i < part.length(); i++) {
            char charAt = part.charAt(i);
            if (Character.isDigit(charAt)) {
                intPart = intPart * 10 + (charAt - '0');
            }
        }
        return intPart;
    }

    double convertDouble(String part) { // Конвертирование распознанной дробной части числа из String в double
        if (part.length() == 1) {
            return convertInt(part.substring(0, 1)) * 0.1; // 1 знак после запятой
        } else if (part.length() == 2) {
            return convertInt(part.substring(0, 2)) * 0.01; // 2 знака после запятой
        } else {
            if (Character.getNumericValue(part.charAt(2)) >= 5) {  // больше 2-х знаков и округление наверх
                return (convertInt(part.substring(0, 2)) + 1) * 0.01;
            } else { // больше 2-х знаков и округление вниз
                return (convertInt(part.substring(0, 2))) * 0.01;
            }
        }
    }

    double roundDouble(double number) { // округление числа до 2 знаков после запятой
        number *= 100;
        number = Math.round(number);
        return number / 100;
    }

    String recognizeExpression(String line) { // распознавание строки в списки чисел и знаков
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<Character> operators = new ArrayList<>();
        while (line.contains("(") && line.contains(")")) { // вычисление внутри скобок
            line = findBrackets(line);
        }
        int end = line.length();
        for (int i = line.length() - 1; i >= 0; i--) { // перебор с конца строки до начала в целях поиска унарных минусов
            char charAt = line.charAt(i);
            if (isOperator(charAt) || i == 0) {
                if (charAt == '-' && (i == 0)) { // долшли до начала строки и первый знак унарный минус
                    numbers.add(0, "-" + line.substring(i + 1, end));
                    break;
                }
                if (i == 0) { // дошли до начала строки
                    numbers.add(0, line.substring(0, end));
                    break;
                } else if (charAt == '-' && isOperator(line.charAt(i - 1))) { // дошли до унарного минуса
                    operators.add(0, line.charAt(i - 1));
                    numbers.add(0, line.substring(i, end));
                    i--;
                } else { // дошли до оператора
                    operators.add(0, charAt);
                    numbers.add(0, line.substring(i + 1, end));
                }
                end = i; // конец подстроки - координата оператора
            }
        }
        return solveExpression(numbers, operators);
    }

    String solveExpression(ArrayList<String> numbers, ArrayList<Character> operators) { // решение выражений в распзнанных списках
        double result = 0;
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i) == '*' || operators.get(i) == '/') { // первоочередное решение умножений и делений
                result = calculate(convertData(numbers.get(i)), convertData(numbers.get(i + 1)), operators.get(i));
                numbers.set(i, String.valueOf(result));
                numbers.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i) == '+' || operators.get(i) == '-') { // решение второстепенных арифметических действий
                result = calculate(convertData(numbers.get(i)), convertData(numbers.get(i + 1)), operators.get(i));
                numbers.set(i, String.valueOf(result));
                numbers.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }
        return String.valueOf(result);
    }

    double calculate(double firstNumber, double lastNumber, char operator) { // выполнение арифметических действий
        double sum = 0;
        if (operator == '+') {
            sum = firstNumber + lastNumber;
        } else if (operator == '-') {
            lastNumber = lastNumber * -1;
            sum = firstNumber + lastNumber;
        } else if (operator == '*') {
            sum = firstNumber * lastNumber;
        }
        else if (operator == '/') {
            if (lastNumber == 0) {
                throw new ArithmeticException("Division by zero is prohibited"); // ошибка при делении на ноль
            } else sum = firstNumber / lastNumber;
        }
        sum = roundDouble(sum);
        return sum;
    }

    String findBrackets(String line) {
        int begin = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                begin = i;
            } else if (line.charAt(i) == ')')
                return line.substring(0, begin) + recognizeExpression(line.substring(begin + 1, i)) + line.substring(i + 1);
        }
        return "";
    }

    void start(String line) throws InputDataException {
        new Validator(line);
        System.out.println("RESULT: " + recognizeExpression(line));
    }


}