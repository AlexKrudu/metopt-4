package methods.newton;

import api.Optimizable;
import api.OptimizationResult;
import formats.PlainMatrix;
import formats.Scalar;
import methods.oneDim.Dichotomy;
import api.Optimizable1D;
import methods.oneDim.brentMethod;

public class QuasiNewtonPowell {
    public OptimizationResult optimize(Scalar xStart, double tolerance, Optimizable targetFunc) {
        int iterCount = 0;
        Scalar xPrev = xStart;
        brentMethod brentMethod = new brentMethod();
        double[][] tmp = new double[xStart.getDim()][xStart.getDim()];
        for (int i = 0; i < xStart.getDim(); i++) {
            tmp[i][i] = 1;
        }
        PlainMatrix G = new PlainMatrix(tmp);
        Scalar prevW = targetFunc.getGradient(xPrev).multiply(-1);
        Scalar prevP = new Scalar(prevW.getFullData(), prevW.getDim());
        final Scalar finalCurPoint = xPrev;
        final Scalar finalP = prevP;
        double alpha = brentMethod.optimize(new Optimizable1D(s ->
                        targetFunc.getValue(finalCurPoint.add(finalP.multiply(s)))),
                0, 10, tolerance).getPoint().get(0);
        System.out.println(iterCount + " & " + "(" + xStart + ") & " + "-" + " {\\scriptsize $^{\\rm}$} \\\\");

        Scalar xNext = xPrev.add(prevP.multiply(alpha));
        Scalar deltaX = xNext.add(xPrev.multiply(-1));
        iterCount++;
        while (true) {
            System.out.println(iterCount + " & " + "(" + xNext + ") & " + (iterCount > 0 ? alpha : "-") + " {\\scriptsize $^{\\rm}$} \\\\");
            Scalar wNext = targetFunc.getGradient(xNext).multiply(-1);
            Scalar deltaW = wNext.add(prevW.multiply(-1));
            Scalar xWaved = deltaX.add(G.multiply(deltaW));
            G = G.add(xWaved.multiplyMatrix(xWaved).multiply(-1 / (deltaW.multiply(xWaved))));
            prevP = G.multiply(wNext);
            final Scalar finalP1 = prevP;
            final Scalar finalXN = xNext;
            alpha = brentMethod.optimize(new Optimizable1D(s ->
                            targetFunc.getValue(finalXN.add(finalP1.multiply(s)))),
                    0, 10, tolerance).getPoint().get(0);
            xPrev = new Scalar(xNext.getFullData(), xNext.getDim());
            xNext = xNext.add(prevP.multiply(alpha));
            deltaX = xNext.add(xPrev.multiply(-1));
            prevW = wNext;
            if (deltaX.getNorm() < tolerance/1000) {
                break;
            }
            iterCount++;
        }

        return new OptimizationResult(xNext, targetFunc.getValue(xNext), iterCount);
    }

}
