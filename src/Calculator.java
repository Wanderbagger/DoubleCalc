import java.text.DecimalFormat;

class Calculator {
    double firstNumber = 0;
    double lastNumber;
    double result = 0;
    char operator;

    private double convertData(String data) {
        double result;

        String[] splitData = new String[2];
        if(data.contains(".")) {
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

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private void start(String text) {
        int begin = 0;
        int end;
        String text1;
        for (int i = 0; i < text.length(); i++) {
            char charAt = text.charAt(i);
            if (isOperator(charAt)) {
                if (i != 0) {
                    end = i;
                    System.out.println("BEGIN " + begin + " END " + end);
                    text1 = text.substring(begin, end);
                    System.out.println("TEXT1 " + text1);
                    operator = charAt;
                    firstNumber = lastNumber;
                    System.out.println(convertData(text1));
                    lastNumber = convertData(text1);
                    begin = end + 1;
                    System.out.println("РЕЗУЛЬТАТ " + firstNumber + operator + lastNumber + "=" + calculate());
                }
            } else if(i == text.length() -1){
                end = text.length();
                System.out.println("BEGIN " + begin + " END " + end);
                text1 = text.substring(begin, end);
                System.out.println("TEXT1 " + text1);
                firstNumber = lastNumber;
                System.out.println("CONVERT " + convertData(text1));
                firstNumber = convertData(text1);
                System.out.println("РЕЗУЛЬТАТ " + firstNumber + operator + lastNumber + "=" + calculate());
            }
        }
    }

    private double calculate() {
        if (operator == '+') {
            result = firstNumber + lastNumber;
        } else if (operator == '-') {
            lastNumber = lastNumber * -1;
            result = firstNumber + lastNumber;
        } else if (operator == '*') result = firstNumber * lastNumber;
        else if (operator == '/') result = firstNumber / lastNumber;


        lastNumber = result;
        return result;
    }


    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.start("1.965+22*5.3-8.6564/545.121+2323");
    }
}
