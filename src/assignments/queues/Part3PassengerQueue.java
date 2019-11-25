package assignments.queues;

import assignments.Builder;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Queue;

public class Part3PassengerQueue {

    public static Builder<Passenger> passengerBuilder = new Builder<>(Passenger.class);
    public static Time CURRENT_TIME;

    public static class Time implements Comparable<Time>
    {
        public final static Builder<Time> timeConstructor = new Builder<Time>(Time.class);
        public int hour;
        public int minute;
        private long value;

        public long getValue() { return value; }

        public int compareTo(Time other)
        {
            return normalize((int) (other.getValue() - value));
        }

        public final static Time parse(String src)
        {
            int colonIndex = src.indexOf(":");
            int hours = Integer.parseInt(src.substring(0, colonIndex));
            int minutes = Integer.parseInt(src.substring(colonIndex + 1, colonIndex + 3));
            if (src.contains("PM")) hours += 12;

            return timeConstructor
                .create()
                .set("hour", hours)
                .set("minute", minutes)
                .set("value", (60 * hours) + minutes)
                .constructAndDispose();
        }
    }

    private static int normalize(int n)
    {
        if (n > 0) return 1;
        if (n < 0) return -1;
        return 0;
    }

    public static class Passenger implements Comparable<Passenger>
    {
        private String name;
        private String destination;
        private String arrivalTimeString;
        private Time arrivalTime;

        /**
         *
         * @param other
         * @return comparison only if current passenger's ETD is within the next hour, otherwise same time as added
         */
        public int compareTo(Passenger other)
        {
            Time otherArrival = passengerBuilder.get("arrivalTime", Time.class, other);
            long etd_other = otherArrival.getValue() - CURRENT_TIME.getValue();
            long etd_this = arrivalTime.getValue() - CURRENT_TIME.getValue();
            if (etd_this <= 60)
                return (int) (etd_other - etd_this);
            return 0;
        }

        public String toString()
        {
            return name + "\n"
                    + destination + "\n"
                    + arrivalTimeString + "\n";
        }
    }

    public static void main(String[] args)
    {
        CURRENT_TIME = Time.parse("9:03 AM");
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
                        .set("arrivalTime", Time.parse(data[2]))
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