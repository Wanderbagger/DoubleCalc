import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    Calculator calculator = new Calculator();
    @Test
    public void convertData() {
        assertEquals(5.12, calculator.convertData("5.12"), "Проверка конвертации числа");
        assertEquals(0.15, calculator.convertData("0.154512321"), "Проверка конвертации длинного числа");
    }

    @Test
    public void convertInt() {
        assertEquals(515, calculator.convertInt("515"), "Проверка конвертиации целого числа");
        assertEquals(10000000, calculator.convertInt("10000000"), "Проверка конвертиации целого числа");
    }

    @Test
    public void convertDouble() {
        assertEquals(0.02, calculator.convertDouble("0212341324"), "Проверка конвертиации дробного числа");
    }

    @Test
    public void roundDouble() {
        assertEquals(5.12, calculator.roundDouble(5.124), "Проверка округления вниз");
        assertEquals(5.12, calculator.roundDouble(5.115), "Проверка округления вверх");
    }

    @Test
    public void isOperator() {
        assertTrue(calculator.isOperator('/'));
        assertTrue(calculator.isOperator('+'));
        assertTrue(calculator.isOperator('-'));
        assertTrue(calculator.isOperator('*'));
        assertFalse(calculator.isOperator(','));
    }

    @Test
    public void calculate() {
        assertEquals(8, calculator.calculate(4, 4, '+'));
        assertEquals(8, calculator.calculate(12, 4, '-'));
        assertEquals(8, calculator.calculate(4, 2, '*'));
        assertEquals(8, calculator.calculate(16, 2, '/'));
        Exception exception = assertThrows(ArithmeticException.class, () -> calculator.calculate(5,0,'/'));
        String expectedMessage = "Division by zero is prohibited";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void recognizeExpression() {
        assertEquals("6.0", calculator.recognizeExpression("2+2*2"), "Проверка очередности действий");
        assertEquals("-6.0", calculator.recognizeExpression("-2+2*-2"), "Проверка считывания унарного минуса");
    }


}