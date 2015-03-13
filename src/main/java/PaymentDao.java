import java.util.Map;

/**
 * Interface for payment dao
 */
public interface PaymentDao {

    void createPayment(String currency, Double amount);
    Map<String, Double> getData();
}
