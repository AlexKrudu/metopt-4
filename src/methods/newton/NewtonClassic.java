package methods.newton;

import api.Matrix;
import api.Method;
import api.Optimizable;
import api.OptimizationResult;
import formats.Scalar;
import methods.LUMethod;

public class NewtonClassic {

    public OptimizationResult optimize(Scalar xStart, double tolerance, Optimizable targetFunc) {
        Scalar curPoint = xStart;
        int iterCount = 0;
        Scalar p;
        do {
            System.out.println(iterCount + " & " + "(" + curPoint + ") & " + "-" + " {\\scriptsize $^{\\rm}$} \\\\");
            Scalar grad = targetFunc.getGradient(curPoint);
            grad = grad.multiply(-1.0);
            Matrix hessian = targetFunc.getHessian(curPoint);
            Method LU = new LUMethod();
            p = new Scalar(LU.solve(hessian, grad.getFullData()).getSolution(), xStart.getDim());
            if (p.getNorm() < tolerance) {
                break;
            }
            curPoint = curPoint.add(p);
            iterCount++;
        } while (true);
        return new OptimizationResult(curPoint, targetFunc.getValue(curPoint), iterCount);

    }

}
