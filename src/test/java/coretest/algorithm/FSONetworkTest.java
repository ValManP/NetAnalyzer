package coretest.algorithm;

import core.algorithm.fastheuristicalgorithm.model.FSOLink;
import core.algorithm.fastheuristicalgorithm.model.FSONetwork;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Valerii Pozdiaev on 2017.
 */
public class FSONetworkTest {
    @Test
    public void isNetworkCreated () {
        //Arrange
        int size = 10;
        FSONetwork network = new FSONetwork(size);
        FSOLink expectedTraffic = new FSOLink(10, 2, 5);
        network.setTraffic(1, 5, expectedTraffic);

        // Act
        FSOLink traffic = network.getTraffic(1, 5);

        //Assert
        assertEquals(expectedTraffic, traffic);
    }

    @Test
    public void isCorrectSize () {
        //Arrange
        int expectedSize = 10;
        FSONetwork network = new FSONetwork(expectedSize);

        // Act
        int size = network.getSize();

        //Assert
        assertEquals(expectedSize, size);
    }
}