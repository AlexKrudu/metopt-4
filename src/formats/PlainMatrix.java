package formats;

import api.Matrix;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PlainMatrix implements Matrix {

    private final double[][] data;

    public PlainMatrix(int i, int j) {
        this(new double[i][j]);
    }

    public PlainMatrix(double[][] data) {
        this.data = data;
    }

    public double[][] getData() {
        return data;
    }

    public void swap(int i, int j) {
        List<double[]> list = Arrays.asList(data);
        Collections.swap(list, i, j);
        list.toArray(data);
    }

    @Override
    public Scalar multiply(final Scalar other) {
        assert (this.getWidth() == other.dim);
        double[] res = new double[getHeight()];
        for (int i = 0; i < getHeight(); i++) {
            double tmp = 0;
            for (int j = 0; j < getWidth(); j++) {
                tmp += get(i, j) * other.get(j);
            }
            res[i] = tmp;
        }
        return new Scalar(res, getHeight());
    }

    @Override
    public int getWidth() {
        return data[0].length;
    }

    @Override
    public int getHeight() {
        return data.length;
    }

    @Override
    public double get(final int i, final int j) {
        return data[i][j];
    }

    @Override
    public void set(final int i, final int j, final double v) {
        data[i][j] = v;
    }

    public PlainMatrix add(Matrix other) {
        assert (this.getWidth() == other.getWidth() && this.getHeight() == other.getHeight());
        double[][] data = new double[this.getHeight()][this.getWidth()];
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                data[i][j] = this.get(i, j) + other.get(i, j);
            }
        }
        return new PlainMatrix(data);
    }

    public PlainMatrix multiply(double coef) {
        double[][] data = new double[this.getHeight()][this.getWidth()];
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                data[i][j] = this.get(i, j) * coef;
            }
        }
        return new PlainMatrix(data);
    }

    @Override
    public String toString() {
        return Arrays.stream(data).map(m -> Arrays.stream(m).mapToObj(String::valueOf).collect(Collectors.joining("\t"))).collect(Collectors.joining(System.lineSeparator()));
    }
}
