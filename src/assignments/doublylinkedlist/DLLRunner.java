package assignments.doublylinkedlist;

public class DLLRunner
{
    public DLLRunner()
    {
        //This is to test your code. If it works, you should get my answers!

        DoublyLinkedList<Integer> list=new DoublyLinkedList<Integer>();
        list.add(10);
        list.add(0,5);
        list.add(list.size()-1,8);
        list.add(1,12);
        list.add(20);
        list.add(25);
        list.add(4,100);
        list.add(0,0);
        list.add(list.size()-1,1000);	//Should put 1000 in front of 25 (which had been the end)
        list.add(list.size(),555);	//should make 555 the last number in the list (the end)
        System.out.println("Size: "+list.size());

        System.out.println("List: "+list);
        System.out.println("List reversed: "+list.toReversedString());

        System.out.println("\nValues at Front and End");
        System.out.println("\tFront: "+list.getRoot());
        System.out.println("\tEnd: "+list.getEnd());

        list.remove(2);
        list.remove(3);
        list.remove(0);
        list.remove(list.size()-1);

        System.out.println("\nRemove values at indexes 2, 3, 0, and then size()-1 respectively.");
        System.out.println("\tList: "+list);
        System.out.println("\tList reversed: "+list.toReversedString());


        System.out.println("\nContains Test:");
        System.out.println("\tContains 4: "+list.contains(4));
        System.out.println("\tContains 20: "+list.contains(20));

        System.out.println("\nHere is the list, one value at a time: ");
        DoublyLinkedList<Integer> tempList=new DoublyLinkedList<Integer>();
        while(!list.isEmpty())
        {
            int val=list.getRoot();
            tempList.add(list.next());
            System.out.print(val+" ");
        }
        list=tempList;

        System.out.println();
        list.clear();
        System.out.println("\nClear List!");
        System.out.println("\tList: "+list);
        System.out.println("\tList reversed: "+list.toReversedString());


        DoublyLinkedList<String> stringList=new DoublyLinkedList<String>();
        stringList.add("St1");
        stringList.add("St2");
        stringList.add("St3");
        System.out.println("String List: "+stringList);
        System.out.println("Reversed String List: "+stringList.toReversedString());
    }
    public static void main(String[] args)
    {
        DLLRunner app=new DLLRunner();
    }

}