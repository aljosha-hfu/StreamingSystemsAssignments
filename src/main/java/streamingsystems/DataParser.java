package streamingsystems;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.EPDeployException;
import com.espertech.esper.runtime.client.EPDeployment;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPRuntimeProvider;
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

        String q1 = "@name('getSensorsEvents‘) select id, speed from SensorEvent;\n";
        String q2 = "create context EventsById partition by id from SensorEvent;\n";

        Configuration configuration = new Configuration();
        configuration.getCommon().addEventType(AverageSpeedEvent.class);
        configuration.getCommon().addEventType(TrafficJamEvent.class);

        EPCompiler compiler = EPCompilerProvider.getCompiler();
        CompilerArguments args = new CompilerArguments(configuration);
        EPCompiled epCompiled = compiler.compile(q1 + q2, args);

        EPRuntime runtime = EPRuntimeProvider.getDefaultRuntime(configuration);
        runtime.initialize();
        EPDeployment deployment = runtime.getDeploymentService().deploy(epCompiled);

        logger.info("Terminating...");
    }
}
