package assignments.stacks;

import java.util.Stack;

public class Part2StringReverseStack {
    public static String reverse(String in)
    {
        String out = "";
        Stack<Character> stack = new Stack<>();
        in.chars().forEach(e -> stack.add((char) e));
        while (stack.size() > 0) out += stack.pop();
        return out;
    }

    public static void main(String[] args) {
        System.out.println("You're funny");
        System.out.println(reverse("You're funny"));
        System.out.println("100BestSciFiBooksOf21stCentury");
        System.out.println(reverse("100BestSciFiBooksOf21stCentury"));
    }
}
