package automation.test;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author nathee
 */
public class Log4jExample {

    private static final Logger LOGGER = Logger.getLogger("Log4jExample");

    public static void main(String[] args) {
        FileHandler fh = null;
        try {
            fh = new FileHandler("MyLogFile.log");
        } catch (IOException ex) {
            Logger.getLogger(Log4jExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (fh != null) {
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            LOGGER.info("info msg");
            LOGGER.warning("warning message");
            LOGGER.severe("error message");
        }
    }
}
