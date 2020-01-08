package assignments;

import assignments.doublylinkedlist.DoublyLinkedList;

import java.util.Collections;
import java.util.List;

public interface FunctionalList<E> extends List<E> {
    public static interface Function<I, O>
    {
        O process(I in);
    }

    public static interface Filter<E>
    {
        boolean filter(E in);
    }

    public static interface Comparison<E>
    {
        boolean compare(E a, E b);
    }

    default <O> FunctionalList<O> map(Function<E, O> function)
    {
        FunctionalList<O> out = new DoublyLinkedList<O>();
        forEach(e -> out.add(function.process(e)));
        return out;
    };

    default FunctionalList<E> filter(Filter<E> function)
    {
        FunctionalList<E> out = new DoublyLinkedList<>();
        forEach(e -> { if (function.filter(e)) { out.add(e); } });
        return out;
    };

    default FunctionalList<E> sort(Comparison<E> function)
    {
        FunctionalList<E> out = new DoublyLinkedList<>();
        int n = size();
        for (int i = 1; i < n; ++i) {
            System.out.println(i + " " + size());

            E key = get(i);
            int j = i - 1;


            while (j >= 0 && function.compare(get(j), key)) {
                set(j + 1, get(j));
                j = j - 1;
            }

            set(j + 1, key);
        }
        return out;
    };

    static <E extends Comparable<? super E>> void sort(FunctionalList<E> list)
    {
        list.sort((Comparison<E>) (b, a) ->
        {
            return b.compareTo(a) >= 1;
        });
    }
}
