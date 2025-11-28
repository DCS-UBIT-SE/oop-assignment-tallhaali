import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/*

ASSUMPTIONS:
 - I am assuming that the files will always contain the final two fields which will have the qty and the price of the products.
 - Also that the date field would include a "date" title.
 - And that the date would be formatted as "XX-MONTH-XX" and the user would want the answer month wise.

 */

public class Analyzer {
    public static void main(String[] args) {
        String path = "sales.tab";
        Scanner scanner = new Scanner(System.in);

        try {
            // Creating the file object and the file reader
            File dataFile = new File(path);
            Scanner read = new Scanner(dataFile);

            // TASK: 1 Total Sales Amount
            double totAmount = giveSalesTotal(read, "\t");
            System.out.println("Total Sales Amount: " + totAmount);


            // TASK: 2 Employee-wise Sales Amount
            read = new Scanner(dataFile);

            HashMap<String, Double> empTot = filteredTotal(read, 2, "\t");

            System.out.print("\nEMPLOYEE WISE ");
            printMapData(empTot);


            // TASK: 3 Product-wise Sales Amount
            read = new Scanner(dataFile);

            HashMap<String, Double> prodTot = filteredTotal(read, 3, "\t");

            System.out.print("\nPRODUCT WISE ");
            printMapData(prodTot);


            // TASK: 4 Region-wise Sales Amount
            read = new Scanner(dataFile);

            HashMap<String, Double> regTot = filteredTotal(read, 1, "\t");

            System.out.print("\nREGION WISE ");
            printMapData(regTot);


            // TASK: 5 Month-wise Sales Amount
            read = new Scanner(dataFile);

            HashMap<String, Double> monthTot = filterDatewiseTotal(read, 0, "\t");

            System.out.print("\nMONTH WISE ");
            printMapData(monthTot);

            System.out.print("Please give the file path: ");
            String filePath = scanner.nextLine();


            // CHALLENGE: Auto-generation of menu options and generic manipulation
            try {
                File file = new File(filePath);
                read = new Scanner(file);

                String fieldHead = read.nextLine();
                String fieldHeads[] = fieldHead.split("\t");

                System.out.println("\nYour data contains the following fields:");
                for (int i = 0; i < (fieldHeads.length - 2); i++) {
                    System.out.println(" " + i + ". " + fieldHeads[i]);
                }
                System.out.print("Please select a field to filter the data with (0-" + (fieldHeads.length - 3) + "): ");

                int fieldI = scanner.nextInt();
                
                System.out.print("\n");

                if (fieldHeads[fieldI].equalsIgnoreCase("date")) {
                    HashMap<String, Double> data = filterDatewiseTotal(read, fieldI, "\t");
                    printMapData(data);

                } else {
                    HashMap<String, Double> data = filteredTotal(read, fieldI, "\t");
                    printMapData(data);

                }
            } catch (FileNotFoundException fnfe) {
                System.out.println("No file found at \"" + filePath + "\"");
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println("No file found at \"" + path + "\"");
        }

        scanner.close();
    }

    public static double giveSalesTotal(Scanner fileReader, String regExt) {
        double total = 0;
        fileReader.nextLine();

        while (fileReader.hasNextLine()) {
            String[] fields = fileReader.nextLine().split(regExt);
            int qty = Integer.parseInt(fields[(fields.length - 2)]);
            double price = Double.parseDouble(fields[(fields.length - 1)]);
            total += qty * price;
        }

        return total;
    }

    public static void printMapData(HashMap<String, Double> data) {
        System.out.println("FILTERED DATA:");
        for (HashMap.Entry<String, Double> entry : data.entrySet()) {
            String name = entry.getKey();
            Double sales = entry.getValue();
            System.out.println(name + ":\t" + sales);
        }
    }

    public static HashMap<String, Double> filterDatewiseTotal(Scanner fileReader, int fieldInd, String regExt) {
        HashMap<String, Double> data = new HashMap<>();
        fileReader.nextLine();

        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] fields = line.split(regExt);

            String filter = fields[fieldInd].split("-")[1];

            if (data.containsKey(filter)) {
                double am = data.get(filter);
                double price = Double.parseDouble(fields[fields.length - 1]);
                int qty = Integer.parseInt(fields[fields.length - 2]);
                am += price * qty;
                data.replace(filter, am);
            } else {
                double price = Double.parseDouble(fields[fields.length - 1]);
                int qty = Integer.parseInt(fields[fields.length - 2]);
                double am = price * qty;
                data.put(filter, am);
            }
        }

        return data;
    }

    public static HashMap<String, Double> filteredTotal(Scanner fileReader, int fieldInd, String regExt) {
        HashMap<String, Double> data = new HashMap<>();
        fileReader.nextLine();

        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] fields = line.split(regExt);

            if (data.containsKey(fields[fieldInd])) {

                double am = data.get(fields[fieldInd]);
                double price = Double.parseDouble(fields[fields.length - 1]);
                int qty = Integer.parseInt(fields[fields.length - 2]);
                am += price * qty;
                data.replace(fields[fieldInd], am);

            } else {
                double price = Double.parseDouble(fields[fields.length - 1]);
                int qty = Integer.parseInt(fields[fields.length - 2]);
                double am = price * qty;
                data.put(fields[fieldInd], am);
            }
        }

        return data;
    }
}