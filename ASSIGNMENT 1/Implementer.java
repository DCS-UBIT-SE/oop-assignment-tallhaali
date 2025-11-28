public class Implementer {
    public static void main(String[] args) {
        String path = "C:\\Users\\Abdullah\\Documents\\GitHub\\University-Projects\\SEM-II\\java projects\\Book Info\\Rules_for_Being_Human";
        EbookInfo eb1 = new EbookInfo("Rules for Being Human", "Cherie Carter-Scott", "Insightful Publishers", path, 2, 1500);
        
        eb1.displayBookInfo();
        System.out.println("\n");
        eb1.displayBook();
        eb1.displayBookPDF();

    }
    
}