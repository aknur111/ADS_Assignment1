package quicksort;

import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {
    public static int maxDepth = 0;

    public static void resetDepth() { maxDepth = 0; }

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        sort(arr, 0, arr.length - 1, 1);
    }

    private static void sort(int[] arr, int low, int high, int depth) {
        while (low < high) {
            if (depth > maxDepth) maxDepth = depth;
            int pivotIdx = ThreadLocalRandom.current().nextInt(low, high + 1);
            swap(arr, pivotIdx, high);
            int p = partition(arr, low, high);
            int leftSize = p - low;
            int rightSize = high - p;
            if (leftSize < rightSize) {
                if (low < p - 1) sort(arr, low, p - 1, depth + 1);
                low = p + 1;
            } else {
                if (p + 1 < high) sort(arr, p + 1, high, depth + 1);
                high = p - 1;
            }
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) { i++; swap(arr, i, j); }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
    }
}