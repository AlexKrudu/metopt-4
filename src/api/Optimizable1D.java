package api;

import formats.Scalar;

import java.util.function.Function;

public class Optimizable1D implements Optimizable {

    Function<Double, Double> targetFunc;

    public Optimizable1D(Function<Double, Double> targetFunc) {
        this.targetFunc = targetFunc;
    }

    @Override
    public double getValue(final Scalar x) {
        return targetFunc.apply(x.get(0));
    }

    @Override
    public Scalar getGradient(final Scalar x) {
        return null;
    }

    @Override
    public Matrix getHessian(final Scalar x) {
        return null;
    }
}
