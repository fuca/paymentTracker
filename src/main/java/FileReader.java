import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * File reader class takes care of reading from file
 */
public class FileReader {

    static final Logger logger = Logger.getLogger(FileReader.class);

    private Path filePath;

    public FileReader (String path) {
        FileSystem fs = FileSystems.getDefault();
        this.filePath = fs.getPath(path);
    }

    public Path getPath() {
        return filePath;
    }

    /**
     * Reads content of file
     * @param path
     * @return list of lines
     * @throws IOException
     */
    public List<String> readInputFile(String path) throws IOException {
        logger.debug("File reader reads input file at " + path);
        List<String> lines = Files.readAllLines(this.filePath, Charset.defaultCharset());
        return lines;
    }
}
