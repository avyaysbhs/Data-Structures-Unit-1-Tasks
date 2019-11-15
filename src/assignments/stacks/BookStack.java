package assignments.stacks;

import assignments.Builder;

import java.io.*;
import java.util.Stack;

public class BookStack {

    public static class Book
    {
        private static Builder<Book> bookBuilder = new Builder<>(Book.class);
        private String truncatedTitle;
        private String fullTitle;
        private String author;
        private String source;
        private int voters;
        private int age;

        public static Book createFrom(String source)
        {
            String[] data = source.split("\n");
            return bookBuilder.create()
                .set("truncatedTitle", data[1])
                .set("fullTitle", data[2].replace("\t", ""))
                .set("author", data[3].replace("by ", ""))
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

        while (stack.size() > 0)
        {
            System.out.println(stack.pop());
        }
    }

}
