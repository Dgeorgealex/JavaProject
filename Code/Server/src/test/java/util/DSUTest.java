package util;

import org.junit.jupiter.api.Test;
import server.util.DSU;

import static org.junit.jupiter.api.Assertions.*;

public class DSUTest {
    @Test
    public void testUnion(){
        int n = 6;
        DSU dsu = new DSU(n);
        assertTrue(dsu.union(0, 1));
        assertTrue(dsu.union(2, 3));
        assertTrue(dsu.union(1, 2));
        assertFalse(dsu.union(1, 3));

        int[] expectedT = {0, 0, 0, 0, 4, 5};
        assertArrayEquals(expectedT, dsu.getT());
    }
    @Test
    public void testFind() {
        int n = 5;
        DSU dsu = new DSU(n);
        for (int i = 0; i < n; i++) {
            assertEquals(i, dsu.find(i));
        }
        dsu.union(1, 3);
        dsu.union(2, 4);
        assertEquals(1, dsu.find(3));
        assertEquals(2, dsu.find(4));
        assertEquals(1, dsu.find(1));
        assertEquals(2, dsu.find(2));
    }
}
