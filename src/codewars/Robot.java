package codewars;

import java.util.List;
import java.util.regex.*;

public class Robot {
    private List<String> words = new java.util.ArrayList<>();
    private final static String TAUGHT_ALREADY = "I already know the word %s";
    private final static String LEARNED_THANKS = "Thank you for teaching me %s";
    private final static String NO_COMPREHENSION = "I do not understand the input";

    private final static Pattern ALPHA_ONLY = Pattern.compile("^[a-zA-Z]+$");

    public String learnWord(String word)
    {
        if (!ALPHA_ONLY.matcher(word).matches())
            return NO_COMPREHENSION;

        if (words.contains(word.toLowerCase()))
            return String.format(TAUGHT_ALREADY, word);

        words.add(word.toLowerCase());
        return String.format(LEARNED_THANKS, word);
    }
}