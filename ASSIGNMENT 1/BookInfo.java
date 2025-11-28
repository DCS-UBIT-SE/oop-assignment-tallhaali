import java.io.*;
import java.util.Scanner;

public class BookInfo {
    public String bookTitle;
    public String pubisher;
    public String author;
    public int numberOfPages;
    public float price;
    private static int numberOfBooks = 0;   // Static variables are created on class scope, and not object scope. Which means they're inaccessible through object but through the actual class.

    // Constructor functions
    public BookInfo () {
        numberOfBooks++;
    }

    // Constructor functions overloaded
    public BookInfo (String title, String author, String publisher, int noOfPages, float price) {
        numberOfBooks++;
        this.bookTitle = title;
        this.author = author;
        this.pubisher = publisher;
        this.numberOfPages = noOfPages;
        this.price = price;
    }

    public BookInfo (String title, String author, String publisher) {
        numberOfBooks++;
        this.bookTitle = title;
        this.author = author;
        this.pubisher = publisher;
    }

    public void displayBookInfo () {
        System.out.println("Book name: "+this.bookTitle);
        System.out.println("Author name: "+this.author);
        System.out.println("Publisher name: "+this.pubisher);
        System.out.println("Number of pages: "+this.numberOfPages);
    }

    public int getNumberOfBookObjs () {
        return numberOfBooks;
    }

    public boolean save() {
        return true;
    }
}

class EbookInfo extends BookInfo {
    String filePath;

    public EbookInfo () {
        super();
    }

    public EbookInfo (String title, String author, String publisher, String path, int noOfPages, float price) {
        super(title, author, publisher, noOfPages, price);
        filePath = path;
    }

    public EbookInfo (String title, String author, String publisher, String path) {
        super(title, author, publisher);
        filePath = path;
    }

    public void displayBook () {
        try {
            File bookFile = new File(filePath+".txt");
            Scanner reader = new Scanner(bookFile);

            System.out.println(this.bookTitle.toUpperCase());
            
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line);
            }
            reader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayBookPDF () {
        
        try {
            System.out.println("start chrome \""+filePath+".pdf\"");
            Runtime.getRuntime().exec(new String[]{
    "cmd", "/c", "start", "chrome", "\""+filePath+"\".pdf\""
});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}