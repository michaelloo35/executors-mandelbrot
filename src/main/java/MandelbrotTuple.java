public class MandelbrotTuple {

    private final int x;
    private final int y;
    private final int iter;

    public MandelbrotTuple(int x, int y, int iter) {
        this.x = x;
        this.y = y;
        this.iter = iter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIter() {
        return iter;
    }
}
