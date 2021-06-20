package api;

import api.Matrix;
import formats.Scalar;

import java.util.function.Function;

public interface Optimizable {

    double getValue(Scalar x);

    Scalar getGradient(Scalar x);

    Matrix getHessian(Scalar x);

}
