/**
 * Application PaymentTracker
 */
public class App {

    /**
     * Entry point to executing of program
     * @param args
     */
    public static void main (String [] args) {

        ConsoleReader consoleReader = new ConsoleReader();

        /** Shared object */
        PaymentDaoHashMapImpl paymentDao = new PaymentDaoHashMapImpl();

        /** Initialization */
        InputManager reader = new InputManager(consoleReader, paymentDao);
        OutputManager writer = new OutputManager(paymentDao);
        writer.setInterval(20*1000L);

        /** Threads starting */
        writer.run();
        reader.run();
    }
}
