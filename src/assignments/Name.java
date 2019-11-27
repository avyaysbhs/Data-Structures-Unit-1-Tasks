package assignments;

public class Name
{
    private String f;
    private String l;
    private String m;

    public final static String LAST_COMMA_FIRST = "{last}, {first}";
    public final static String MIDDLE_LAST_COMMA_FIRST = "{middle} {last}, {first}";
    public final static String LAST_COMMA_FIRST_MIDDLE = "{last}, {first} {middle}";
    public final static String FIRST_MIDDLE_LAST = "{first} {middle} {last}";

    public Name(String src)
    {
        f = src.substring(0, src.indexOf(" "));
        src = src.substring(f.length());
        if (src.chars().filter(character -> character == ' ').count() > 1)
        {
            m = src.substring(src.indexOf(" ") + 1);
            m = m.substring(0, m.indexOf(" "));
            src = src.substring(m.length());
        }
        l = src.substring(src.indexOf(" ") + 1);
    }

    public String getFirst() { return f; }
    public String getLast() { return l; }
    public String format(String format)
    {
        return format.replaceAll("(\\{last\\})", l).replaceAll("(\\{first\\})", f);
    }
}
