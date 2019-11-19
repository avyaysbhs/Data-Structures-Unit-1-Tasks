package assignments.doublylinkedlist;

import java.util.ArrayList;
import java.util.List;

public class DLLRunner {
    public static void main(String[] args)
    {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        list.add(1);
        list.add(2);
        list.add(4);
        list.addAll(arrayList);

        for (int i: list)
        {
            System.out.println(i);
        }
        System.out.println(list.get(2));
    }
}
