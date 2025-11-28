import java.util.Scanner;

public class EquationSolver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("Please enter the lower limit: ");
            double a = scanner.nextDouble();
            System.out.print("Please enter the upper limit: ");
            double b = scanner.nextDouble();

            if (correctLimits(a, b)) {
                double solution = findSolution(a, b);
                System.out.println("The solution to the equation is: " + solution);
                break;

            } else {
                System.out.println("The solution doesn't lie between " + a + " and " + b);
            }
        }

        scanner.close();

    }

    // public static double f(double x) {
    //     return 2 * Math.pow(x, 3) - 250;
    // }

    public static double f(double x) {
        return 3 * Math.pow(x, 3) - 24;
    }

    public static boolean correctLimits(double a, double b) {
        return ((f(a) * f(b)) < 0);
    }

    public static double findSolution(double a, double b) {
        while (true) {
            double c = (a + b) / 2;
            if (f(c) > 0) {
                b = c;
            } else if (f(c) < 0) {
                a = c;
            } else {
                return c;
            }

            if ((Math.round(a) == Math.round(b)) && (f(Math.round(a)) == 0)) {
                return Math.round(a);
            }
        }
    }
}