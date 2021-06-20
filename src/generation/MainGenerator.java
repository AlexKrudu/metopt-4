package generation;

import api.Generator;
import formats.PlainMatrix;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MainGenerator implements Generator {
    @Override
    public void generate(int n, int k) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of("matrix.out"), StandardCharsets.UTF_8)) {
//            int n = 6;
            writer.write(String.valueOf(n));
            writer.newLine();
            double[][] matrix = new double[n][n];
            // k = 0, 1, 2,...

            int[] ia = new int[n + 1];
            ia[0] = 1;
            ia[1] = 1;
            for (int i = 2; i < n+1; i++) {
                ia[i] = ia[i-1] + ThreadLocalRandom.current().nextInt(0, i+1);
            }
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (i-(ia[i+1]-ia[i]) < j) {
                        matrix[i][j] = ThreadLocalRandom.current().nextInt(-4, 1);
//                        matrix[j][i] = matrix[i][j];
                        matrix[j][i] = ThreadLocalRandom.current().nextInt(-4, 1);
                    } else if (i-(ia[i+1]-ia[i]) == j) {
                        matrix[i][j] = ThreadLocalRandom.current().nextInt(-4, 0);
//                        matrix[j][i] = matrix[i][j];
                        matrix[j][i] = ThreadLocalRandom.current().nextInt(-4, 0);
                    }  else {
                        matrix[i][j] = 0;
                        matrix[j][i] = 0;
                    }
                }
            }

//            for (int i = 0; i < n; i++) {
//                for (int j = 0; j < n; j++) {
//                    if (i != j) {
//                        matrix[i][j] = ThreadLocalRandom.current().nextInt(-4, 1);
//                    }
//                }
//            }
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    double aii = 0;
                    for (int z = 1; z < n; z++) {
                        aii += matrix[i][z];
                    }
                    aii *= -1;
                    aii += Math.pow(10, -k);
                    matrix[i][i] = aii;
                } else {
                    double aii = 0;
                    for (int z = 0; z < n; z++) {
                        if (i != z) {
                            aii += matrix[i][z];
                        }
                    }
                    aii *= -1;
                    matrix[i][i] = aii;
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    writer.write(matrix[i][j] + " ");
                }
                writer.newLine();
            }

            writer.write(Arrays.stream(getB(new PlainMatrix(matrix))).mapToObj(String::valueOf).collect(Collectors.joining(" ")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
