import api.NonQuadratic;
import api.OptimizationResult;
import formats.PlainMatrix;
import formats.Scalar;
import methods.newton.*;

import static java.lang.Math.pow;

public class NewtonTest {

    public static void main(String[] args) {
        NonQuadratic f1 = new NonQuadratic((v) -> {
            double x = v.get(0);
            double y = v.get(1);
            return 4 + pow(7 * x * x + 4 * y * y, 2.0 / 3.0);
        }, (v) -> {
            double x = v.get(0);
            double y = v.get(1);
            return new Scalar(new double[]{(28 * x) / (3 * pow(7 * x * x + 4 * y * y, 1.0 / 3.0)),
                    (16 * y) / (3 * pow(7 * x * x + 4 * y * y, 1.0 / 3.0))}, 2);
        }, (v) -> {
            double x = v.get(0);
            double y = v.get(1);
            double[][] data = new double[2][2];
            data[0][0] = (28 * (7 * x * x + 12 * y * y)) / (9 * pow((pow(7 * x * x + 4 * y * y, 2)), 2.0 / 3.0));
            data[0][1] = (-224 * x * y) / (9 * pow((pow(7 * x * x + 4 * y * y, 2)), 2.0 / 3.0));
            data[1][0] = data[0][1];
            data[1][1] = (16 * (21 * x * x + 4 * y * y)) / (9 * pow((pow(7 * x * x + 4 * y * y, 2)), 2.0 / 3.0));
            return new PlainMatrix(data);
        });

        NonQuadratic f2 = new NonQuadratic((v) -> {
            double x = v.get(0);
            double y = v.get(1);
            return x * x + y * y - 1.2 * x * y;
        }, (v) -> {
            double x = v.get(0);
            double y = v.get(1);
            return new Scalar(new double[]{2 * x - 1.2 * y, 2 * y - 1.2 * x}, 2);
        }, (v) -> {
            double x = v.get(0);
            double y = v.get(1);
            double[][] data = new double[2][2];
            data[0][0] = 2;
            data[0][1] = -1.2;
            data[1][0] = data[0][1];
            data[1][1] = 2;
            return new PlainMatrix(data);
        });

        NonQuadratic f3 = new NonQuadratic((v) -> {
            double x1 = v.get(0);
            double x2 = v.get(1);
            return 100 * pow((x2 - x1 * x1), 2) + pow((1 - x1), 2);
        }, (v) -> {
            double x = v.get(0);
            double y = v.get(1);
            return new Scalar(new double[]{2 * (200 * pow(x, 3) - 200 * x * y + x - 1), 200 * (y - pow(x, 2))}, 2);
        }, (v) -> {
            double x = v.get(0);
            double y = v.get(1);
            double[][] data = new double[2][2];
            data[0][0] = 1200 * pow(x, 2) - 400 * y + 2;
            data[0][1] = -400 * x;
            data[1][0] = data[0][1];
            data[1][1] = 200;
            return new PlainMatrix(data);
        });
        //NewtonDirected newton = new NewtonDirected();
        //NewtonOneD newton = new NewtonOneD();
        NewtonClassic newton = new NewtonClassic();
        // QuasiNewtonDFP newton = new QuasiNewtonDFP();
        //QuasiNewtonPowell newton = new QuasiNewtonPowell();


        OptimizationResult res = newton.optimize(new Scalar(new double[]{10, 10}, 2), 1e-8, f3);
        System.out.println("Final point: " + res.getPoint() + ". Func value: " + res.getfMin());
    }
}
