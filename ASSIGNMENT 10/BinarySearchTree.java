import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BinarySearchTree {
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // Read BST from CSV file. CSV is expected to contain one node per
        // line in the form: leftIndex,value,rightIndex
        int[][] bst = giveBSTFromFile("bst.csv");
        String query = "";

        // Simple loop until user chooses to quit
        while (!(query.trim().equalsIgnoreCase("quit"))) {

            System.out.println("\n\nPlease select an option from below:");
            System.out.println("1.\tPrint all leaf nodes of the binary search tree.");
            System.out.println("2.\tSearch for a number in the binary search tree.");
            System.out.println("3.\tPrint the parent and child nodes of an element.");
            System.out.println("4.\tPrint the binary search tree with in-order traversal");
            System.out.println("5.\tQuit");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    // Print all leaves (nodes with no children)
                    printLeafNodes(bst);
                    Thread.sleep(2000);
                    break;

                case 2:
                    // Search for a value and print its index (or not found)
                    // Search for a value and print its index (or not found)
                    System.out.print("Please enter the target number: ");
                    int target = scanner.nextInt();
                    int res = findInBST(bst, target, 0);
                    if (res != -1) {
                        System.out.println("The number " + target + " is at " + res + " index.");
                    } else {
                        System.out.println("The number doesn't exist in the binary search tree.");
                    }
                    Thread.sleep(2000);
                    break;

                case 3:
                    // Print the parent and children of a target node
                    System.out.print("Please enter the target number: ");
                    target = scanner.nextInt();
                    printChildnParent(bst, target);
                    Thread.sleep(2000);
                    break;

                case 4:
                    // Print values with in-order traversal starting at root
                    printBSTInOrd(bst, 0);
                    Thread.sleep(2000);
                    break;
                    
                case 5:
                    // Exit loop
                    query = "quit";
                    break;


                default:
                    System.out.println("Invalid option! Please choose from 1-3.");
                    break;
            }

        }

        // Save the tree as a CSV file before exiting
        saveBSTtoFile(bst, "bst.csv");
        scanner.close();

    }

    // Printing the binary search tree
    public static void printBST(int[][] bst) {
        for (int i = 0; i < bst.length; i++) {
            System.out.println(bst[i][0] + "\t" + bst[i][1] + "\t" + bst[i][2]);
        }
    }

    public static void printBSTInOrd(int[][] bst, int i) {
        // In-order traversal: left -> node -> right
        // BASE CASES
        // Leaf node (no children)
        if (bst[i][0] == -1 && bst[i][2] == -1) {
            System.out.print(bst[i][1]);
        } 
        // No left child, only print node then traverse right
        else if (bst[i][0] == -1 && bst[i][2] != -1) {
            System.out.print(bst[i][1] + " ");
            printBSTInOrd(bst, bst[i][2]);
        } 
        
        // Has left child: traverse left, print node, traverse right
        else {
            printBSTInOrd(bst, bst[i][0]);
            System.out.print(" " + bst[i][1] + " ");
            printBSTInOrd(bst, bst[i][2]);
        }
    }

    public static void printBSTPreOrd(int[][] bst, int i) {
        // Pre-order traversal: node -> left -> right
        if (bst[i][0] == -1 && bst[i][2] == -1) {
            // Leaf
            System.out.print(bst[i][1] + " ");
        } else if (bst[i][0] == -1 && bst[i][2] != -1) {
            // No left child
            System.out.print(bst[i][1] + " ");
            printBSTPreOrd(bst, bst[i][2]);
        } else {
            System.out.print(bst[i][1] + " ");
            printBSTPreOrd(bst, bst[i][0]);
            printBSTPreOrd(bst, bst[i][2]);
        }
    }

    public static void printBSTPostOrd(int[][] bst, int i) {
        // Post-order traversal: left -> right -> node
        if (bst[i][0] == -1 && bst[i][2] == -1) {
            // Leaf
            System.out.print(bst[i][1] + " ");
        } else if (bst[i][0] == -1 && bst[i][2] != -1) {
            // Only right child
            printBSTPostOrd(bst, bst[i][2]);
            System.out.print(bst[i][1] + " ");
        } else {
            printBSTPostOrd(bst, bst[i][0]);
            printBSTPostOrd(bst, bst[i][2]);
            System.out.print(bst[i][1] + " ");
        }
    }

    // Finding and returning if a binary search tree exists in file
    public static int[][] giveBSTFromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        String line = "";
        // Read whole file into a single string, then split by line.
        while (scanner.hasNextLine()) {
            line += scanner.nextLine() + '\n';
        }

        String[] contents = line.split("\n");
        int[][] bst = new int[contents.length][3];

        // Parse each CSV line into the [leftIndex, value, rightIndex] format into the bst array
        for (int i = 0; i < contents.length; i++) {
            String[] temp = (contents[i]).split(",");
            bst[i][0] = Integer.parseInt(temp[0]);
            bst[i][1] = Integer.parseInt(temp[1]);
            bst[i][2] = Integer.parseInt(temp[2]);
        }

        scanner.close();
        return bst;

    }

    public static void saveBSTtoFile(int[][] bst, String filename) throws IOException {
        File file = new File(filename);
        FileWriter fw = new FileWriter(file);
        for (int i = 0; i < bst.length; i++) {
            fw.write(String.valueOf(bst[i][0]));
            fw.write(',');
            fw.write(String.valueOf(bst[i][1]));
            fw.write(',');
            fw.write(String.valueOf(bst[i][2]));
            fw.write('\n');
        }

        fw.close();
    }

    // Initialize the binary search tree with the data-array as input
    public static int[][] initBST(int[] inpArray) {
        int[][] bst = new int[inpArray.length][3];
        // Fill nodes with default -1 for missing children and the given value
        for (int i = 0; i < inpArray.length; i++) {
            bst[i][0] = -1; // left child index
            bst[i][1] = inpArray[i]; // node value
            bst[i][2] = -1; // right child index

            // Update the parent/child indices for previously inserted nodes
            if (i != 0) {
                bst = updateIndices(bst, i, 0);

            }
        }
        return bst;
    }

    // Recursive function for updating the indices of previous nodes in the tree
    // when a new node is entered
    public static int[][] updateIndices(int[][] bst, int numi, int i) {
        // Compare the new node's value with the current node's value and
        // descend left or right accordingly, updating indices when an empty
        // child spot (-1) is found.
        if (bst[numi][1] > bst[i][1]) {
            // New value is greater -> go right
            if (bst[i][2] == -1) {
                bst[i][2] = numi;
            } else {
                bst = updateIndices(bst, numi, bst[i][2]);
            }
        }

        else {
            // New value is less or equal -> go left
            if (bst[i][0] == -1) {
                bst[i][0] = numi;
            } else {
                bst = updateIndices(bst, numi, bst[i][0]);
            }
        }

        return bst;
    }

    // Function for printing the leaf nodes in a binary search tree
    public static void printLeafNodes(int[][] bst) {
        System.out.println("Leaf Nodes:");
        for (int i = 0; i < bst.length; i++) {
            if ((bst[i][0] == -1) && (bst[i][2] == -1)) {
                System.out.print(bst[i][1] + " ");
            }
        }
    }

    public static int findInBST(int[][] bst, int target, int i) {
        // BASE CASE
        // If we've found the number, return it's index
        if (bst[i][1] == target) {
            return i;
        }

        // RECURSION
        // If the node we've reached is greater than our target, go to the node's left
        // child node (if available)
        if (bst[i][1] > target) {
            if (bst[i][0] == -1) {
                return -1;
            }
            return findInBST(bst, target, bst[i][0]);
        }

        // If the node we've reached is lesser than our target, go to the node's right
        // child node (if available)
        else {
            if (bst[i][2] == -1) {
                return -1;
            }
            return findInBST(bst, target, bst[i][2]);
        }
    }

    // Print the parent nodes data and child nodes data of a target value (if exists)
    public static void printChildnParent(int[][] bst, int target) {
        int targInd = findInBST(bst, target, 0);
        if (targInd == -1) {
            System.out.println("The target number: " + target + " is not in the binary search tree!");
        } else {
            int parInd = giveParent(bst, target, 0, 0);
            System.out.println("Parent Node: " + bst[parInd][1]);
            System.out.println("Child Nodes: " + (((bst[targInd][0] != -1) ? bst[bst[targInd][0]][1] : "none") + ", "
                    + ((bst[targInd][2] != -1) ? bst[bst[targInd][2]][1] : "none")));
        }
    }

    public static int giveParent(int[][] bst, int target, int i, int j) {
        // BASE CASE
        // If we've found the number, return it's index
        if (bst[i][1] == target) {
            return j;
        }

        // RECURSION
        // If the node we've reached is greater than our target, go to the node's left
        // child node (if available)
        if (bst[i][1] > target) {
            if (bst[i][0] == -1) {
                return -1;
            }
            return giveParent(bst, target, bst[i][0], i);
        }

        // If the node we've reached is lesser than our target, go to the node's right
        // child node (if available)
        else {
            if (bst[i][2] == -1) {
                return -1;
            }
            return giveParent(bst, target, bst[i][2], i);
        }
    }
}