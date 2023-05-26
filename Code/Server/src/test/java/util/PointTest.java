package util;

import org.junit.jupiter.api.Test;
import server.model.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointTest {
    @Test
    public void testDistance(){
        Point a = new Point(-13.20, 10009.23);
        Point b = new Point(1021.12, 12231.1231);
        assertEquals(a.distance(b), 2451);
    }
}