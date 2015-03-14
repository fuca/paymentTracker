import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * HashMap implementation of PaymentDao
 */
public class PaymentDaoHashMapImpl implements PaymentDao {

    static final Logger logger = Logger.getLogger(PaymentDaoHashMapImpl.class);

    private ConcurrentMap<String, Double> storage = null;

    public PaymentDaoHashMapImpl() {
        this.storage = new ConcurrentHashMap<String, Double>();
    }

    /**
     * Creates single payment
     * @param currency currency code
     * @param amount amount value
     */
    public void createPayment(String currency, Double amount) {
        logger.debug("Creating payment :: "+currency+" "+amount);
        if (this.storage.containsKey(currency)) {
            this.storage.put(currency, this.storage.get(currency) + amount);
        } else {
            this.storage.put(currency, amount);
        }
    }

    /**
     * Returns value of passed currency
     * @param code currency code
     * @return amount of passed currency
     */
    public Double getAmount(String code) {
        return this.storage.get(code);
    }

    /**
     * Returns copy of data collection
     * @return Map of entries
     */
    public Map<String, Double> getData() {
        logger.debug("Returning data collection");
        Map<String, Double> coll = new HashMap<String, Double>();
        coll.putAll(this.storage);
        return coll;
    }
}
