import com.sun.media.sound.InvalidFormatException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Takes care of console input and commands executing
 */
public class InputManager extends Thread {

    static final Logger logger = Logger.getLogger(InputManager.class);

    private ConsoleReader cReader;
    private FileReader fReader;
    private PaymentDaoHashMapImpl pDao;

    public InputManager (ConsoleReader cr, PaymentDaoHashMapImpl pd) {
        this.cReader = cr;
        this.fReader = null;
        this.pDao = pd;
    }

    /**
     * Input manager lifecycle
     */
    public void run() {
        logger.debug("Input manager is running");
        while (true) {
            try {
                String s = cReader.readLine();
                executeCommand(s);
            } catch (InvalidFormatException e) {
                System.out.println("Bad format of input! Example: USD 100");
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }

    /**
     * Validation and recognizing of input line commands
     * @param s Input line content
     * @throws InvalidFormatException
     */
    private void executeCommand(String s) throws InvalidFormatException {
        List<String> lines = null;

        if (quitCommand(s)) {
            logger.debug("Quit command recognized");
            Runtime.getRuntime().halt(0);
            //System.exit(0);
            return;
        }

        if (fileCommand(s)) {
            logger.debug("File command recognized");
            try {
                if (this.fReader == null) {
                    String[] chunks = s.split("\\s+");
                    this.fReader = new FileReader(chunks[1]);
                }
                lines = fReader.readInputFile(s);
                for(String line: lines) {
                    parsePayment(line);
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            return;
        }

        if (inputCommand(s)) {
            logger.debug("Console payment input command recognized");
            parsePayment(s);
            return;
        }
    }

    /**
     * Parses payment chunks from line content and ensures adding
     * @param s Input line content
     */
    private void parsePayment(String s) {
        String currency = parseCurrency(s);
        Double amount = parseAmount(s);
        this.pDao.createPayment(currency, amount);
    }


    /**
     * Checks whether input line contains read from file command (-f)
     * @param s Input line content
     * @return if command is present
     * @throws InvalidFormatException
     */
    private boolean fileCommand(String s) throws InvalidFormatException {
        CharSequence cs = "-f";
        if (s.contains(cs)) {
            if (!s.matches("-f\\u0020.+\\.txt")) {
                throw new InvalidFormatException("Passed command or its argument doest not fit in required format");
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Checks whether input line contains quit command
     * @param s Input line content
     * @return if command is present
     * @throws InvalidFormatException
     */
    private boolean quitCommand(String s) throws InvalidFormatException {
        CharSequence cs = "quit";
        if (s.contains(cs)) {
            if (!s.matches("quit")) {
                throw new InvalidFormatException("Bad command format");
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Checks whether input line contains new payment command
     * @param s Input line content
     * @return if command is present
     * @throws InvalidFormatException
     */
    private boolean inputCommand(String s) throws InvalidFormatException {
        if (!s.isEmpty() && Character.isUpperCase(s.charAt(0))) {
            if (!s.matches("[A-Z]{3}\\u0020-?\\d+.?\\d*")) {
                throw new InvalidFormatException("Passed string does not have valid format");
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Parses currency code from string SSS DDDDD
     * @param s
     * @return currency code
     */
    private String parseCurrency (String s) {
        String currency = s.substring(0,3);
        return currency;
    }

    /**
     * Parses amount value
     * @param s
     * @return value
     */
    private Double parseAmount(String s) {
        Double amount = Double.parseDouble(s.substring(4));
        return amount;
    }
}
