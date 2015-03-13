import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Console reader class deals with reading from command line
 */
public class ConsoleReader {

    public String readLine() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
}
