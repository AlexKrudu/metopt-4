package methods.newton;

import api.*;
import formats.Scalar;
import methods.LUMethod;
import methods.oneDim.Dichotomy;
import methods.oneDim.brentMethod;

public class NewtonOneD {

    public OptimizationResult optimize(Scalar xStart, double tolerance, Optimizable targetFunc) {
        Scalar curPoint = xStart;
        int iterCount = 0;
        brentMethod brent = new brentMethod();
        Scalar p;
        double alpha = 0;
        do {
            System.out.println(iterCount + " & " + "(" + curPoint + ") & " + (iterCount > 0 ? alpha : "-") + " {\\scriptsize $^{\\rm}$} \\\\");

            //System.out.println("Iteration: " + iterCount + ". Current point: " + curPoint);
            Scalar grad = targetFunc.getGradient(curPoint);
            grad = grad.multiply(-1.0);
            Matrix hessian = targetFunc.getHessian(curPoint);
            Method LU = new LUMethod();
            p = new Scalar(LU.solve(hessian, grad.getFullData()).getSolution(), xStart.getDim());
            if (p.getNorm() < tolerance) {
                break;
            }
            final Scalar finalCurPoint = curPoint;
            final Scalar finalP = p;
            alpha = brent.optimize(new Optimizable1D(s ->
                            targetFunc.getValue(finalCurPoint.add(finalP.multiply(s)))),
                    0, 10, tolerance).getPoint().get(0);
          //  System.out.println("Alpha found: " + alpha);
            curPoint = curPoint.add(p.multiply(alpha));
            iterCount++;
        } while (true);
        return new OptimizationResult(curPoint, targetFunc.getValue(curPoint), iterCount);

    }
}
