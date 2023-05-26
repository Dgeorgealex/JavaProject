package server.util;

import java.util.Random;

public class ArrayUtils {
    private static Random random = new Random();
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
