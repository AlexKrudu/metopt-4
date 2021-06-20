package api;

import java.util.stream.IntStream;

public interface Generator {

    void generate(int n, int k);

    default double[] getB(Matrix m) {
        int n = m.getWidth();
        double[] x = IntStream.range(1, n + 1).mapToDouble(d -> (double)d).toArray();
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                b[i] += m.get(i, j) * x[j];
            }
        }
        return b;
    }
}
