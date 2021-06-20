package formats;

import api.Matrix;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UMatrix implements Matrix {

    private final Matrix source;

    public UMatrix(final Matrix source) {
        this.source = source;
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
        if (i > 0 && i > j) {
            return 0;
        }
        return source.get(i, j);
    }

    @Override
    public void set(final int i, final int j, final double v) {
        source.set(i, j, v);
    }

    public void swap(int i, int j) {
        source.swap(i, j);
    }

    @Override
    public Scalar multiply(final Scalar other) {
        return null;
    }

    @Override
    public String toString() {
        return IntStream.range(0, getHeight())
                .mapToObj(i -> IntStream.range(0, getWidth()).mapToDouble(j -> get(i, j)).mapToObj(String::valueOf).collect(Collectors.joining(" ")))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
