package methods;

import api.Matrix;
import api.Method;
import api.MatrixResult;

public class LUMethod implements Method {
    @Override
    public strictfp MatrixResult solve(final Matrix A, final double[] b) {
        int n = A.getWidth();
        int count = LU(A);

        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            double v = 0;
            for (int m = 0; m < i; m++) {
                v += A.get(i, m) * y[m];
                count++;
            }
            y[i] = b[i] - v;
            count++;
        }

        double[] x = new double[n];
        for (int i = 1; i < n + 1; i++) {
            double v = 0;
            for (int m = n - i; m < n; m++) {
                v += A.get(n - i, m) * x[m];
                count++;
            }
            x[n - i] = (y[n - i] - v) / A.get(n - i, n - i);
            count++;
        }
        return new MatrixResult(x, count);
    }

    private strictfp int LU(Matrix a) {
        int count = 0;
        int n = a.getWidth();
        for (int k = 0; k < n; k++) {
            for (int j = k; j < n; j++) {
                double v = 0;
                for (int m = 0; m < k; m++) {
                    v += a.get(k, m) * a.get(m, j);
                    count++;
                }
                a.set(k, j, a.get(k, j) - v);
                count++;
            }
            for (int i = k + 1; i < n; i++) {
                if (a.get(k, k) != 0) {
                    double v = 0;
                    for (int m = 0; m < k; m++) {
                        v += a.get(i, m) * a.get(m, k);
                        count++;
                    }
                    a.set(i, k, (a.get(i, k) - v) / a.get(k, k));
                    count++;
                } else {
                    throw new IllegalStateException("not all major minors of a square matrix are nonzero");
                }
            }
        }
        return count;
    }
}
