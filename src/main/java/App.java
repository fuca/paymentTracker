/**
 * Application PaymentTracker
 */
public class App {

    /**
     * Entry point to executing of program
     * @param args application args
     */
    public static void main (String [] args) {

        ConsoleReader consoleReader = new ConsoleReader();

        /** Shared object */
        PaymentDaoHashMapImpl paymentDao = new PaymentDaoHashMapImpl();

        /** Initialization */
        InputManager reader = new InputManager(consoleReader, paymentDao);
        OutputManager writer = new OutputManager(paymentDao);

        /** Threads starting */
        writer.run();
        reader.run();
    }
}
