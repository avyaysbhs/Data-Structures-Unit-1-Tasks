package assignments;

public final class Math2 {
    public static void normalize(float[] arr)
    {
        float max = 0;
        for (float f: arr)
            max = f > max ? f : max;
        for (int i=0;i<arr.length;i++)
            arr[i] = arr[i]/max;
    }

    public static void normalize(float[][] arr)
    {
        float max = 0;
        for (float[] r: arr)
            for (float f: r)
                max = f > max ? f : max;
        for (int i=0;i<arr.length;i++)
            for (int j=0;j<arr[i].length;j++)
                arr[i][j] = arr[i][j]/max;
    }

    public static int normalize(int n)
    {
        return Integer.compare(n, 0);
    }

    public static int clamp(int n, int min, int max)
    {
        return n > max ? max : (n < min ? min : n);
    }
}
