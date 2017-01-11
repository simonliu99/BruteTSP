package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 1/5/17.
 * Combine's combinate function returns a list of all possible combinations of a given list of numbers
 */
public class Combine {
    public static List<List<Integer>> combinate(int n) {
        int[] l = new int[n];
        for (int i = 0; i < n; i++) {
            l[i] = i;
        }
        List<List<Integer>> combinated= new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        for (int c : l) {
            b.add(c);
        }
        combinated.add(b);
        int[] p = new int[n];
        int i = 1;
        List<Integer> temp = new ArrayList<>();
        while (i < n) {
            if (p[i] < i) { // if  weight index is bigger or equal then i, j have already been switched
                int j = ((i % 2) == 0) ? 0 : p[i];
                swap(l, i, j);
                temp.clear(); // reduce amount of active temporary objects
                for (int d : l) {
                    temp.add(d);
                }
                combinated.add(temp);
                p[i]++;
                i = 1;
            }
            else {
                p[i] = 0;
                i++;
            }
        }
        return combinated;
    }

    private static int[] swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        return a;
    }
}