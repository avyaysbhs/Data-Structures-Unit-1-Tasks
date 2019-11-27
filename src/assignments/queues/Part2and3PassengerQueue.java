package assignments.queues;

import assignments.Builder;
import assignments.Name;
import assignments.Time;

import java.io.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Part2and3PassengerQueue {

    public static Builder<Passenger> passengerBuilder = new Builder<>(Passenger.class);
    public static Time CURRENT_TIME;

    public static class Passenger implements Comparable<Passenger>
    {
        private Name name;
        private String destination;
        private Time arrivalTime;

        /**
         *
         * @param other
         * @return comparison only if current passenger's ETD is within the next hour, otherwise same time as added
         */
        public int compareTo(Passenger other)
        {
            long etd_this = arrivalTime.getValue() - CURRENT_TIME.getValue();

            if (etd_this <= 60)
                return -1;
            return 1;
        }

        public String getEtd()
        {
            long etd_this = arrivalTime.getValue() - CURRENT_TIME.getValue();
            return Time.from(etd_this).toValueString();
        }

        public String toString()
        {
            return name.format(Name.LAST_COMMA_FIRST) + " - " + destination + " - " + arrivalTime + " - " + getEtd();
        }
    }

    public static void main(String[] args)
    {
        CURRENT_TIME = Time.parse("9:03 AM");
        Queue<Passenger> priorityQueue = new PriorityQueue<>();
        Queue<Passenger> linkedList = new LinkedList<Passenger>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("PassengerInfo.txt")));
            String line;
            String full = "";
            int lineNumber = 0;
            while ((line = reader.readLine()) != null)
            {
                full += line + "\n";
                lineNumber++;
                if (lineNumber > 2)
                {
                    String[] data = full.split("\n");
                    priorityQueue.add(passengerBuilder.create()
                        .set("name", new Name(data[0]))
                        .set("destination", data[1])
                        .set("arrivalTime", Time.parse(data[2]))
                        .constructAndDispose());
                    full = "";
                    lineNumber = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Before sort:");
        while (linkedList.peek() != null)
            System.out.println(linkedList.poll());

        System.out.println("After sort:");
        while (priorityQueue.peek() != null)
            System.out.println(priorityQueue.poll());
    }
}