import org.junit.Test;
import tools.Calculator;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void testAdd() {
        // Arrange
        Calculator calc = new Calculator();
        int a = 2;
        int b = 4;

        // Act
        int result = calc.add(a, b);
        int expectedResult = 6;

        // Assert
        assertEquals(expectedResult, result);
    }
}
