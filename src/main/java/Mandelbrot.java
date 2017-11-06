import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Mandelbrot extends JFrame {

    private final int MAX_ITER = 10000;
    private final double ZOOM = 550;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;

    public Mandelbrot() {
        super("Mandelbrot Set");
        setBounds(0, 0, 1920, 1280);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        // Defining pretty colours table
        int[] colors = new int[MAX_ITER];
        for (int i = 0; i < MAX_ITER; i++) {
            colors[i] = Color.HSBtoRGB(i / 256f, 15, i / (i + 8f));
        }

        // Initialization of result structure and executor
        ExecutorService executor = Executors.newFixedThreadPool(20);
        java.util.List<Future<MandelbrotTuple>> resultList = new ArrayList<>(getWidth() * getHeight());


        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {

                final int finalX = x;
                final int finalY = y;
                resultList.add(executor.submit(() -> this.checkIfBelongs(finalY, finalX)));
            }
        }
        resultList.forEach(futureTuple -> {
            try {
                MandelbrotTuple tuple = futureTuple.get();
                I.setRGB(tuple.getX(), tuple.getY(), colors[tuple.getIter()]);
            } catch (Exception e) {
            }
        });

    }

    private MandelbrotTuple checkIfBelongs(int y, int x) {
        zx = zy = 0;
        cX = (x - 1200) / ZOOM;
        cY = (y - 550) / ZOOM;
        int iter = MAX_ITER;

        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return new MandelbrotTuple(x, y, iter);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

}