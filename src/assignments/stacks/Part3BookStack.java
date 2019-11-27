package assignments.stacks;

import assignments.Builder;
import assignments.Function;
import assignments.Name;

import java.io.*;
import java.util.Stack;

public class Part3BookStack {

    public static class Book
    {
        private static Builder<Book> bookBuilder = new Builder<>(Book.class);
        private String truncatedTitle;
        private String fullTitle;
        private Name author;
        private String source;
        private int voters;
        private double rating;
        private int age;

        public Name getAuthor() {
            return author;
        }

        public String getTitle()
        {
            return fullTitle;
        }

        public double getRating()
        {
            return rating;
        }

        public static Book createFrom(String source)
        {
            String[] data = source.split("\n");
            return bookBuilder.create()
                .set("truncatedTitle", data[1])
                .set("fullTitle", data[2].replace("\t", ""))
                .set("author", new Name(data[3].replace("by ", "")))
                .set("rating", Double.parseDouble(data[4].substring(0, 4)))
                .set("source", source)
                .constructAndDispose();
        }

        public String toString()
        {
            return source;
        }
    }

    public static void main(String[] args)
    {
        String full = "";
        Stack<Book> stack = new Stack<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("100BestSciFi21stCentury.txt")));
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null)
            {
                full += line + "\n";
                lineNumber++;
                if (lineNumber > 6)
                {
                    stack.add(Book.createFrom(full));
                    full = "";
                    lineNumber = 0;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stack.add(Book.createFrom(full));

        Function<String, Boolean> validName = name ->
        {
            return name.toLowerCase().substring(0, 1).chars().filter(c -> {
                return c == 'e' || c == 'h' || c == 'p' || c == 's';
            }).count() > 0;
        };

        while (stack.size() > 0)
        {
            Book book = stack.pop();
            if (validName.out(book.getAuthor().getFirst()) || validName.out(book.getAuthor().getLast()))
                System.out.println(book.getAuthor().format(Name.LAST_COMMA_FIRST) + " - " + book.getTitle() + "; " + book.getRating());
        }
    }

}
