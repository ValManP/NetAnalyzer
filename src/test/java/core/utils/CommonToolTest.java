/**
 * Created by Valerii Pozdiaev on 2017.
 */
package core.utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CommonToolTest {
    @Test
    public void testIsBelong() {
        // Arrange
        int rangeFrom = 1;
        int rangeTo = 10;
        int x = 5;

        // Act&Assert
        assertTrue(CommonTool.isBelong(x, rangeFrom, rangeTo));
    }

    @Test
    public void testIsBelongWithoutRangeFrom() {
        // Arrange
        int rangeTo = 10;
        int x = 5;

        // Act&Assert
        assertTrue(CommonTool.isBelong(x, rangeTo));
    }

    @Test
    public void testIsExist() {
        // Arrange
        Object object = new Object();

        // Act&Assert
        assertTrue(CommonTool.isExist(object));
    }
}
