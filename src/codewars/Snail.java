package codewars;

import java.util.Arrays;

public class Snail {
    public static int[] snail(int[][] array) {
        int[] out = new int[array.length * array.length];
        int s = 0;
        int l = array.length;
        //while (l > 0) {
            int c = 0;
            for (int i = s; i < l; i++) {
                out[c] = array[s][i];
                c++;
            }
            for (int i = 1; i < l; i++) {
                out[c] = array[i][l - 1 - s];
                c++;
            }
            for (int i = l - 1; i > s; i--) {
                out[c] = array[l - 1][i];
                c++;
            }
            s++;
            l--;
//        }
        System.out.println(Arrays.toString(out));
        return out;
    }
}