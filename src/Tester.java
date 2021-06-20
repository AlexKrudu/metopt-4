import api.Matrix;
import api.Method;
import api.MatrixResult;
import formats.PlainMatrix;
import formats.ProfileMatrix;
import api.Generator;
import generation.GilbertGenerator;
import generation.MainGenerator;
import methods.Gauss;
import methods.LUMethod;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Tester {

    private static PlainMatrix loadMatrix(BufferedReader reader) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        PlainMatrix read = new PlainMatrix(n, n);
        for (int i = 0; i < n; i++) {
            String[] input = reader.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                read.set(i, j, Double.parseDouble(input[j]));
            }
        }
        return read;
    }

    private static void check(int test, int n, String method, String gen, MatrixResult solution) {
        double fault = solution.getFault();
        double normed = fault / solution.getSolutionNorm();
        if (fault < 1) {
            System.out.println("$" + n + "$" + " & " + "$" + test + "$" + " & " + "$" + fault + "$" + " & " + "$" + normed + "$" + " {\\scriptsize $^{\\rm a}$} \\\\");
            //System.out.println(fault + "         | " + normed + " | " + solution.actions + " | Passed: test " + test + ", solved with method " + method + " on matrix, generated by " + gen);
        } else {
            System.out.println("$" + n + "$" + " & " + "$" + test + "$" + " & " + "$" + fault + "$" + " & " + "$" + normed + "$" + " {\\scriptsize $^{\\rm a}$} \\\\");
            //throw new AssertionError(fault + "         | " + normed + " | " + solution.actions + " | Failed: test " + test + ", solved with method " + method + " on matrix, generated by " + gen);
        }
    }

    public static void runTest(int i, int n, Generator generator, Method method, boolean profile) {
        generator.generate(n, i);
        try (BufferedReader reader = Files.newBufferedReader(Path.of("matrix.out"))) {
            Matrix A = loadMatrix(reader);
            if (profile) {
                A = new ProfileMatrix(((PlainMatrix) A).getData());
            }
            double[] b = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
            MatrixResult solution = method.solve(A, b);
            check(i, n, method.getClass().getSimpleName(), generator.getClass().getSimpleName(), solution);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Generator main = new MainGenerator();
        Generator gilbert = new GilbertGenerator();
        Method lu = new LUMethod();
        Method gauss = new Gauss();

        final int TESTS = 10;
        int counter = 0;
        int ng = ThreadLocalRandom.current().nextInt(3, 9);
        for (int n = 2; n < 15; n += 1) {
    //        System.out.println("Running tests for n = " + n + ", ng = " + ng);
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("tests.log")))) {
                for (int i = 0; i < TESTS; i+=2) {
                    try {
                        try {
                            runTest(i, n, gilbert, lu, false);
                           /* try {
                                runTest(i, n, main, lu, true);
                            } catch (IllegalStateException e) {
                                throw new AssertionError("Test " + i + ": error while evaluating LU method on common-generated matrix: " + e.getMessage());
                            } */
                        } catch (AssertionError e) {
                            writer.write(e.getMessage());
                            writer.newLine();
                            System.out.println(e.getMessage());
                        }
                   /* try {
                        runTest(i, ng, gilbert, gauss, false);
                        try {
                            runTest(i, ng, gilbert, lu, true);
                        } catch (IllegalStateException e) {
                            throw new AssertionError("Test " + i + ": error while evaluating LU method on Gilbert matrix: " + e.getMessage());
                        }
                    } catch (AssertionError e) {
                        writer.write(e.getMessage());
                        writer.newLine();
                        System.out.println(e.getMessage());
                    } */
                        counter++;
                        //System.out.println("Test " + i + " passed");
                    } catch (RuntimeException e) {
                        writer.write("Fatal error on test " + i + ":");
                        writer.write(e.toString());
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        System.out.println("--------------------------------------");
//        System.out.println("Tests: " + counter + " passed, " + (TESTS - counter) + " failed");
    }
}
