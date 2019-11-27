package assignments;

public class Time implements Comparable<Time>
{
    public final static Builder<Time> timeConstructor = new Builder<Time>(Time.class);
    private int hour;
    private int minute;
    private long value;

    public long getValue() { return value; }

    public int compareTo(Time other)
    {
        return Math2.normalize((int) (other.getValue() - value));
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
            .set("value", (hours * 60) + minutes)
            .constructAndDispose();
    }

    public static final Time from(long t)
    {
        int h = (int) t/60;
        int m = (int) t % 60;
        return timeConstructor.create()
            .set("hour", h)
            .set("minute", m)
            .set("value", t)
            .constructAndDispose();
    }

    public String toString() {
        int hr = hour;
        String ext = "AM";
        if (hr > 12)
        {
            hr -= 12;
            ext = "PM";
        }
        return hr + ":" + (minute > 9 ? minute : "0" + minute) + " " + ext;
    }

    public String toValueString()
    {
        return hour + ":" + (minute > 9 ? minute : "0" + minute);
    }
}
