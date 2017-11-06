public class Main {
    public static void main(String[] args) {

        long start = System.nanoTime();
        new Mandelbrot().setVisible(true);
        long end = System.nanoTime();

        long result = (long) ((end - start) / (Math.pow(10,6)));
        System.out.println("Time: " + result);
    }
}
