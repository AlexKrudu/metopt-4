package api;

import java.util.Arrays;

public class MatrixResult {

    public final double[] solution;
    public final int actions;

    public MatrixResult(double[] solution, int actions) {
        this.solution = solution;
        this.actions = actions;
    }

    public double getFault() {
        double sum = 0;
        for (int i = 0; i < solution.length; i++) {
            sum += Math.pow(solution[i] - i - 1, 2);
        }
        return Math.sqrt(sum);
    }

    public double getSolutionNorm() {
        return Math.sqrt(Arrays.stream(solution).map(x -> x * x).reduce(Double::sum).orElse(0));
    }

    public double[] getSolution() {
        return this.solution;
    }
}
