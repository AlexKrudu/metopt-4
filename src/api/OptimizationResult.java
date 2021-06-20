package api;

import formats.Scalar;

public class OptimizationResult {
    Scalar xMin;
    double fMin;
    int iterationsN;

    public OptimizationResult(Scalar xMin, double fMin, int n) {
        this.xMin = xMin;
        this.fMin = fMin;
        this.iterationsN = n;
    }

    public Scalar getPoint() {
        return this.xMin;
    }

    public double getfMin() {
        return this.fMin;
    }
}
