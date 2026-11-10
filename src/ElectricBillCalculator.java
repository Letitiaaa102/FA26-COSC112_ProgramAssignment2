
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class ElectricBillCalculator {
    public static void main(String[] args) {
        try {
            File inputFile = new File("Customer_Data.txt");
            Scanner input = new Scanner(inputFile);

            if (!input.hasNext()) {
                JOptionPane.showMessageDialog(null, "Error: Input file is empty.");
                input.close();
                return;
            }

            String customerID = input.next();
            String firstName = input.next();
            String lastName = input.next();

            if (!input.hasNextDouble()) {
                JOptionPane.showMessageDialog(null, "Error: Invalid electricity usage.");
                input.close();
                return;
            }

            double usage = input.nextDouble();
            String paymentStatus = input.next();
            input.close();

            if (customerID.isEmpty() || firstName.isEmpty() ||
                    lastName.isEmpty() || usage < 0 ||
                    (!paymentStatus.equalsIgnoreCase("Y") &&
                            !paymentStatus.equalsIgnoreCase("N"))) {

                JOptionPane.showMessageDialog(null,
                        "Error: Invalid customer information.");
                return;
            }

            double electricityCharge;

            if (usage <= 100) {
                electricityCharge = usage * 0.10;
            } else if (usage <= 300) {
                electricityCharge = (100 * 0.10)
                        + ((usage - 100) * 0.15);
            } else {
                electricityCharge = (100 * 0.10)
                        + (200 * 0.15)
                        + ((usage - 300) * 0.20);
            }

            double lateFee = 0.00;
            String paymentMessage = "Previous bill not overdue";

            if (paymentStatus.equalsIgnoreCase("Y")) {
                lateFee = 15.00;
                paymentMessage = "Previous bill overdue";
            }

            double totalAmount = electricityCharge + lateFee;

            String bill = String.format(
                    "MONTHLY UTILITY BILL%n" +
                            "==============================%n" +
                            "Customer ID: %s%n" +
                            "Customer Name: %s %s%n" +
                            "Electricity Usage: %.0f kWh%n%n" +
                            "Electricity Charge: $%.2f%n" +
                            "Late-Payment Fee: $%.2f%n%n" +
                            "TOTAL AMOUNT DUE: $%.2f%n" +
                            "Payment Status: %s",
                    customerID, firstName, lastName, usage,
                    electricityCharge, lateFee, totalAmount,
                    paymentMessage
            );

            JOptionPane.showMessageDialog(null, bill);

            PrintWriter output =
                    new PrintWriter("UtilityBillReport.txt");
            output.println(bill);
            output.close();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Error: Input file not found.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error: Invalid or missing input data.");
        }
    }
}
