package assignments.doublylinkedlist;

import assignments.FunctionalList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class MyDLLRunner {
    public static void main(String[] args)
    {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        for (int i=0;i<30;i++)
            list.add((int) (Math.random() * 1000) + 1);
        System.out.println("List: " + list);
        System.out.println("Reversed List: " + list.toReversedString());
        System.out.println("List size: " + list.size());
        System.out.println("Sum: " + list.stream().reduce((b, a) -> b + a).get());
        AtomicBoolean bool = new AtomicBoolean(true);
        System.out.println("Sum of even indexed values: " + list.stream().filter(e -> bool.getAndSet(!bool.get())).reduce((b, a) -> b + a).get());
        bool.set(false);
        System.out.println("Sum of odd indexed values: " + list.stream().filter(e -> bool.getAndSet(!bool.get())).reduce((b, a) -> b + a).get());
        System.out.println("Duplicating all even values in the list and adding them to the end...");
        list.addAll(list.stream().filter(e -> e % 2 == 0).collect(Collectors.toList()));
        System.out.println("List: " + list);
        List<Integer> temp = list.stream().filter(e -> e % 3 != 0).collect(Collectors.toList());
        list.clear(); list.addAll(temp);
        System.out.println("Removed all values divisible by 3 - Current List: " + list);
        list.add(3, 55555);
        System.out.println("Inserted 55555 into list[3] || 4th position: " + list);
        list.sort((FunctionalList.Comparison<Integer>) (a, b) -> b > a);
        System.out.println("Sorted list in ascending order: " + list);
    }
}
