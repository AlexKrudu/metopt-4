package methods.oneDim;

import formats.Scalar;
import api.Optimizable;
import api.OptimizationResult;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class brentMethod {

    public boolean nEq(double lhs, double rhs, double tol) {
        return Math.abs(rhs - lhs) > tol;
    }

    public OptimizationResult optimize(Optimizable targetFunc, double leftBound, double rightBound, double tolerance) {
        double K = (3 - sqrt(5)) / 2;
        double x, w, v, f_x, f_w, f_v, d, e, u = 0, f_u, g;
        x = w = v = leftBound + K * (rightBound - leftBound);
        f_x = f_w = f_v = targetFunc.getValue(new Scalar(new double[]{x}, 1));
        d = e = rightBound - leftBound;
        int count = 1;
        while (Math.abs(x - (leftBound + rightBound) * 0.5) + (rightBound - leftBound) * 0.5 >= 2 * tolerance) {
            g = e;
            e = d;
            boolean acc = false;
            if (nEq(x, w, tolerance) && nEq(x, v, tolerance) && nEq(v, w, tolerance) && nEq(f_x, f_w, tolerance) &&
                    nEq(f_x, f_v, tolerance) &&
                    nEq(f_w, f_v, tolerance)) { // check if x, w, v and f_x, f_w, f_v are different
                u = w - (((f_w - f_v) * pow((w - x), 2)) - ((f_w - f_x) * pow((w - v), 2))) /
                        (2 * (((w - x) * (f_w - f_v)) - ((w - v) * (f_w - f_x))));
                if (u >= leftBound + tolerance && u <= rightBound - tolerance && Math.abs(u - x) < g / 2) {
                    acc = true;
                }
            }
            if (!acc) {
                if (x < leftBound + (rightBound - leftBound) / 2) {
                    u = x + K * (rightBound - x);
                    e = rightBound - x;
                } else {
                    u = x - K * (x - leftBound);
                    e = x - leftBound;
                }
            }
            f_u = targetFunc.getValue(new Scalar(new double[]{u}, 1));
            d = Math.abs(u - x);
            if (f_u <= f_x) {
                if (u >= x) {
                    leftBound = x;
                } else {
                    rightBound = x;
                }
                v = w;
                w = x;
                x = u;
                f_v = f_w;
                f_w = f_x;
                f_x = f_u;
            } else {
                if (u >= x) {
                    rightBound = u;
                } else {
                    leftBound = u;
                }
                if (f_u <= f_w || w == x) {
                    v = w;
                    w = u;
                    f_v = f_w;
                    f_w = f_u;
                } else if (f_u <= f_v || v == x || v == w) {
                    v = u;
                    f_v = f_u;
                }
            }
            count++;
        }
        return new OptimizationResult(new Scalar(new double[]{x}, 1), f_x, count);
    }
}
