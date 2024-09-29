import java.util.ArrayList;

class Calculator {
    private double convertData(String data) {
        String[] splitData = new String[2];
        double result;
        if(data.contains(".")) {
            for (int i = 0; i < data.length(); i++) {
                char charAt = data.charAt(i);
                if (charAt == '.') {
                    splitData[0] = data.substring(0, i);
                    splitData[1] = data.substring(i + 1);
                }
            }
            result =  convertInt(splitData[0]) + convertDouble(splitData[1]);
        } else {
            result = convertInt(data);

        }
        if(data.charAt(0) == '-') result *= -1;
        return result;
    }

    private int convertInt(String part) {
        int intPart = 0;
        for (int i = 0; i < part.length(); i++) {
            char charAt = part.charAt(i);
            if (Character.isDigit(charAt)) {
                intPart = intPart * 10 + (charAt - '0');
            }
        }
        return intPart;
    }

    private double convertDouble(String part) {
        double converter;
        int end;
        if (part.length() == 1){
            end = 1;
            converter = 0.1;
        } else if (part.length() == 2) {
            end = 2;
            converter = 0.01;
        } else {
            end = 2;
            converter = 0.01;
            if(Character.getNumericValue(part.charAt(2)) >= 5){
                int intPart = convertInt(part.substring(0, 2));
                return (intPart + 1) * converter;
            }
        }
        int intPart = convertInt(part.substring(0, end));
        return intPart * converter;
    }
    private double roundDouble(double number){
        number *= 100;
        number = Math.round(number);
        return number /100;
    }

    private boolean isSpecialCharacter(char c) {
        return c == '+' ||  c == '*' || c == '/' || c == '.';
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    private void findMistake(String line){
        if(isSpecialCharacter(line.charAt(0))) {
            System.out.println("Ошибка ввода - Неверное начало строки");
            return;
        }
        char c = ' ';
        for (int i = 0; i < line.length(); i++) {
            if(isSpecialCharacter(line.charAt(i))){
                c = line.charAt(i);
            }
            if(isSpecialCharacter(c) && isSpecialCharacter(line.charAt(i-1))) {
                System.out.println("Ошибка ввода - Два специальных символа подряд");
                return;
            } else if(c == '.' && line.charAt(i-1) == '.') {
                System.out.println("Ошибка ввода - Двe точки подряд");
                return;
            }
        }
    }

    private void solveExpression( ArrayList<String> numbers, ArrayList<Character> operators){
        double result = 0;
        for (int i = 0; i < operators.size(); i++) {
            if(operators.get(i) == '*' || operators.get(i) == '/'){
                result = calculate(convertData(numbers.get(i)), convertData(numbers.get(i+1)), operators.get(i));
                numbers.set(i, String.valueOf(result));
                numbers.remove(i+1);
                operators.remove(i);
                i--;
            }
        }
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i) == '+' || operators.get(i) == '-') {
                result = calculate(convertData(numbers.get(i)), convertData(numbers.get(i+1)), operators.get(i));
                numbers.set(i, String.valueOf(result));
                numbers.remove(i+1);
                operators.remove(i);
                i--;
            }
        }
        System.out.println("RESULT: " + result);
    }

    private double calculate(double firstNumber, double lastNumber, char operator) {
        double sum = 0;
        if (operator == '+') {
            sum = firstNumber + lastNumber;
        } else if (operator == '-') {
            lastNumber = lastNumber * -1;
            sum = firstNumber + lastNumber;
        } else if (operator == '*') sum = firstNumber * lastNumber;
        else if (operator == '/') sum = firstNumber / lastNumber;
        sum = roundDouble(sum);
        System.out.println(firstNumber + " " + operator + " " + lastNumber + " = " + sum);
        return sum;
    }


    private void recognizeExpression(String line) {
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<Character> operators = new ArrayList<>();
        System.out.println("INPUT DATA:" + line);
        int end = line.length();
        for (int i = line.length()-1; i >= 0; i--) {
            if (line.length() == 0) break;
            char charAt = line.charAt(i);
            if(isOperator(charAt)) {
                if (charAt == '-' && (i == 0)) {
                    numbers.add(0, "-" + line.substring(i+1, end));
                    break;
                } else if (charAt == '-' && isOperator(line.charAt(i - 1))) {
                    operators.add(0, line.charAt(i - 1));
                    numbers.add(0, line.substring(i, end));
                    i--;
                } else {
                    operators.add(0, charAt);
                    numbers.add(0, line.substring(i+1, end));
                }
                end = i;
            }
        }
        solveExpression(numbers, operators);
    }
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.recognizeExpression("-1*-3+4/2*2.1-8.5/4");
    }
}