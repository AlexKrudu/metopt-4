package methods;

import api.Matrix;
import api.Method;
import api.MatrixResult;

import java.util.ArrayList;
import java.util.Collections;

public class Gauss implements Method {

    @Override
    public strictfp MatrixResult solve(final Matrix A, final double[] b) {
        double curP = 1e-30, coef = 0;
        double[] results = new double[Math.max(A.getHeight(), A.getWidth())];
        ArrayList<Double> params = new ArrayList<Double>(Collections.nCopies(A.getHeight(), 0.0));
        int maxIndex = 0;
        boolean nonZero = false;
        int count = 0;
        for (int i = 0; i < A.getHeight(); i++) {
            for (int j = i; j < A.getWidth(); j++) {
                if (Math.abs(A.get(j, i)) > Math.abs(curP)) {
                    nonZero = true;
                    curP = A.get(j, i);
                    maxIndex = j;
                }
            }
            if (!nonZero) {
                params.set(i, 1.0);
                count++;
                continue;
            }
            if (maxIndex != i) {
                A.swap(i, maxIndex);
                double tmp = b[i];
                b[i] = b[maxIndex];
                b[maxIndex] = tmp;
                count++;
            }
            for (int j = i + 1; j < A.getWidth(); j++) {
                coef = A.get(j, i) / A.get(i, i);
                count++;
                for (int k = 0; k < A.getWidth() + 1; k++) {
                    if (k == A.getWidth()) {
                        b[j] = b[j] - coef * b[i];
                        count++;
                        continue;
                    }
                    A.set(j, k, A.get(j, k) - coef * A.get(i, k));
                    count++;
                }
            }
            nonZero = false;
            curP = 1e-11;
        }
        for (int i = A.getWidth() - 1; i >= 0; i--) {
            if (params.get(i) != 1) {
                results[i] = b[i] / A.get(i, i);
                count++;
                for (int j = A.getHeight() - 1; j > i; j--) {
                    results[i] = results[i] - ((A.get(i, j) * results[j]) / A.get(i, i));
                    count++;
                }
            } else {
                results[i] = 0;
            }
        }
        return new MatrixResult(results, count);
    }
}
