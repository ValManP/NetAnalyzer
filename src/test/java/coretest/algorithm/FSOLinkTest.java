package coretest.algorithm;

import core.algorithm.fastheuristicalgorithm.model.FSOLink;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Valerii Pozdiaev on 2017.
 */
public class FSOLinkTest {
    @Test
    public void isCorrectParams () {
        //Arrange
        double expectedCapacity = 10;
        double expectedReliability = 2;
        double expectedTraffic = 6;
        FSOLink link = new FSOLink(expectedCapacity, expectedReliability, expectedTraffic);

        // Act
        double capacity = link.getCapacity();
        double reliability = link.getReliability();
        double traffic = link.getTraffic();

        //Assert
        double eps = 0.0001;
        assertEquals(expectedCapacity, capacity, eps);
        assertEquals(expectedReliability, reliability, eps);
        assertEquals(expectedTraffic, traffic, eps);
    }
}