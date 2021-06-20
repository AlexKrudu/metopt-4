package api;

import api.Matrix;
import api.Optimizable;
import formats.PlainMatrix;
import formats.Scalar;

public class SquareForm implements Optimizable {
    Matrix A;
    Scalar b;
    double c;

    public SquareForm(Matrix a, Scalar b, double c) {
        this.A = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double getValue(Scalar x) {
        return A.multiply(x).multiply(x) * 0.5 + b.multiply(x) + c; // wrong order probably, need to check
    }

    @Override
    public Scalar getGradient(final Scalar x) {
        return A.multiply(x).add(b);
    }

    @Override
    public Matrix getHessian(final Scalar x) {
        double[][] tmp = new double[A.getHeight()][A.getWidth()];
        for (int i = 0; i < A.getHeight(); i++) {
            for (int j = 0; j < A.getWidth(); j++) {
                tmp[i][j] = A.get(i, j);
            }
        }
        return new PlainMatrix(tmp);
    }
}
