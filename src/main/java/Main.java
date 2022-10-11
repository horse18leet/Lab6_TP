import java.util.Arrays;

public class Main extends Thread {
    public static void main (String[] args) {
        method1();
        method2();
    }
    static final int SIZE = 50_000_000;
    public static void method1() {
        System.out.println("Первый метод");

        float[] arr = new float[SIZE];

        Arrays.fill(arr, 1);

        long a = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) arr [i] = (float)(arr [i] * Math.sin (0.2f + i/5) * Math.cos (0.2f + i/5) * Math.cos (0.4f + i / 2));

        System.out.println(System.currentTimeMillis() - a + "мс");
        System.out.println();

        System.out.println(arr[0] + " " + arr[arr.length-1]);
    }

    public static void method2() {
        System.out.println("Второй метод");

        final int h = SIZE/2;
        float[] arr = new float[SIZE];
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];

        Arrays.fill(arr, 1);

        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);

        long a = System.currentTimeMillis();

        Thread thread = new Thread(() -> {
            for (int i = 0; i < arr1.length; i++) arr1[i] = (float)(arr1[i] * Math.sin (0.2f + i/5) * Math.cos (0.2f + i/5) * Math.cos (0.4f + i / 2));
        });
        Thread thread2 = new Thread(() -> {
            for (int i = arr2.length; i < arr2.length*2; i++) arr2[i-arr2.length] = (float)(arr2[i-arr2.length] * Math.sin (0.2f + i/5) * Math.cos (0.2f + i/5) * Math.cos (0.4f + i / 2));
        });

        thread.start();
        thread2.start();

        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);


        System.out.println(System.currentTimeMillis() - a + "мс");
        System.out.println();

        System.out.println(arr[0] + " " + arr[arr.length-1]);
    }
}