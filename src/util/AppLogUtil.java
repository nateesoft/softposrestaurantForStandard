package util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author nathee
 */
public class AppLogUtil {

    static SimpleDateFormat logDateFmt = new SimpleDateFormat("yyyy-MM-dd");
    static FileHandler fh = null;

    public static void log(Class t, String type, Exception e) {
        Logger logger = Logger.getLogger(t.getName());
        if (fh == null) {
            try {
                fh = new FileHandler("application-" + logDateFmt.format(new Date()) + ".log", true);
            } catch (IOException ex) {
            }
        }

        if (fh != null) {
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            
            String msg = e.getMessage();
            int lineNumber = e.getStackTrace()[0].getLineNumber();

            switch (type) {
                case "error":
                    logger.log(Level.SEVERE, "{0}:{1}:{2}", new Object[]{msg, t.getName(), lineNumber});
                    break;
                case "warning":
                    logger.log(Level.WARNING, "{0}:{1}:{2}", new Object[]{msg, t.getName(), lineNumber});
                    logger.warning(msg);
                    break;
                default:
                    logger.log(Level.INFO, "{0}:{1}:{2}", new Object[]{msg, t.getName(), lineNumber});
                    logger.info(msg);
                    break;
            }
        }
    }
}
