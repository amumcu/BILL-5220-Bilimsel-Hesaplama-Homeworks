package bill5220.bilimsel_hesaplama.linear_equations;

import bill5220.bilimsel_hesaplama.linear_equations.algorithms.Gauss_Elimination;

import java.util.Scanner;

public class Foo {

    /** Main function **/

    public static void main (String[] args)

    {

        Scanner scan = new Scanner(System.in);

        System.out.println("Gaussian Elimination Algorithm Test\n");

        /** Make an object of GaussianElimination class **/

        Gauss_Elimination ge = new Gauss_Elimination();



        System.out.println("\nEnter number of variables");

        int N = scan.nextInt();



        double[] B = new double[N];

        double[][] A = new double[N][N];



        System.out.println("\nEnter "+ N +" equations coefficients ");

        for (int i = 0; i < N; i++)

            for (int j = 0; j < N; j++)

                A[i][j] = scan.nextDouble();



        System.out.println("\nEnter "+ N +" solutions");

        for (int i = 0; i < N; i++)

            B[i] = scan.nextDouble();



        ge.solve(A,B);

    }
}
