package deterministicselect;

import deterministicselect.DeterministicSelect;

import java.util.Arrays;
import java.util.Random;

public class SelectManualTest {
    public static void main(String[] args) {
        Random rnd = new Random(42);

        System.out.println("Testing DeterministicSelect...");

        for (int t = 1; t <= 100; t++) {
            int n = rnd.nextInt(2000) + 1;
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = rnd.nextInt(2001) - 1000;
            }

            int[] sorted = a.clone();
            Arrays.sort(sorted);

            int k = rnd.nextInt(n);
            int got = DeterministicSelect.select(a.clone(), k);
            int expected = sorted[k];

            if (got != expected) {
                System.out.println("Trial " + t + " failed: n=" + n + ", k=" + k);
                System.out.println("Expected " + expected + " but got " + got);
                return;
            }
        }

        System.out.println("All 100 random trials passed.");
    }
}