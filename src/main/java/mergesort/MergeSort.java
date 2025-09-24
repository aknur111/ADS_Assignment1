package mergesort;

import java.util.Arrays;

public final class MergeSort {
    private static final int CUTOFF = 24;

    private MergeSort() {}

    public static void sort(int[] a) {
        if (a == null || a.length < 2) return;
        int[] buf = new int[a.length];
        sort(a, buf, 0, a.length);
    }

    public static void sort(int[] a, int cutoff) {
        if (a == null || a.length < 2) return;
        int[] buf = new int[a.length];
        sort(a, buf, 0, a.length, Math.max(0, cutoff));
    }

    private static void sort(int[] a, int[] buf, int l, int r) {
        sort(a, buf, l, r, CUTOFF);
    }

    private static void sort(int[] a, int[] buf, int l, int r, int cutoff) {
        int n = r - l;
        if (n <= 1) return;
        if (n <= cutoff) { insertionSort(a, l, r); return; }
        int m = l + (n >>> 1);
        sort(a, buf, l, m, cutoff);
        sort(a, buf, m, r, cutoff);
        if (a[m - 1] <= a[m]) return;
        merge(a, buf, l, m, r);
    }

    private static void merge(int[] a, int[] buf, int l, int m, int r) {
        int leftLen = m - l;
        System.arraycopy(a, l, buf, 0, leftLen);
        int i = 0, j = m, k = l;
        while (i < leftLen && j < r) {
            if (buf[i] <= a[j]) a[k++] = buf[i++];
            else               a[k++] = a[j++];
        }
        while (i < leftLen) a[k++] = buf[i++];
    }

    private static void insertionSort(int[] a, int l, int r) {
        for (int i = l + 1; i < r; i++) {
            int x = a[i], j = i - 1;
            while (j >= l && a[j] > x) { a[j + 1] = a[j]; j--; }
            a[j + 1] = x;
        }
    }

    public static void main(String[] args) {
        int[] x = {7, 3, 5, 2, 2, 9, 1, 6};
        MergeSort.sort(x);
        System.out.println(Arrays.toString(x));
    }
}
