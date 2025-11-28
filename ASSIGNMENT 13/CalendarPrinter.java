import java.time.LocalDate;
import java.util.Scanner;

public class CalendarPrinter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int year;
        int month;

        System.out.print("Please enter the year: ");
        year = scanner.nextInt();

        System.out.print("Please enter the month: ");
        month = scanner.nextInt();

        printCalendar(year, month);

        scanner.close();
    }

    public static void printCalendar(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);

        int startDay = (date.getDayOfWeek()).getValue();
        int monthDays = date.lengthOfMonth();

        System.out.println("\nMON\tTUE\tWED\tTHU\tFRI\tSAT\tSUN");

        
        for (int i = 1, day = 1 ; day <= monthDays; i++, day++) {
            if (startDay > 1) {
                System.out.print('\t');
                startDay--;
                day--;
            } else {
                System.out.print(day + "\t");
                if (i % 7 == 0) {
                    System.out.print('\n');
                }
            }
        }
    }
}
