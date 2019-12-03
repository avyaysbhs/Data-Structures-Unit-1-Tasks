package codewars;

import java.util.*;

class DoubleLinear {
    private static Set<Integer> createUpTo(Set<Integer> list, int n)
    {
        list.add(1);
        synchronized (list) {
            try
            {
                while (list.size() < n) {
                    Thread.sleep(50);
                    Iterator<Integer> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        int i = iterator.next();
                        list.add(i * 2 + 1);
                        int z = i * 3 + 1;
                        //list.add(z);
                    }
                    System.out.println(list);
                }
            } catch (InterruptedException e)
            {

            }
        }
        return list;
    }

    public static int dblLinear (int n) {
        List<Integer> list = new ArrayList<>();
        list.addAll(createUpTo(new HashSet<>(), n));
        Collections.sort(list);
        return list.get(n - 1);
    }
}