class Calculator {
    double firstNumber;
    double lastNumber;
     int sum = 0;
    char operator;
    boolean isFractionalPart = false;


    private void recognize (String text){

        String[] data = text.split(String.valueOf(operator));
        firstNumber = convertData(data[0]);
        lastNumber = convertData(data[1]);
        System.out.println(firstNumber);
        System.out.println(lastNumber);
    }

    private double convertData(String data){
        int intPart;
        double fractionalPart;
        String[] parts = data.split(".");
        intPart = convertInt(parts[0]);
        fractionalPart = convertDouble(parts[1]);
        return intPart + fractionalPart;
    }

    private int convertInt(String part){
        int intPart = 0;
        for (int i = 0; i < part.length(); i++) {
            char charAt = part.charAt(i);
            if (Character.isDigit(charAt)){
                firstNumber = firstNumber * 10 + (charAt - '0');
            }
        }
        return intPart;
    }

    private double convertDouble(String part){
        double converter;
        if(part.length()>1){
            converter = 0.01;
        } else {
            converter = 0.1;
        }
        int intPart = convertInt(part.substring(0, 2));
        return intPart * converter;
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private void findOperator(String text){
        int begin = 0;
        int end = 0;
        String text1;
        String text2;
        for (int i = 0; i < text.length(); i++) {
            char charAt = text.charAt(i);
            if(isOperator(charAt)){
                if(i==0) end = 0;
                else end = i-1;
                text1 = text.substring(begin, end);
            }
        }

    }



    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.recognize("0.999+0.888");
    }
}
