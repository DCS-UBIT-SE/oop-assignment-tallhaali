import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HammingCode {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String bitString="";
        boolean inValid = true;

        while (inValid) {

            System.out.print("Enter the bit string: ");
            bitString = scanner.nextLine();
            for (int i = 0; i < bitString.length(); i++) {
                if (((bitString.charAt(i)) == '0') || ((bitString.charAt(i)) == '1')) {
                    inValid = false;
                } else {
                    System.out.println("Invalid input, please enter only 0\'s and 1\'s!");
                    break;
                }
            }

        }

        ArrayList<Integer> encodedMsg = encodeHamming(bitString);
        System.out.println("\n\nENCODED BIT STRING:");
        printArr(encodedMsg);

        Random rnd = new Random();
        int errorBit = rnd.nextInt(encodedMsg.size()) + 1;

        ArrayList<Integer> errMsg = flipBit(encodedMsg, errorBit);
        System.out.println("\n\nAFTER ERROR");
        printArr(errMsg);

        int error = findError(errMsg);

        System.out.println("\n\nERROR POSITION: " + error);

        scanner.close();
    }

    public static void printArr(ArrayList<Integer> arr) {
        for (Integer elemObject : arr) {
            System.out.print(elemObject + " ");
        }
    }

    public static int findError(ArrayList<Integer> data) {
        int error = 0;

        for (int i = 0; i < data.size(); i++) {
            if (powerOfTwo(i + 1)) {
                int hamBit = findHamBit(data, i + 1);
                if (hamBit != 0) {
                    error += i + 1;
                }
            }
        }
        return error;
    }

    public static ArrayList<Integer> flipBit(ArrayList<Integer> data, int bitNum) {
        ArrayList<Integer> flippedMsg = new ArrayList<>();
        for (Integer elem : data) {
            flippedMsg.add(elem);
        }
        flippedMsg.set(bitNum - 1, flippedMsg.get(bitNum - 1) ^ 1);

        return flippedMsg;
    }

    public static ArrayList<Integer> encodeHamming(String bitString) {
        ArrayList<Integer> data = new ArrayList<>();
        data = giveDataArray(bitString);

        for (int i = 0; i < data.size(); i++) {
            if (powerOfTwo(i + 1)) {
                int hamBit = findHamBit(data, i + 1);
                data.set(i, hamBit);
            }
        }

        return data;
    }

    public static int findHamBit(ArrayList<Integer> data, int pos) {
        int hamBit = 0;
        boolean skip = true;

        for (int i = pos - 1; i < data.size(); i++) {
            if (((i + 1) % pos) == 0) {
                skip = !skip;
            }

            if (!skip) {
                // System.out.print(data.get(i));
                hamBit = data.get(i) ^ hamBit;
            }

            // else {
            // System.out.print("_");
            // }
        }

        return hamBit;
    }

    public static ArrayList<Integer> giveDataArray(String bitS) {
        ArrayList<Integer> data = new ArrayList<>();
        for (int i = 1, j = 1; i <= bitS.length(); i++, j++) {
            if (powerOfTwo(j)) {
                data.add(0);
                i--;
            } else {
                data.add((bitS.charAt(i - 1) - '0'));
            }
        }
        return data;
    }

    public static boolean powerOfTwo(int x) {
        if (x == 0) {
            return false;
        }
        return (x & x - 1) == 0;
    }

}