package deterministicselect;

import java.util.Random;

public final class DeterministicSelect {
    private static final int CUTOFF = 24;
    private DeterministicSelect() {}

    public static int select(int[] a, int k) {
        if (a == null) throw new IllegalArgumentException("array is null");
        if (a.length == 0) throw new IllegalArgumentException("array is empty");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return select(a, 0, a.length, k);
    }

    private static int select(int[] a, int l, int r, int k) {
        while (true) {
            int n = r - l;
            if (n <= CUTOFF) {
                insertionSort(a, l, r);
                return a[l + k];
            }
            // 1) Choose pivot by median-of-medians (groups of 5).
            int p = pivotIndexByMoM5(a, l, r);
            int pv = a[p];
            swap(a, l, p); // move pivot to l

            // 2) In-place 3-way partition around pv: [ <pv | ==pv | >pv ]
            int[] b = partition3(a, l, r, pv);
            int lt = b[0], gt = b[1];
            int leftSize = lt - l;
            int midSize = gt - lt;

            // 3) Recurse only into the side that contains k (prefer smaller side).
            if (k < leftSize) {
                r = lt;
            } else if (k < leftSize + midSize) {
                return pv;
            } else {
                k -= (leftSize + midSize);
                l = gt;
            }
        }
    }

    private static int pivotIndexByMoM5(int[] a, int l, int r) {
        int n = r - l;
        if (n <= 5) {
            insertionSort(a, l, r);
            return l + n / 2;
        }

        int m = 0;
        for (int i = l; i < r; i += 5) {
            int gR = Math.min(i + 5, r);
            insertionSort(a, i, gR);
            int medianIdx = i + (gR - i - 1) / 2;
            swap(a, l + m, medianIdx);
            m++;
        }

        return selectIndexOfMedian(a, l, l + m);
    }

    private static int selectIndexOfMedian(int[] a, int l, int r) {
        int n = r - l;
        int k = n / 2;
        int val = select(a, l, r, k);
        for (int i = l; i < r; i++) if (a[i] == val) return i;
        return l + k;
    }

    private static int[] partition3(int[] a, int l, int r, int pv) {
        int lt = l, i = l, gt = r;
        while (i < gt) {
            if (a[i] < pv) {
                swap(a, lt++, i++);
            } else if (a[i] > pv) {
                swap(a, i, --gt);
            } else {
                i++;
            }
        }
        return new int[]{lt, gt};
    }

    private static void insertionSort(int[] a, int l, int r) {
        for (int i = l + 1; i < r; i++) {
            int x = a[i], j = i - 1;
            while (j >= l && a[j] > x) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = x;
        }
    }

    private static void swap(int[] a, int i, int j) {
        if (i != j) {
            int t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
    }
    public static void main(String[] args) {
        int[] arr = {7, 3, 5, 2, 2, 9, 1, 6};
        int k = 3; // 0-indexed: the 4th smallest
        int val = DeterministicSelect.select(arr, k);
        System.out.println("k-th smallest = " + val);
    }
}