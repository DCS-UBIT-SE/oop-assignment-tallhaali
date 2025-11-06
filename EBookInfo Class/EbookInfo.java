import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EbookInfo {
    String author;
    String title;
    public void readBookInfo(String filePath) {
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                if (line.startsWith("Title:")) {
                    title = line.replace("Title:", "").trim();
                } else if (line.startsWith("Author:")) {
                    author = line.replace("Author:", "").trim();
                }
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
    }

    public void displayInfo() {
        System.out.println("=== E-Book Information ===");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
    }

    public static void main(String[] args) {
        EbookInfo ebook = new EbookInfo();
        ebook.readBookInfo("Ebook.txt"); // read from file
        ebook.displayInfo();
    }
}
