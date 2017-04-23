package ui.tools;
import javax.swing.text.JTextComponent;

public class Logger {
    private JTextComponent log;

    public static Logger getLogger(JTextComponent log) {
        Logger logger = new Logger();
        logger.log = log;
        return logger;
    }

    public void debug(String message) {
        log.setText(log.getText() + "\nDebug: " + message);
    }

    public void trace(String message) {
        log.setText(log.getText() + "\n" + message);
    }

    public void error(String message) {
        log.setText(log.getText() + "\nERROR: " + message);
    }
}
