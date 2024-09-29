public class Analyzer {
    final String line;

    public Analyzer(String line) throws InputDataException {
        this.line = line;
        analyse();
    }

    private boolean isSpecialCharacter(char c) {
        return c == '+' || c == '*' || c == '/' || c == '-' || c == '.';
    }

    private boolean isOperator(char c) { // распознавание операторов арифметических действий
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public void analyse() throws InputDataException {
        if (isEmpty()) throw new InputDataException("The string is Empty");
        else if (containsLetter()) throw new InputDataException("The string contains letter");
        else if (containsNoDigits()) throw new InputDataException("The string contains no digits");
        else if (containsNoOperators()) throw new InputDataException("The string contains no operators");
        else if (isSpecialCharacter(line.charAt(0)) && line.charAt(0) != '-')
            throw new InputDataException("The string starts with an invalid character");
        else if (isSpecialCharacter(line.charAt(line.length() - 1)))
            throw new InputDataException("The string ends with an invalid character");
        else if (containsIncorrectSequence())
            throw new InputDataException("The string contains an incorrect sequence of characters");
        else if (containsDoubleDot()) throw new InputDataException("The string contains a number with two dots");
        else if (containsIncorrectBrackets())
            throw new InputDataException("The string contains incorrect brackets number");
        else if (containsIncorrectSequenceWithBrackets())
            throw new InputDataException("The string contains incorrect  brackets sequence");
        else if (containsIncorrectSequenceWithBrackets())
            throw new InputDataException("The string contains incorrect  brackets sequence");
    }


    private boolean containsNoOperators() {
        for (int i = 0; i < line.length(); i++) {
            if (isOperator(line.charAt(i))) return false;
        }
        return true;
    }

    private boolean containsLetter() {
        for (int i = 0; i < line.length(); i++) {
            if (Character.isLetter(line.charAt(i))) return true;
        }
        return false;
    }

    private boolean isEmpty() {
        if (line == "") return true;
        else return false;
    }

    private boolean containsNoDigits() {
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) return false;
        }
        return true;
    }

    private boolean containsIncorrectSequence() {
        for (int i = 1; i < line.length(); i++) {
            if (isSpecialCharacter(line.charAt(i)) && isSpecialCharacter(line.charAt(i - 1)) && line.charAt(i) != '-') {
                return true;
            }
        }
        return false;
    }

    private boolean containsDoubleDot() {
        char lastSymbolFound = ' ';
        for (int i = 1; i < line.length(); i++) {
            if (line.charAt(i) == '.' && lastSymbolFound == '.') {
                return true;
            }
            if (isSpecialCharacter(line.charAt(i))) lastSymbolFound = line.charAt(i);
        }
        return false;
    }

    private boolean containsIncorrectBrackets() {
        int beginNumber = 0;
        int endNumber = 0;
        for (int i = 1; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                beginNumber++;
            } else if (line.charAt(i) == ')') {
                endNumber++;
            }
        }
        return beginNumber != endNumber;

    }

    private boolean containsIncorrectSequenceWithBrackets() {
        for (int i = 1; i < line.length(); i++) {
            if (isSpecialCharacter(line.charAt(i)) && line.charAt(i - 1) == ('(') && line.charAt(i) != '-') { // Ошибка откр. скобка перед оператором (не унарным минусом)
                return true;
            } else if (line.charAt(i) == (')') && isSpecialCharacter(line.charAt(i - 1))) { // Ошибка оператор перед закр. скобкой
                return true;
            }
        }
        return false;
    }

    class InputDataException extends Exception {
        public InputDataException(String message) {
            super(message);
        }
    }
}
