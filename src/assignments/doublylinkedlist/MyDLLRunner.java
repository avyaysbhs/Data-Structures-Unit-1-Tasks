package assignments.doublylinkedlist;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class MyDLLRunner {
    static void sort(List<Integer> arr)
    {
        int n = arr.size();
        for (int i=1; i<n; ++i)
        {
            int key = arr.get(i);
            int j = i-1;

            /*
             Move elements of arr[0..i-1], that are
             greater than key, to one position ahead
             of their current position
            */
            while (j>=0 && arr.get(j) > key)
            {
                arr.set(j+1, arr.get(j));
                j = j-1;
            }

            arr.set(j+1, key);
        }
    }

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
        System.out.println("Sum of even indexed values: " + list.stream().filter(e -> bool.getAndSet(!bool.get())).reduce(Integer::sum).orElse(-1));
        bool.set(false);
        System.out.println("Sum of odd indexed values: " + list.stream().filter(e -> bool.getAndSet(!bool.get())).reduce(Integer::sum).orElse(-1));
        System.out.println("Duplicating all even values in the list and adding them to the end...");
        list.addAll(list.stream().filter(e -> e % 2 == 0).collect(Collectors.toList()));
        System.out.println("List: " + list);
        List<Integer> temp = list.stream().filter(e -> e % 3 != 0).collect(Collectors.toList());
        list.clear(); list.addAll(temp);
        System.out.println("Removed all values divisible by 3 - Current List: " + list);
        list.add(3, 55555);
        System.out.println("Inserted 55555 into list[3] || 4th position: " + list);
        sort(list);
        System.out.println("Sorted list in ascending order: " + list);
    }
}
