package codewars;

import java.util.TreeSet;

class DoubleLinear {

    public static int dblLinear (int n) {
        TreeSet<Integer> list = new TreeSet<>();
        list.add(1); int i = 1;

        while (i <= n)
        {
            list.add(list.first() * 2 + 1);
            list.add(list.first() * 3 + 1);
            list.remove(list.first());
            i++;
        }


        return list.first();
    }
}