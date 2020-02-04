package assignments.doublylinkedlist;

import org.junit.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

    static void sortStr(List<String> arr)
    {

        for (int i=0; i<arr.size()-1; i++)
        {
            int j = i;

            /*
             Move elements of arr[0..i-1], that are
             greater than key, to one position ahead
             of their current position
            */
            try {
                while (arr.get(j).toUpperCase().compareTo(arr.get(j + 1).toUpperCase()) > 0) {
                    arr.add(j, arr.remove(j + 1));
                    j--;
                }
            }catch(ArrayIndexOutOfBoundsException e){}
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
        System.out.println("Sorted list in ascending order (Standard insertion sort): " + list);
        list.sort(Comparator.naturalOrder());
        System.out.println("Sorted list in ascending order (Stream sort): " + list);
        //both sorts work
        System.out.println("Median of the list: " + list.median().stream().mapToInt(Integer::intValue).average().orElse(0));

        list.clear();
        list = null;
        // free up memory

        DoublyLinkedList<String> list2 = new DoublyLinkedList<>();
        list2.addAll(
            List.<String>of(
                ("Store a “sentence” in a string and then store each of the words in the sentence in your\n" +
                "DoublyLinkedList.").split(" ")
            ));
        System.out.println("Stored “sentence” in a string and then stored each of the words in the sentence in " +
                "DoublyLinkedList: " + list2);
        list2.removeIf(e->{System.out.println(e); return e.length()<=3;});
        System.out.println("Removed all words with 3 or less characters: " + list2);
        //Collections.sort(list2);
        sortStr(list2);
        System.out.println("Sorted list alphabetically: " + list2);
        Assert.assertEquals(12, list2.size());
        System.out.println(list2.size());
    }
}
