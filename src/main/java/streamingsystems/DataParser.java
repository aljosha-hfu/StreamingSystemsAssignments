package streamingsystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * A class for starting the data processing pipeline.
 */
public class DataParser implements Serializable {
    /**
     * The windows size in seconds.
     */
    public static final int WINDOW_SIZE_SECONDS = 30;

    /**
     * The main method for this class.
     */
    public void parse() {
        Logger logger = LoggerFactory.getLogger(DataParser.class.getName());
        logger.info("Starting...");



        logger.info("Terminating...");
    }
}
