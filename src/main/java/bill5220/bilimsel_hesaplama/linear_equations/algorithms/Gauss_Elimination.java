package bill5220.bilimsel_hesaplama.linear_equations.algorithms;

public class Gauss_Elimination {
    public double[] solve(double[][] A, double[] B) {

        int N = B.length;

        for (int k = 0; k < N; k++){

            /** find pivot row */

            int max = k;

            for (int i = k + 1; i < N; i++)

                if (Math.abs(A[i][k]) > Math.abs(A[max][k]))

                    max = i;



            double[] temp = A[k];

            A[k] = A[max];

            A[max] = temp;



            double t = B[k];

            B[k] = B[max];

            B[max] = t;



            for (int i = k + 1; i < N; i++){

                double factor = A[i][k] / A[k][k];

                B[i] -= factor * B[k];

                for (int j = k; j < N; j++)

                    A[i][j] -= factor * A[k][j];

            }

        }



        printRowEchelonForm(A, B);


        /** back substitution **/

        double[] solution = new double[N];

        for (int i = N - 1; i >= 0; i--){

            double sum = 0.0;

            for (int j = i + 1; j < N; j++)

                sum += A[i][j] * solution[j];

            solution[i] = (B[i] - sum) / A[i][i];

        }

        return solution;

    }

    /**
     *  row    echleon form
     **/

    public void printRowEchelonForm(double[][] A, double[] B){

        int N = B.length;

        System.out.println("\nRow Echelon form : ");

        for (int i = 0; i < N; i++){

            for (int j = 0; j < N; j++)

                System.out.printf("%.3f ", A[i][j]);

            System.out.printf("| %.3f\n", B[i]);

        }

        System.out.println();

    }


}
