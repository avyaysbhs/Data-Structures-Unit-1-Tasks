package assignments.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HashMapTask1 {
    private final static Pattern letters = Pattern.compile("[A-Za-z]");
    private final static boolean lettersOnly = true;
    private final static boolean caseSensitive = true;

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name (with extension):");
        String fileName = sc.nextLine();
        File f = new File(fileName);

        if (f.exists() && f.canRead())
        {
            CharBuffer buffer = CharBuffer.allocate((int) f.length());
            Map<Character, Integer> frequency = new HashMap<>();

            try {
                new BufferedReader(new FileReader(f)).read(buffer); buffer.flip();
            } catch (IOException e) {
                e.printStackTrace(); return;
            }

            buffer.chars().mapToObj(e -> "" + (char) e).map(c -> caseSensitive ? c : c.toLowerCase()).filter(e ->
                !lettersOnly || letters.matcher(e).matches()
            ).map(e -> e.charAt(0)).forEach(c -> {
                frequency.put(c, frequency.getOrDefault(c, 0) + 1);
            });

            System.out.println(frequency);
        }
    }
}
