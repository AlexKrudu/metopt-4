package api;

import formats.Scalar;

public interface Matrix {

    int getWidth();

    int getHeight();

    double get(int i, int j);

    void set(int i, int j, double v);

    void swap(int i, int j);

    Scalar multiply(Scalar other);
}
