import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class InputManagerTest {

    static final Logger logger = Logger.getLogger(InputManagerTest.class);

    private InputManager im;
    private PaymentDao pDao;

    @Before
    public void setUp() {
        this.pDao = new PaymentDaoHashMapImpl();
        this.im = new InputManager(new ConsoleReader(), this.pDao);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void parseAmountTest() {
        Double resAmount1, resAmount2, resAmount3;
        try {
            Method method = InputManager.class.getDeclaredMethod("parseAmount", String.class);
            method.setAccessible(true);

            resAmount1 = Double.parseDouble(method.invoke(this.im, "USD 1000").toString());
            resAmount2 = Double.parseDouble(method.invoke(this.im, "USD -1000").toString());
            resAmount3 = Double.parseDouble(method.invoke(this.im, "USD 1000.1").toString());

            assertEquals(resAmount1, Double.valueOf(1000D));
            assertEquals(resAmount2, Double.valueOf(-1000D));
            assertEquals(resAmount3, Double.valueOf(1000.1D));
        } catch (NoSuchMethodException e) {
            logger.error(e);
            fail();
        } catch (IllegalAccessException e) {
            logger.error(e);
            fail();
        } catch (InvocationTargetException e) {
            logger.error(e);
            fail();
        }
    }

    @Test
    public void parseCurrencyTest() {
        String resCurrency1, resCurrency2, resCurrency3;
        try {
            Method method = InputManager.class.getDeclaredMethod("parseCurrency", String.class);
            method.setAccessible(true);

            resCurrency1 = method.invoke(this.im, "USD 1000").toString();
            resCurrency2 = method.invoke(this.im, "CZK 100").toString();
            resCurrency3 = method.invoke(this.im, "EUR 1000").toString();

            assertEquals(resCurrency1, "USD");
            assertEquals(resCurrency2, "CZK");
            assertEquals(resCurrency3, "EUR");

        } catch (NoSuchMethodException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        } catch (InvocationTargetException e) {
            logger.error(e);
        }

    }

    @Test
    public void inputCommandTestFail1() {
        try {
            Method method = InputManager.class.getDeclaredMethod("inputCommand", String.class);
            method.setAccessible(true);

            method.invoke(this.im, "CZ 1000");
        } catch (NoSuchMethodException e) {
            logger.error(e);
            fail();
        } catch (IllegalAccessException e) {
            logger.error(e);
            fail();
        } catch (InvocationTargetException e) {
            logger.error(e);
            assertTrue(true);
        }
    }

    @Test
    public void inputCommandTestFail2() {
        try {
            Method method = InputManager.class.getDeclaredMethod("inputCommand", String.class);
            method.setAccessible(true);

            method.invoke(this.im, "HRK ");

        } catch (NoSuchMethodException e) {
            logger.error(e);
            fail();
        } catch (IllegalAccessException e) {
            logger.error(e);
            fail();
        } catch (InvocationTargetException e) {
            logger.error(e);
            assertTrue(true);
        }
    }

    @Test
    public void inputCommandTestFail3() {
        try {
            Method method = InputManager.class.getDeclaredMethod("inputCommand", String.class);
            method.setAccessible(true);

            method.invoke(this.im, "1000");

        } catch (NoSuchMethodException e) {
            logger.error(e);
            fail();
        } catch (IllegalAccessException e) {
            logger.error(e);
            fail();
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void inputCommandTest() {
        Boolean res1, res2, res3, res4;
        try {
            Method method = InputManager.class.getDeclaredMethod("inputCommand", String.class);
            method.setAccessible(true);

            res1 = Boolean.parseBoolean(method.invoke(this.im, "USD 1000").toString());
            assertTrue(res1);

        } catch (NoSuchMethodException e) {
            logger.error(e);
            fail();
        } catch (IllegalAccessException e) {
            logger.error(e);
            fail();
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
            fail();
        }
    }

    @Test
    public void quitCommandTestFail1() {
        try {
            Method method = InputManager.class.getDeclaredMethod("quitCommand", String.class);
            method.setAccessible(true);

            method.invoke(this.im, " quit");
        } catch (NoSuchMethodException e) {
            logger.error(e);
            fail();
        } catch (IllegalAccessException e) {
            logger.error(e);
            fail();
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void quitCommandTestFail2() {
        try {
            Method method = InputManager.class.getDeclaredMethod("quitCommand", String.class);
            method.setAccessible(true);

            method.invoke(this.im, "quit ");
        } catch (NoSuchMethodException e) {
            logger.error(e);
            fail();
        } catch (IllegalAccessException e) {
            logger.error(e);
            fail();
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void quitCommandTest() {
        Boolean res1, res2;
        try {
            Method method = InputManager.class.getDeclaredMethod("quitCommand", String.class);
            method.setAccessible(true);

            res1 = Boolean.parseBoolean(method.invoke(this.im, "quit").toString());
            res2 = Boolean.parseBoolean(method.invoke(this.im, "qui").toString());

            assertTrue(res1);
            assertFalse(res2);

        } catch (NoSuchMethodException e) {
            logger.error(e);
            fail();
        } catch (IllegalAccessException e) {
            logger.error(e);
            fail();
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
            fail();
        }
    }

    @Test
    public void fileCommandTest() {
        Boolean res1, res2, res3, res4, res5;
        try {
            Method method = InputManager.class.getDeclaredMethod("fileCommand", String.class);
            method.setAccessible(true);

            res1 = Boolean.parseBoolean(method.invoke(this.im, "-f blah.txt").toString());
            res2 = Boolean.parseBoolean(method.invoke(this.im, "-f C:\\FooFolder\\blah.txt").toString());
            res3 = Boolean.parseBoolean(method.invoke(this.im, "-f /usr/blah.txt").toString());
            res4 = Boolean.parseBoolean(method.invoke(this.im, "f /usr/blah.txt").toString());
            res5 = Boolean.parseBoolean(method.invoke(this.im, "/usr/blah.txt").toString());

            assertTrue(res1);
            assertTrue(res2);
            assertTrue(res3);
            assertFalse(res4);
            assertFalse(res5);

        } catch (NoSuchMethodException e) {
            logger.error(e);
            fail();
        } catch (IllegalAccessException e) {
            logger.error(e);
            fail();
        } catch (InvocationTargetException e) {
            logger.error(e);
            fail();
        }
    }

    @Test
    public void parsePaymentTest() {
        try {
            Method method = InputManager.class.getDeclaredMethod("parsePayment", String.class);
            method.setAccessible(true);

            assertEquals(this.pDao.getData().size(), 0);

            method.invoke(this.im, "USD 1000");
            assertEquals(this.pDao.getData().size(), 1);

            method.invoke(this.im, "CZK 1000");
            assertEquals(this.pDao.getData().size(), 2);

        } catch (NoSuchMethodException e) {
            logger.error(e);
            fail();
        } catch (IllegalAccessException e) {
            logger.error(e);
            fail();
        } catch (InvocationTargetException e) {
            logger.error(e);
            fail();
        }
    }
}
