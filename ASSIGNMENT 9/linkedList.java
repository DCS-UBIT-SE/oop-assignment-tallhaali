import java.util.Scanner;

public class linkedList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Node.startingIndex = 1;
        Node[] linked_list = { new Node(65, 3), new Node(15, 2), new Node(45, 0), new Node(72, -1) };

        // Main program loop
        String message = "";
        while (!message.equalsIgnoreCase("quit")) {
            System.out.println("ACTIONS:\n\t1. Create a new linked list.");
            System.out.println("\t2. Add an element to the list.");
            System.out.println("\t3. Print all the elements of the list.");
            System.out.println("\t4. Quit (or type 'quit')");
            System.out.print("\nPlease choose an action from above (1-4): ");

            message = scanner.nextLine();
            String choice = message.trim();

            if (choice.equalsIgnoreCase("1")) {
                linked_list = new Node[0];
                Node.startingIndex = -1;
                System.out.println("New empty linked list created.");
            } else if (choice.equalsIgnoreCase("2")) {
                System.out.print("Enter the element (number): ");
                String value = scanner.nextLine().trim();
                try {
                    float num = Float.parseFloat(value);
                    linked_list = addElement(num, linked_list);
                    System.out.println(num + " added to the list.");
                } catch (NumberFormatException nfe) {
                    System.out.println("Invalid number entered. Please try again.");
                }
            } else if (choice.equalsIgnoreCase("3")) {
                printLinkedList(linked_list);
            } else if (choice.equalsIgnoreCase("4") || choice.equalsIgnoreCase("quit")) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, 3 or 4 (or type 'quit').");
            }
        }

        scanner.close();

    }

    public static void printLinkedList(Node[] ll) {
        System.out.println("\nLinked List:");
        if (ll == null || ll.length == 0 || Node.startingIndex == -1) {
            System.out.println("<empty>");
            System.out.println();
            return;
        }

        int i = Node.startingIndex;
        int safety = 0; // avoid infinite loops in case of corrupted nextIndex
        while (i != -1) {
            if (i < 0 || i >= ll.length) {
                System.out.println("[error] invalid node index: " + i);
                break;
            }
            System.out.println(ll[i].data);
            i = ll[i].nextIndex;
            safety++;
            if (safety > ll.length + 5) { // simple loop detection
                System.out.println("[error] possible cycle detected in list, aborting print.");
                break;
            }
        }
        System.out.println();
    }

    // To add elements to the linked list
    public static Node[] addElement(float new_num, Node[] linked_list) {
        int i = Node.startingIndex;
        int j = Node.startingIndex;

        if (linked_list.length == 0) {
            linked_list = addListElement(linked_list, new Node(new_num, -1));
            Node.startingIndex = 0;
        } else if (new_num < linked_list[i].data) {
            Node.startingIndex = linked_list.length;
            linked_list = addListElement(linked_list, new Node(new_num, i));
        } else {

            while (i != -1 && linked_list[i].data <= new_num) {
                j = i;
                i = linked_list[i].nextIndex;
            }

            linked_list[j].nextIndex = linked_list.length;
            linked_list = addListElement(linked_list, new Node(new_num, i));

        }

        return linked_list;
    }

    public static Node[] addListElement(Node[] ll, Node element) {
        Node[] newll = new Node[ll.length + 1];

        for (int i = 0; i < ll.length; i++) {
            newll[i] = ll[i];
        }

        newll[newll.length - 1] = element;

        return newll;
    }

}

class Node {
    static int startingIndex;
    float data;
    int nextIndex;

    Node(float d, int ni) {
        data = d;
        nextIndex = ni;
    }
}