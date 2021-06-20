package methods.oneDim;

import formats.Scalar;
import api.Optimizable;
import api.OptimizationResult;

public class Dichotomy {

    public OptimizationResult optimize(Optimizable targetFunc, double leftBound, double rightBound, double tolerance) {
        double x1, x2;
        double delta = tolerance / 5;
        int count = 0;
        while ((rightBound - leftBound) / 2 > tolerance) {
            x1 = (leftBound + rightBound - delta) / 2;
            x2 = (leftBound + rightBound + delta) / 2;
            count += 2; // т.к целевая функция на каждой итерации вычисляется 2 раза
            var f1 = targetFunc.getValue(new Scalar(new double[]{x1}, 1));
            var f2 = targetFunc.getValue(new Scalar(new double[]{x2}, 1));
            if (f1 <= f2) {
                rightBound = x2;
            } else {
                leftBound = x1;
            }
        }
        x1 = (leftBound + rightBound - tolerance) / 2;
        x2 = (leftBound + rightBound + tolerance) / 2;
        double f_x1 = targetFunc.getValue(new Scalar(new double[]{x1}, 1));
        double f_x2 =targetFunc.getValue(new Scalar(new double[]{x2}, 1));
        if (f_x1 <= f_x2) {
            return new OptimizationResult(new Scalar(new double[]{x1}, 1), f_x1, count);
        } else {
            return new OptimizationResult(new Scalar(new double[]{x2}, 1), f_x2, count);
        }
    }
}
