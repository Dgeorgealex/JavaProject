package util;

import org.junit.jupiter.api.Test;
import server.util.ArrayUtils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArrayUtilsTest {
    @Test
    public void testGenerateRandomPermutation() {
        int n = 5;
        int[] permutation = new int[n];
        ArrayUtils.generateRandomPermutation(permutation);
        for (int i = 0; i < n; i++) {
            boolean found = false;
            for (int j = 0; j < n; j++) {
                if (permutation[j] == i) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    public void testReverseArrayPart() {
        int[] array = {1, 2, 3, 4, 5};
        ArrayUtils.reverseArrayPart(array, 1, 3);
        int[] expectedArray = {1, 4, 3, 2, 5};
        assertArrayEquals(expectedArray, array);
    }
}
