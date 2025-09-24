package quicksort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    @Test
    void tiny_and_edges() {
        assertDoesNotThrow(() -> QuickSort.sort((int[]) null));

        int[][] cases = {
                {},
                {7},
                {2, 1},
                {1, 1},
                {3, 2, 1},
                {1, 2, 3},
                {5, 5, 5, 5}
        };

        for (int[] a : cases) {
            int[] expected = a.clone();
            Arrays.sort(expected);

            int[] b = a.clone();
            QuickSort.sort(b);
            assertArrayEquals(expected, b);
        }
    }

    @Test
    void random_arrays_100_trials() {
        Random rnd = new Random(42);
        for (int t = 0; t < 100; t++) {
            int n = rnd.nextInt(10_000) + 1;
            int[] a = rnd.ints(n, -1_000_000, 1_000_001).toArray();

            int[] expected = a.clone();
            Arrays.sort(expected);

            int[] b = a.clone();
            QuickSort.sort(b);

            assertArrayEquals(expected, b);
        }
    }

    @Test
    void adversarial_sorted_ascending() {
        int n = 100_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;

        int[] expected = a.clone();
        Arrays.sort(expected);

        int[] b = a.clone();
        assertDoesNotThrow(() -> QuickSort.sort(b));
        assertArrayEquals(expected, b);
    }

    @Test
    void adversarial_sorted_descending() {
        int n = 100_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = n - 1 - i;

        int[] expected = a.clone();
        Arrays.sort(expected);

        int[] b = a.clone();
        assertDoesNotThrow(() -> QuickSort.sort(b));
        assertArrayEquals(expected, b);
    }

    @Test
    void adversarial_many_duplicates() {
        int[] a = {5,5,5,5,5,5,5,5,5,5,5, 3,3,3, 7,7,7,7, 1,1,1,1,1};

        int[] expected = a.clone();
        Arrays.sort(expected);

        int[] b = a.clone();
        QuickSort.sort(b);

        assertArrayEquals(expected, b);
    }

    @Test
    void adversarial_wide_range_mixture() {
        int[] a = {
                Integer.MIN_VALUE, 0, 1, -1, 2, -2, 3, -3,
                Integer.MAX_VALUE, 42, 42, -42, 10, -10, 0
        };

        int[] expected = a.clone();
        Arrays.sort(expected);

        int[] b = a.clone();
        QuickSort.sort(b);

        assertArrayEquals(expected, b);
    }
}