import ui.ConsoleUI;
import util.InputValidator;

public class App {
    public static void main(String[] args) throws Exception {
        ConsoleUI cli = new ConsoleUI();
        cli.start();

        InputValidator.closeScanner();
    }
}
