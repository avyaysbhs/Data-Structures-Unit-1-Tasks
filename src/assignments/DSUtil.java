package assignments;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DSUtil {
    public static void array_copy(Object[] a, Object[] b)
    {
        for (int i=0; i<a.length; i++)
        {
            if (i < b.length)
                b[i] = a[i];
        }
    }

    public static void array_copy(int[] a, int[] b)
    {
        for (int i=0; i<a.length; i++)
        {
            if (i < b.length)
                b[i] = a[i];
        }
    }

    public static void array_copy(String[] a, String[] b)
    {
        for (int i=0; i<a.length; i++)
        {
            if (i < b.length)
                b[i] = a[i];
        }
    }

    public static void array_copy(char[] a, char[] b)
    {
        for (int i=0; i<a.length; i++)
        {
            if (i < b.length)
                b[i] = a[i];
        }
    }

    public static void array_copy(float[] a, float[] b)
    {
        for (int i=0; i<a.length; i++)
        {
            if (i < b.length)
                b[i] = a[i];
        }
    }

    public static void array_copy(double[] a, double[] b)
    {
        for (int i=0; i<a.length; i++)
        {
            if (i < b.length)
                b[i] = a[i];
        }
    }

    public static class NumberSet
    {
        private int[] _numbers;

        public NumberSet(int[] input_set)
        {
            _numbers = input_set;
        }

        public static DSUtil.NumberSet readFromString(String _input)
        {
            String[] numbers = _input.split(" ");
            int[] ns = new int[numbers.length]; int i=0;

            for (String num: numbers)
            {
                ns[i++] = Integer.parseInt(num);
            }

            return new DSUtil.NumberSet(ns);
        }

        public boolean contains(int a)
        {
            for (int n: _numbers)
            {
                if (n == a)
                {
                    return true;
                }
            }

            return false;
        }

        public DSUtil.NumberSet remove(int a)
        {
            if (contains(a))
            {
                int[] n = new int[_numbers.length - 1];
                boolean removed = false; int c = 0;
                for (int i=0;i<_numbers.length;i++)
                {
                    if (removed || _numbers[i] != a)
                    {
                        n[c] = _numbers[i];
                        c++;
                    } else if (!removed && _numbers[i] == a)
                    {
                        removed = true;
                    }
                }
                return new DSUtil.NumberSet(n);
            }
            return this;
        }

        public boolean isEmpty()
        {
            return _numbers.length == 0;
        }

        public int[] getIntersection(DSUtil.NumberSet other)
        {
            int[] intersection = new int[0];
            for (int i=0; i<_numbers.length; i++)
            {
                if (other.contains(_numbers[i]))
                {
                    other = other.remove(_numbers[i]);
                    int[] placeholder = intersection;
                    intersection = new int[intersection.length+1];
                    DSUtil.array_copy(placeholder, intersection);
                    intersection[intersection.length - 1] = _numbers[i];
                }
            }
            return intersection;
        }

        public boolean intersects(DSUtil.NumberSet other)
        {
            return getIntersection(other).length > 0;
        }

        public void sort()
        {
            sort(this._numbers);
        }

        public static void sort(int[] array)
        {
            int n = array.length;
            for (int i = 1; i < n; i++)
            {
                int key = array[i];
                int j = i - 1;

                while (array[j] > key && j >= 0)
                {
                    array[j + 1] = array[j];
                    j = j - 1;
                }
                array[j + 1] = key;
            }
        }

        public String toString()
        {
            return java.util.Arrays.toString(_numbers).replace("[", "{").replace("]", "}");
        }
    }

    public static interface NewLineReadProcessor
    {
        void processNewLine(String s);
    }

    public static interface ReductionOperation<T>
    {
        T reduce(T a, T b);
    }

    public static interface ComparisonOperation<T>
    {
        boolean compare(T a, T b);
    }

    public static interface FilterOperation<T>
    {
        boolean filter(T t);
    }

    public static String readFileContents(File f) throws IOException
    {
        BufferedReader input = new BufferedReader(
                new FileReader(f)
        );

        String text, output = "";
        while(( text = input.readLine()) != null )
        {
            output += text + "\n";
        }

        input.close();
        return output;
    }

    public static String readFileContents(File f, NewLineReadProcessor eventHandler) throws IOException {
        BufferedReader input = new BufferedReader(
                new FileReader(f)
        );

        String text, output = "";
        while(( text = input.readLine()) != null )
        {
            eventHandler.processNewLine(text);
            output += text + "\n";
        }

        input.close();
        return output;
    }

    public static String readFileContents(String fileName) throws IOException
    { return readFileContents(new File(fileName)); }
    public static String readFileContents(String fileName, NewLineReadProcessor eventHandler) throws IOException
    { return readFileContents(new File(fileName), eventHandler); }

    public static int[] ListIntegerToArrayPrimitive(List<Integer> list)
    {
        AtomicInteger c = new AtomicInteger(0); int[] output = new int[list.size()];
        list.forEach(e -> {
            output[c.getAndIncrement()] = e.intValue();
        });
        return output;
    }

    public static List<Integer> ArrayPrimitiveToListInteger(int[] array)
    {
        ArrayList<Integer> list = new ArrayList<>();
        for (int n: array)
        { list.add(n); }
        return list;
    }

    public static class BetterList<E> extends ArrayList<E>
    {
        private static final long serialVersionUID = 7978345468538524076L;

        public BetterList(Collection<E> originalCollection)
        {
            addAll(originalCollection);
        }

        public BetterList(int s)
        {
            super(s);
        }

        public BetterList(E... items)
        {
            for (E item: items)
            {
                add(item);
            }
        }

        public BetterList()
        {
            super(10);
        }

        public BetterList<E> cloneList()
        {
            BetterList<E> out = new BetterList<>();

            for (E e: this)
            {
                out.add(e);
            }

            return out;
        }

        public final void recycle()
        {
            while (size() > 0)
                remove(0);
            return;
        }

        public BetterList<E> sector(int start, int end)
        {
            BetterList<E> out = new BetterList<>();

            for (int i=start; i<end; i++)
            {
                out.add(get(i));
            }

            return out;
        }

        public BetterList<E> filter(FilterOperation<E> filter)
        {
            BetterList<E> out = new BetterList<>();

            for (E item: this)
            {
                if (filter.filter(item))
                {
                    out.add(item);
                }
            }

            return out;
        }

        public BetterList<E> filter(Function<E, Boolean> _filter)
        {
            return filter(new FilterOperation<E>()
            {
                public boolean filter(E in)
                {
                    return _filter.call(in);
                }
            });
        }

        public BetterList<E> sort(ComparisonOperation<E> comparison)
        {
            if (size() < 2) return cloneList();

            BetterList<E> out = cloneList();

            int j;

            for (int i = 1; i < out.size(); i++) {
                E temp = out.get(i);
                j = i;
                while ((j > 0) && comparison.compare(out.get(j - 1), temp)) {
                    out.set(j, out.get(j - 1));
                    j--;
                }
                out.set(j, temp);
            }

            return out;
        }

        public boolean equals(BetterList<E> other)
        {
            if (other.size() != this.size()) return false;
            for (int i=0;i<other.size();i++)
            {
                if (other.get(i) != this.get(i)) {
                    System.out.println(other.get(i) + " " + this.get(i));
                }
                return false;
            }
            return true;
        }

        public BetterList<E> reverse()
        {
            BetterList<E> out = new BetterList<>(size());

            for (int i=size() - 1;i>=0;i--)
            {
                out.add(get(i));
            }

            return out;
        }

        public BetterList<E> reverse(int times)
        {
            BetterList<E> out = cloneList();
            for (int i=0;i<times;i++)
            {
                out = out.reverse();
            }
            return out;
        }

        public BetterList<E> rotate(int times)
        {
            BetterList<E> out = cloneList();
            for (int i=0;i<times;i++)
            {
                out.add(0, out.remove(out.size() - 1));
            }
            return out;
        }

        public E reduce(ReductionOperation<E> reducer)
        {
            E out = get(0);

            for (int i=1;i<size();i++)
            {
                out = reducer.reduce(out, get(i));
            }

            return out;
        }

        public E reduce(Function<E, E> reducer)
        {
            return reduce(new ReductionOperation<E>()
            {
                public E reduce(E a, E b)
                {
                    return reducer.call(a, b);
                }
            });
        }
    }

    public interface Function<I, O>
    {
        O call(I... argv);
    }

    public interface Command
    {
        void call();
    }

    public interface ArgCommand<I>
    {
        void call(I... argv);
    }

    public interface SingleArgCommand<I>
    {
        void call(I arg);
    }
}