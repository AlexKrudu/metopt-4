package formats;

import api.Matrix;

public class ProfileMatrix implements Matrix {
    int n;
    double[] di;
    int[] ia;
    double[] al;
    double[] au;

    @Override
    public int getWidth() {
        return n;
    }

    public ProfileMatrix(double[][] matrix) {
        n = matrix.length;
        di = new double[n];
        ia = new int[n + 1];
        int last = 0;
        int last2 = 0;
        ia[0] = 1;
        ia[1] = 1;
        for (int i = 0; i < n; i++) {
            di[i] = matrix[i][i];
            if (i >= 1) {
                int notZero = i;
                for (int j = 0; j < i; j++) {
                    if (matrix[i][j] != 0) {
                        notZero = j;
                        break;
                    }
                }
                ia[i + 1] = ia[i] + i - notZero;
            }
        }
        al = new double[ia[n] - 1];
        au = new double[ia[n] - 1];
        for (int i = 0; i < n; i++) {
            di[i] = matrix[i][i];
            if (i >= 1) {
//                int notZero1 = 0, notZero2 = 0;
                for (int j = 0; j < i; j++) {
                    if (matrix[i][j] != 0) {
//                        notZero1 = j;
                        for (int k = j; k < i; k++) {
                            al[last++] = matrix[i][k];
                            au[last2++] = matrix[k][i];
                        }
                        break;
                    }
                }

//                for (int j = 0; j < i; j++) {
//                    if (matrix[j][i] != 0) {
////                        notZero2 = j;
//                        for (int k = j; k < i; k++) {
//                            au[last2++] = matrix[k][i];
//                        }
//                        break;
//                    }
//                }
            }
        }
    }

    @Override
    public int getHeight() {
        return n;
    }

    @Override
    public double get(int i, int j) {
        //from 0 to n-1
        if (j < i) {
            int firstNum = ia[i] + i - ia[i + 1];
            if (firstNum <= j) {
                return al[ia[i] - 1 + j - firstNum];
            } else {
                return 0;
            }
        } else if (j > i) {
            int firstNum = ia[j] + j - ia[j + 1];
            if (firstNum <= i) {
                return au[ia[j] - 1 + i - firstNum];
            } else {
                return 0;
            }
        }
        return di[i];
    }

    @Override
    public void set(int i, int j, double v) {
        //from 0 to n-1
        if (j < i) {
            int firstNum = ia[i] + i - ia[i + 1];
            if (firstNum <= j) {
                al[ia[i] - 1 + j - firstNum] = v;
            } else {
                //System.err.println("Out of bounds");
            }
        } else if (j > i) {
            int firstNum = ia[j] + j - ia[j + 1];
            if (firstNum <= i) {
                au[ia[j] - 1 + i - firstNum] = v;
            } else {
                //System.err.println("Out of bounds");
            }
        } else {
            di[i] = v;
        }
    }

    @Override
    public void swap(final int i, final int j) {
        double[] tmp = new double[this.getWidth()];
        for (int k = 0; k < this.getWidth(); k++) {
            tmp[k] = this.get(i, k);
        }
        for (int k = 0; k < this.getWidth(); k++) {
            this.set(i, k, this.get(j, k));
        }
        for (int k = 0; k < this.getWidth(); k++) {
            this.set(j, k, tmp[k]);
        }
    }

    @Override
    public Scalar multiply(final Scalar other) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                sb.append(get(i, j)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
