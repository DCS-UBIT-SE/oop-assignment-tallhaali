import java.util.Scanner;

public class recursive {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Fibonacci
        System.out.println("Calculating Fibonacci number.");
        System.out.print("Enter the Fibonacci sequence index (non-negative integer): ");
        int fibIndex = -1;
        if (scanner.hasNextInt()) {
            fibIndex = scanner.nextInt();
            if (fibIndex >= 0) {
            System.out.println("The Fibonacci number is: " + fibonacci(fibIndex));
            } else {
            System.out.println("Incorrect index! Must be non-negative.");
            }
        } else {
            System.out.println("Invalid input! Please enter an integer.");
            scanner.next(); // clear invalid input
        }

        // GCD
        System.out.println("\nCalculating GCD of two numbers.");
        System.out.print("Enter first integer: ");
        int a = 0, b = 0;
        boolean validGCDInput = true;
        if (scanner.hasNextInt()) {
            a = scanner.nextInt();
        } else {
            System.out.println("Invalid input! Please enter an integer.");
            scanner.next();
            validGCDInput = false;
        }
        System.out.print("Enter second integer: ");
        if (scanner.hasNextInt()) {
            b = scanner.nextInt();
        } else {
            System.out.println("Invalid input! Please enter an integer.");
            scanner.next();
            validGCDInput = false;
        }
        if (validGCDInput) {
            System.out.println("The GCD is: " + GCD(a, b));
        }

        // Max value in array
        System.out.println("\nFinding max value in array: {5,6,2,5,1,3,34,98,1,2,65}");
        int[] arr = {5,6,2,5,1,3,34,98,1,2,65};
        System.out.println("The max value is: " + maxVal(arr, 0, 0));

    }

    public static int fibonacci (int n) {
        if (n <= 1) {
            return 1;
        }

        return fibonacci(n-1) + fibonacci(n-2);
    }

    public static int GCD (int n, int m) {
        if (m == 0) {
            return n;
        }

        return GCD(m, n%m);
    }

    public static int maxVal (int[] arr, int m, int i) {
        int max;
        if (i == arr.length) {
            return m;
        }

        if (arr[i] > m) {
            max = arr[i];
        } 
        else {
            max = m;
        }

        return maxVal(arr, max, (i+1));
    }
}
