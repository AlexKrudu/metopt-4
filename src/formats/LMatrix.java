package formats;

import api.Matrix;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LMatrix implements Matrix {

    private final Matrix source;

    public LMatrix(final Matrix source) {
        this.source = source;
    }

    public void swap(int i, int j) {
        source.swap(i, j);
    }

    @Override
    public Scalar multiply(final Scalar other) {
        return null;
    }

    @Override
    public int getWidth() {
        return source.getWidth();
    }

    @Override
    public int getHeight() {
        return source.getHeight();
    }

    @Override
    public double get(final int i, final int j) {
        if (i == j) {
            return 1;
        }
        if (i < j) {
            return 0;
        }
        return source.get(i, j);
    }

    @Override
    public void set(final int i, final int j, final double v) {
        source.set(i, j, v);
    }

    @Override
    public String toString() {
        return IntStream.range(0, getHeight())
                .mapToObj(i -> IntStream.range(0, getWidth()).mapToDouble(j -> get(i, j)).mapToObj(String::valueOf).collect(Collectors.joining(" ")))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
