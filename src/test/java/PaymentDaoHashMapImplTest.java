import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PaymentDaoHashMapImplTest {

    private PaymentDaoHashMapImpl pDao;

    @Before
    public void setUp() {
        this.pDao = new PaymentDaoHashMapImpl();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void createPaymentTest() {
        assertEquals(this.pDao.getData().size(), 0);

        this.pDao.createPayment("USD", 1000D);
        assertTrue(this.pDao.getAmount("USD").equals(1000D));
        assertEquals(this.pDao.getData().size(), 1);

        this.pDao.createPayment("CZK", 500D);
        assertTrue(this.pDao.getAmount("CZK").equals(500D));
        assertEquals(this.pDao.getData().size(), 2);

        this.pDao.createPayment("CZK", -500D);
        assertTrue(this.pDao.getAmount("CZK").equals(0D));
        assertEquals(this.pDao.getData().size(), 2);
    }

    @Test
    public void getDataTest() {
        this.pDao.createPayment("USD", 1000D);
        this.pDao.createPayment("CZK", 500D);
        this.pDao.createPayment("CZK", -400D);
        this.pDao.createPayment("HRK", -400D);

        Map<String, Double> result = this.pDao.getData();

        assertEquals(result.size(), 3);

        assertTrue(result.get("USD").equals(1000D));
        assertTrue(result.get("CZK").equals(100D));
        assertTrue(result.get("HRK").equals(-400D));
        assertNull(result.get("ABC"));

        this.pDao.createPayment("ABC", 50D);
        result = this.pDao.getData();
        assertTrue(result.get("ABC").equals(50D));
    }
}
