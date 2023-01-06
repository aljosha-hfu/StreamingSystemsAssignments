package streamingsystems;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamingsystems.events.AverageSpeedEvent;
import streamingsystems.events.TrafficJamEvent;

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
    public void parse() throws EPCompileException, EPDeployException {
        Logger logger = LoggerFactory.getLogger(DataParser.class.getName());
        logger.info("Starting...");

        Configuration configuration = new Configuration();
        configuration.getCommon().addEventType(AverageSpeedEvent.class);
        configuration.getCommon().addEventType(TrafficJamEvent.class);

        String getSensorEventsStatement = "@name('getSensorsEvents‘) select id, speed from AverageSpeedEvent;\n";
        String createEventsByIdContextStatement = "create context EventsById partition by id from AverageSpeedEvent;\n";

        EPCompiler compiler = EPCompilerProvider.getCompiler();
        CompilerArguments args = new CompilerArguments(configuration);
        EPCompiled epCompiled = compiler.compile(getSensorEventsStatement + createEventsByIdContextStatement, args);

        EPRuntime epRuntime = EPRuntimeProvider.getDefaultRuntime(configuration);
        epRuntime.initialize();

        EPDeployment epDeployment = epRuntime.getDeploymentService().deploy(epCompiled);

        EPStatement epStatement = epRuntime.getDeploymentService().getStatement(epDeployment.getDeploymentId(),
                                                                                "getSensorsEvents"
        );

        // Register a listener for the statement
        epStatement.addListener(new SensorEventListener());

        logger.info("Terminating...");
    }

}
