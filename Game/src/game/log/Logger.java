package game.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Semyon Danilov on 06.04.2014.
 */
public class Logger {

    private String className = null;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM hh:mm:ss");

    private Logger(final String className) {
        this.className = className;
    }

    /**
     * Log the message
     *
     * @param message
     */
    public void log(final String message) {
        log(null, message);
    }

    /**
     * Log the message
     *
     * @param tag     additional TAG for a log message (for example ID of a thread)
     * @param message
     */
    public void log(final String tag, final String message) {
        Date date = new Date();
        String dateString = sdf.format(date);
        String logMsg = "[" + dateString + "]" + (tag == null ? "" : ("[" + tag + "]")) + "[" + className + "]" + ": " + message;
        System.out.println(logMsg);
    }

    /**
     * Log the error
     *
     * @param errorMessage
     */
    public void error(final String errorMessage) {
        log("ERROR", errorMessage);
    }

    /**
     * Get logger with class name of loggable specified
     *
     * @param className loggable class's classname
     * @return logger
     */
    public static Logger getLogger(final Class className) {
        return new Logger(className.getSimpleName());
    }

}
