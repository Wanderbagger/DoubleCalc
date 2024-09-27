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
        if(data.contains(".")) {
            for (int i = 0; i < data.length(); i++) {
                char charAt = data.charAt(i);
                if (charAt == '.') {
                    splitData[0] = data.substring(0, i);
                    splitData[1] = data.substring(i + 1);
                }
            }
            return  convertInt(splitData[0]) + convertDouble(splitData[1]);
        } else {
            return convertInt(data);
        }
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


    private void start(String text) {
        int begin = 0;
        int end;
        int counter = 0;
        for (int i = 0; i < text.length(); i++) {
            char charAt = text.charAt(i);
            if (isOperator(charAt)) {
                if(counter == 0 && charAt != '-') continue;
                counter++;
                if (i != 0) {
                    end = i;
                    operator = charAt;
                    firstNumber = lastNumber;
                    lastNumber = convertData(text.substring(begin, end));
                    begin = end + 1;
                }
            } else if(i == text.length() -1){
                end = text.length();
                firstNumber = lastNumber;
                lastNumber = convertData(text.substring(begin, end));
            }
        }
        System.out.println("РЕЗУЛЬТАТ " + firstNumber + operator + lastNumber + "=" + result);
        firstNumber = 0;
        lastNumber = 0;
    }
    private void solution(){
        for (int i = 1; i < characterArrayList.size(); i++) {
            if(characterArrayList.get(i) == '*' || characterArrayList.get(i) == '/'){
                firstNumber = convertData(stringArrayList.get(i-1));
                lastNumber = convertData(stringArrayList.get(i));
                operator = characterArrayList.get(i-1);
                calculate();
                stringArrayList.set(i-1, String.valueOf(sum));
                stringArrayList.remove(i);
                characterArrayList.remove(i-1);
                solution();
            } else if (characterArrayList.get(i) == '*' || characterArrayList.get(i) == '/') {
                firstNumber = convertData(stringArrayList.get(i-1));
                lastNumber = convertData(stringArrayList.get(i));
                operator = characterArrayList.get(i-1);
                calculate();
                stringArrayList.set(i-1, String.valueOf(sum));
                stringArrayList.remove(i);
                characterArrayList.remove(i-1);
                solution();
            }

        }
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
        lastNumber = sum;
        result = sum;
    }
    private void recognize(String line) {

        int begin = 0;
        int end;
        for (int i = 0; i < line.length(); i++) {
            char charAt = line.charAt(i);
            if (isOperator(charAt)) {
                characterArrayList.add(charAt);
                stringArrayList.add(line.substring(begin, i));
                begin = i+1;
            } else if (i == line.length()-1) {
                stringArrayList.add(line.substring(begin));
            }
        }
        System.out.println(characterArrayList);
        System.out.println(stringArrayList);
        solution();
        System.out.println(result);
    }
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.recognize("2+2*3/8");

    }
}
