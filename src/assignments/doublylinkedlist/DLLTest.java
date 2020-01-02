package assignments.doublylinkedlist;

import assignments.FunctionalList;

import java.util.*;

public class DLLTest {
    public static void test(DoublyLinkedList<Integer> list)
    {
        System.out.println("Can you add a number to the list when it's empty?");
        list.add(1); System.out.println(list);
        System.out.println("Can you add a number to the list when it's not (append to the end)?");
        list.add(2); System.out.println(list);
        System.out.println("Cleared list.");
        list.clear(); System.out.println(list);
        System.out.println("Can you add a number at position 0 when it's empty?");
        list.add(0, 1); System.out.println(list);
        System.out.println("Can you add a number at position 0 when it's not empty?");
        list.add(0, 2); System.out.println(list);
        System.out.println("Cleared list.");
        list.clear(); System.out.println(list.size());
        System.out.println("Can you add a number a position equal to the size of the list when it's empty?");
        list.add(0, 1); System.out.println(list);
        System.out.println("Can you add a number a position equal to the size of the list when it's not empty?");
        list.add(1, 2); System.out.println(list);
        System.out.println("Can you add a number at any other position inside of the list (insert in the middle)?");
        list.add(1, 3); System.out.println(list);
        System.out.println("Does it throw the appropriate error if you try to add a number in a position that's not possible?");
        try {
            list.add(54, 2);
            System.out.println(list);
        } catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }

        try {
            list.add(-1, 2);
            System.out.println(list);
        } catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }

        System.out.println("Can you remove a number from position 0 when the size of the list is 1?");
        list.clear(); list.add(4); System.out.println(list);
        list.remove(0); System.out.println(list);

        System.out.println("Can you remove a number from position size() - 1 when the size of the list is 1?");
        list.add(1);
        System.out.println(list); list.remove(list.size() - 1);
        System.out.println(list);

        list.addAll(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8}));
        System.out.println("Can you remove a number from position 0 when the size is greater than 1?");
        list.remove(0);
        System.out.println(list);
        System.out.println("Can you remove a number from position size() - 1 when the size is greater than 1?");
        list.remove(list.size() - 1);
        System.out.println(list);
    }

    public static void main(String[] args)
    {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        for (int i=0;i<40;i++)
        {
            list.add((int) (Math.random() * 70));
        }

        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);

        //test(list);
        //System.out.println(list.subList(9990, 10000) + " " + list.size());
    }
}
