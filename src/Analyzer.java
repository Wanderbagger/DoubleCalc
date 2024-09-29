public class Analyzer {
    final String line;

    public Analyzer(String line) throws InputDataException {
        this.line = line;
        analyse();
    }

    private boolean isSpecialCharacter(char c) {
        return c == '+' ||  c == '*' || c == '/' || c == '-' || c == '.';
    }
    public void analyse() throws InputDataException {

        if (isSpecialCharacter(line.charAt(0)) && line.charAt(0) != '-') throw new InputDataException("The string starts with an invalid character");
        else if (isSpecialCharacter(line.charAt(line.length()-1))) throw new InputDataException("The string ends with an invalid character");
        else if (containsLetter()) throw new InputDataException("The string contains letter");
        else if (containsIncorrectSequence())throw new InputDataException("The string contains an incorrect sequence of characters");
        else if (contansDoubleDot()) throw new InputDataException("The string contains a number with two dots");

        }


    private boolean containsLetter(){
        for (int i = 0; i < line.length(); i++) {
            if(Character.isLetter(line.charAt(i))) return true;
        }
        return false;
    }

    private boolean containsIncorrectSequence(){
        for (int i = 1; i < line.length(); i++) {
            if(isSpecialCharacter(line.charAt(i)) && isSpecialCharacter(line.charAt(i-1)) && line.charAt(i) != '-'){
                return true;
            }
        }
        return false;
    }

    private boolean contansDoubleDot(){
        char lastSymbolFound = ' ';
        for (int i = 1; i < line.length(); i++) {
            if(line.charAt(i) == '.' && lastSymbolFound == '.'){
                return true;
            }if(isSpecialCharacter(line.charAt(i))) lastSymbolFound = line.charAt(i);
        }
        return false;
    }

        /*
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
*/

    class InputDataException extends Exception{
        public InputDataException(String message){
            super(message);
        }
    }
}
