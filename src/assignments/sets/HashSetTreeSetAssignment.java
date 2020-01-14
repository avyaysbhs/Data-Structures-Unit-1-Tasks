package assignments.sets;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class HashSetTreeSetAssignment {
    public static <T> Set<T> union(Set<T>... sets)
    {
        Set<T> out = new HashSet<>();
        for (Set<T> set: sets) out.addAll(set);
        return out;
    }

    public static <T> Set<T> intersection(Set<T>... sets)
    {
        Set<T> out = new HashSet<>();
        for (Set<T> set: sets) out.addAll(set);
        out.removeIf(e -> {
            for (Set<T> set: sets) if (!set.contains(e)) return true;
            return false;
        }); return out;
    }

    public static void populate(Set<Integer> set, int size, int min, int max)
    {
        if ((max - min) < size) throw new UnsupportedOperationException();
        while (set.size() < size)
            set.add((int) (Math.random() * (max - min)) + min);
    }

    public static <T> Set<T> problem2(Set<T> one, Set<T> two)
    {
        return intersection(one, two);
    }

    public static <T> Set<T> problem3(Set<T> one, Set<T> two)
    {
        return union(one, two);
    }

    public static Set<Integer> problem4(Set<Integer> one, Set<Integer> two)
    {
        return intersection(one, two).stream().filter(e -> e % 2 == 0).collect(Collectors.toSet());
    }

    public static Set<Integer> problem5(Set<Integer> one, Set<Integer> two)
    {
        return union(one, two).stream().filter(e -> e % 2 == 0).collect(Collectors.toSet());
    }

    public static void main(String[] args)
    {
        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();

        populate(set1, 20, 0, 60);
        populate(set2, 20, 0, 60);

        System.out.println(set1);
        System.out.println(set2);

        System.out.println(problem2(set1, set2));
        System.out.println(problem3(set1, set2));
        System.out.println(problem4(set1, set2));
        System.out.println(problem5(set1, set2));
    }
}
