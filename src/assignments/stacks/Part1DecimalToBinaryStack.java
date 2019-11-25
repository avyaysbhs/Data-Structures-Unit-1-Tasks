package assignments.stacks;

import java.util.Stack;

public class Part1DecimalToBinaryStack {
    public static String decimal_to_binary(int n)
    {
        String out = "";
        Stack<Integer> stack = new Stack<>();
        while (n > 0)
        {
            stack.add(n % 2);
            n /= 2;
        }

        while (stack.size() > 0)
            out += stack.pop();

        return out;
    }

    public static void main(String[] args)
    {
        System.out.println(decimal_to_binary(24));
        System.out.println(decimal_to_binary(124));
        System.out.println(decimal_to_binary(14724));
    }
}
