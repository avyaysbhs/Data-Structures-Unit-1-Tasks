package assignments.queues;

import assignments.Builder;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Queue;

public class PassengerQueue {

    public static Builder<Passenger> passengerBuilder = new Builder<>(Passenger.class);

    public static class Passenger implements Comparable<Passenger>
    {
        private String name;
        private String destination;
        private String arrivalTimeString;
        private long arrivalTime;

        public int compareTo(Passenger other)
        {
            long otherArrival = passengerBuilder.get("arrivalTime", long.class, other);
            if (otherArrival > arrivalTime)
                return 1;
            else if (otherArrival == arrivalTime) return 0;
            return -1;
        }

        public String toString()
        {
            return name + "\n"
                    + destination + "\n"
                    + arrivalTimeString + "\n";
        }
    }

    public static long readTime(String time)
    {
        int colonIndex = time.indexOf(":");
        int hours = Integer.parseInt(time.substring(0, colonIndex));
        int minutes = Integer.parseInt(time.substring(colonIndex + 1, colonIndex + 3));
        if (time.contains("PM")) hours += 12;
        return (60 * hours) + minutes;
    }

    public static void main(String[] args)
    {
        Queue<Passenger> queue = new PriorityQueue<>();
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
                    queue.add(passengerBuilder.create()
                        .set("name", data[0])
                        .set("destination", data[1])
                        .set("arrivalTimeString", data[2])
                        .set("arrivalTime", readTime(data[2]))
                        .constructAndDispose());
                    full = "";
                    lineNumber = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (queue.peek() != null)
        {
            System.out.println(queue.poll());
        }
    }

}