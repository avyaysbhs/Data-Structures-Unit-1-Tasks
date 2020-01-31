package assignments.queues;

import assignments.DSUtil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Part1WordsQueue {
    public static class Word implements Comparable<Word>
    {
        public final String value;
        private final int factor;

        public Word(String v, boolean descending)
        {
            value = v;
            factor = -1;
        }

        public Word(String v)
        {
            value = v;
            factor = 1;
        }

        public int compareTo(Word other)
        {
            return factor * value.toLowerCase().compareTo(other.value.toLowerCase());
        }

        public String toString()
        {
            return value;
        }
    }

    public static void main(String[] args) {
        Queue<Word> linkedList = new LinkedList<>();
        Queue<Word> priorityQueue = new PriorityQueue<>();

        try {
            DSUtil.readFileContents(new File("sample_paragraph.txt"), e ->
            {
                for (String w : e.split(" "))
                    linkedList.add(new Word(w));

                for (String w : e.split(" "))
                    priorityQueue.add(new Word(w, false));
            });
        } catch (IOException e) { e.printStackTrace();}

        while (linkedList.peek() != null)
        {
            System.out.println(String.format("%-40s %s", linkedList.poll(), priorityQueue.poll()));
        }
    }
}
