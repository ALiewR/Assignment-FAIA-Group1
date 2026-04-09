public class ConsolePrinter implements Printer{
    @Override
    public void print(String message){
        System.out.print(message);
    }
    @Override
    public void printLine(String message){
        System.out.println(message);
    }
    @Override
    public void print(String message,boolean isBold){
        if (isBold){
            System.out.print("\u001B[1m" + message + "\u001B[0m");
        } else{
            System.out.print(message);
        }
    }
    @Override
    public void printLine(String message, boolean isBold){
        if (isBold){
            System.out.println("\u001B[1m" + message + "\u001B[0m");
        } else{
            System.out.println(message);
        }
    }
}