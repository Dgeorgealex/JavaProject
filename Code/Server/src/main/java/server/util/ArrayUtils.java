package server.util;

import java.util.Random;

/**
 * A class of ArrayUtils that are not directly implemented in Java
 */
public class ArrayUtils {
    private static Random random = new Random();

    /**
     * Generate a permutation with the numbers from 0 to length - 1
     * @param array The array
     */
    public static void generateRandomPermutation(int[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            array[i] = i;
        }
        for (int i = n - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    /**
     * Reverse the array between x and y
     * @param array
     * @param x start position
     * @param y end position
     */
    public static void reverseArrayPart(int[] array, int x, int y) {
        while (x < y) {
            int temp = array[x];
            array[x] = array[y];
            array[y] = temp;
            x++;
            y--;
        }
    }
}
