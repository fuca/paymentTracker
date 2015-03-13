import org.apache.log4j.Logger;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Output manager takes care of application output
 * Runs in separate thread
 */
public class OutputManager extends Thread {

    static final Logger logger = Logger.getLogger(OutputManager.class);

    private PaymentDaoHashMapImpl pDao;
    private Long interval;
    private Timer timer;

    public OutputManager(PaymentDaoHashMapImpl pd) {
        this.pDao = pd;
        this.timer = new Timer();
        this.interval = 60*1000L;
    }

    public Long getInterval() {
        return this.interval;
    }

    /**
     * Sets time interval of output to console
     * @param i millis
     */
    public void setInterval(Long i) {
        logger.debug("Timeout interval of output manager changed to "+i);
        this.interval = i;
    }

    /**
     * Lifecycle of Output manager
     */
    public void run() {
        logger.debug("Output manager is running");
        this.timer.schedule(
                new TimerTask(){
                    public void run() {
                        System.out.println();
                        StringBuilder sb = new StringBuilder();
                        Map<String, Double> blah = pDao.getData();
                        System.out.println("--- STATUS ---");
                        for (Map.Entry<String, Double> entry: blah.entrySet()) {
                            sb.append(entry.getKey());
                            sb.append(" ");
                            sb.append(entry.getValue());
                            System.out.println(sb.toString());
                            sb.setLength(0);
                        }
                        System.out.println("--- ------ ---");
                    }
                },0, this.interval
        );
    }

    public void shutDown() {
        logger.debug("Output manager is goin to be shutted down");
        this.timer.cancel();
    }
}
