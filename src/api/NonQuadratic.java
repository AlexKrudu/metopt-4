package api;

import api.Matrix;
import api.Optimizable;
import formats.Scalar;

import java.util.function.Function;

public class NonQuadratic implements Optimizable {
    Function<Scalar, Double> compFunc;
    Function<Scalar, Scalar> gradFunc;
    Function<Scalar, Matrix> hessFunc;


    public NonQuadratic(Function<Scalar, Double> compFunc, Function<Scalar, Scalar> gradFunc, Function<Scalar, Matrix> hessFunc) {
        this.compFunc = compFunc;
        this.gradFunc = gradFunc;
        this.hessFunc = hessFunc;
    }

    @Override
    public double getValue(final Scalar x) {
        return compFunc.apply(x);
    }

    @Override
    public Scalar getGradient(final Scalar x) {
        return gradFunc.apply(x);
    }

    @Override
    public Matrix getHessian(final Scalar x) {
        return hessFunc.apply(x);
    }
}
