package bill5220.bilimsel_hesaplama.linear_equations.algorithms;


import java.util.Arrays;

public class Gauss_Seidel {

    public static final int MAX_ITERATIONS = 300;

    private double[][] M;

    public Gauss_Seidel(double[][] matrix) {
        M = matrix;
    }


    public boolean transformToDominant(int r, boolean[] V, int[] R)

    {

        int n = M.length;

        if (r == M.length)

        {

            double[][] T = new double[n][n + 1];

            for (int i = 0; i < R.length; i++)

            {

                for (int j = 0; j < n + 1; j++)

                    T[i][j] = M[R[i]][j];

            }


            M = T;


            return true;

        }


        for (int i = 0; i < n; i++)

        {

            if (V[i]) continue;


            double sum = 0;


            for (int j = 0; j < n; j++)

                sum += Math.abs(M[i][j]);


            if (2 * Math.abs(M[i][r]) > sum)

            { // diagonally dominant?

                V[i] = true;

                R[r] = i;


                if (transformToDominant(r + 1, V, R))

                    return true;


                V[i] = false;

            }

        }


        return false;

    }


    public boolean makeDominant()

    {

        boolean[] visited = new boolean[M.length];

        int[] rows = new int[M.length];


        Arrays.fill(visited, false);


        return transformToDominant(0, visited, rows);

    }


    public double[] solve()

    {

        int iterations = 0;

        int n = M.length;

        double epsilon = 1e-15;

        double[] X = new double[n]; // Approximations

        double[] P = new double[n]; // Prev

        Arrays.fill(X, 0);


        while (true)

        {

            for (int i = 0; i < n; i++)

            {

                double sum = M[i][n]; // b_n


                for (int j = 0; j < n; j++)

                    if (j != i)

                        sum -= M[i][j] * X[j];


                // Update x_i to use in the next row calculation

                X[i] = 1 / M[i][i] * sum;

            }


            System.out.print("X_" + iterations + " = {");

            for (int i = 0; i < n; i++)

                System.out.print(X[i] + " ");

            System.out.println("}");


            iterations++;

            if (iterations == 1)

                continue;


            boolean stop = true;

            for (int i = 0; i < n && stop; i++)

                if (Math.abs(X[i] - P[i]) > epsilon)

                    stop = false;


            if (stop || iterations == MAX_ITERATIONS) {
                return X;
            }

            P = (double[]) X.clone();

        }

    }


}
