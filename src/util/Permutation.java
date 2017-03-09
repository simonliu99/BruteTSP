package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 1/5/17.
 * Permutation's permutate function returns a list of all possible permutations of a given list of numbers
 */
public class Permutation {
    public static List<List<Integer>> permutate(int n) {
        int[] l = new int[n];
        for (int i = 0; i < n; i++) {
            l[i] = i;
        }
        List<List<Integer>> permutated= new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        for (int c : l) {
            b.add(c);
        }
        b.add(b.get(0));
        permutated.add(b);
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
                temp.add(temp.get(0));
                permutated.add(temp);
                p[i]++;
                i = 1;
            }
            else {
                p[i] = 0;
                i++;
            }
        }
        return permutated;
    }

    private static int[] swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        return a;
    }
}