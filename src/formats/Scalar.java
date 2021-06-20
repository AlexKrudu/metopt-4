package formats;

import api.Matrix;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Scalar {
    int dim;
    double[] data;

    public Scalar(double[] data, int dim) {
        this.data = data;
        this.dim = dim;
    }

    public double get(int ind) {
        assert (ind < dim);
        return data[ind];
    }

    void set(int ind, double value) {
        assert (ind < dim);
        data[ind] = value;
    }


    public double multiply(Scalar other) {
        assert (this.dim == other.dim);
        double res = 0;
        for (int i = 0; i < this.dim; i++) {
            res += this.get(i) * other.get(i);
        }
        return res;
    }

    public Scalar add(Scalar other) {
        assert (this.dim == other.dim);
        double[] tmp = new double[this.dim];
        for (int i = 0; i < this.dim; i++) {
            tmp[i] = this.get(i) + other.get(i);
        }
        return new Scalar(tmp, this.dim);
    }

    public Scalar multiply(double coef) {
        double[] tmp = new double[getDim()];
        for (int i = 0; i < dim; i++) {
            tmp[i] = data[i] * coef;
        }
        return new Scalar(tmp, getDim());
    }

    public PlainMatrix multiplyMatrix(Scalar other) {
        double[][] data = new double[this.dim][other.getDim()];
        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < other.getDim(); j++) {
                data[i][j] = this.get(i) * other.get(j);
            }
        }
        return new PlainMatrix(data);
    }

    public double getNorm() {
        double tmp = 0;
        for (int i = 0; i < dim; i++) {
            tmp += pow(this.get(i), 2);
        }
        return sqrt(tmp);
    }

    public double[] getFullData() {
        return this.data;
    }

    public int getDim() {
        return this.dim;
    }

    @Override
    public String toString() {
        return Arrays.stream(data).mapToObj(Double::toString).collect(Collectors.joining(" "));
    }
}
