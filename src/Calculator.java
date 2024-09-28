import java.util.ArrayList;

class Calculator {
    double firstNumber = 0;
    double lastNumber;
    double sum = 0;
    char operator;
    double result;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<Character> characterArrayList = new ArrayList<>();

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



    private void solveExpression(){
        for (int i = 0; i < characterArrayList.size(); i++) {
            if(characterArrayList.get(i) == '*' || characterArrayList.get(i) == '/'){
                firstNumber = convertData(stringArrayList.get(i));
                lastNumber = convertData(stringArrayList.get(i+1));
                operator = characterArrayList.get(i);
                calculate();
                stringArrayList.set(i, String.valueOf(sum));
                stringArrayList.remove(i+1);
                characterArrayList.remove(i);
                i--;
            }

        }
        for (int i = 0; i < characterArrayList.size(); i++) {
        if (characterArrayList.get(i) == '+' || characterArrayList.get(i) == '-') {
            firstNumber = convertData(stringArrayList.get(i));
            lastNumber = convertData(stringArrayList.get(i+1));
            operator = characterArrayList.get(i);
            calculate();
            stringArrayList.set(i, String.valueOf(sum));
            stringArrayList.remove(i);
            characterArrayList.remove(i);
            i--;
            }
        }
        System.out.println("RESULT: " + result);
    }

    private void calculate() {
        if (operator == '+') {
            sum = firstNumber + lastNumber;
        } else if (operator == '-') {
            lastNumber = lastNumber * -1;
            sum = firstNumber + lastNumber;
        } else if (operator == '*') sum = firstNumber * lastNumber;
        else if (operator == '/') sum = firstNumber / lastNumber;
            sum = roundDouble(sum);
        System.out.println(firstNumber + " " + operator + " " + lastNumber + " = " + sum);
        lastNumber = sum;
        result = sum;
    }


    private void recognizeExpression(String line) {
        System.out.println("INPUT DATA:" + line);
        int end = line.length();
        for (int i = line.length()-1; i >= 0; i--) {
            if (line.length() == 0) break;
            char charAt = line.charAt(i);
            if(isOperator(charAt)) {
                 if (charAt == '-' && (i == 0)) {
                    stringArrayList.add(0, "-" + line.substring(i+1, end));
                    break;
                } else if (charAt == '-' && isOperator(line.charAt(i - 1))) {
                    characterArrayList.add(0, line.charAt(i - 1));
                    stringArrayList.add(0, line.substring(i, end));
                    i--;
                } else {
                     characterArrayList.add(0, charAt);
                     stringArrayList.add(0, line.substring(i+1, end));
                 }
                 end = i;
            }
        }
    }
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.recognizeExpression("-1*-3+4/2");
        calculator.solveExpression();

    }
}
