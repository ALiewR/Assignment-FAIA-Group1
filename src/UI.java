import java.util.Scanner;

public abstract class UI {
    private Scanner scanner = new Scanner(System.in);
    private Printer printer;

    public UI(Printer printer) {
        this.printer = printer;
    }

    public void displayMessage(String msg) {
        checkPrinter();
        printer.print(msg);
    }
    public void displayLineMessage(String msg) {
        checkPrinter();
        printer.printLine(msg);
    }
    public void displayMessage(String msg, boolean isBold) {
        checkPrinter();
        printer.print(msg, isBold);
    }
    public void displayLineMessage(String msg, boolean isBold) {
        checkPrinter();
        printer.printLine(msg, isBold);
    }
    private void checkPrinter() {
        if (printer == null) printer = new ConsolePrinter(); // use default printer if no printer assigned
    }
    public int getUIInt() {
        return scanner.nextInt();
    }
}
