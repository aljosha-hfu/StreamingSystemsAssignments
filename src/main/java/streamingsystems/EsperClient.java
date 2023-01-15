package streamingsystems;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.EventSender;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.*;
import streamingsystems.eventlisteners.AverageSpeedEventListener;
import streamingsystems.eventlisteners.SensorEventListener;
import streamingsystems.events.AverageSpeedEvent;
import streamingsystems.events.SensorEvent;
import streamingsystems.events.TrafficJamEvent;

public class EsperClient {
    private static EsperClient INSTANCE;
    private final EventSender sensorEventSender;

    private EsperClient() {
        Configuration configuration = new Configuration();

        // Add all classes that are used as events
        configuration.getCommon().addEventType(SensorEvent.class);
        configuration.getCommon().addEventType(AverageSpeedEvent.class);
        configuration.getCommon().addEventType(TrafficJamEvent.class);

        EPRuntime epRuntime = EPRuntimeProvider.getDefaultRuntime(configuration);

        EPCompiler compiler = EPCompilerProvider.getCompiler();
        CompilerArguments args = new CompilerArguments(configuration);
        epRuntime.initialize();
        EPDeployment epDeployment = null;
        EPDeploymentService epDeploymentService = null;
        try {
            EPCompiled epCompiled = compiler.compile(getEsperStatementString(), args);
            epDeploymentService = epRuntime.getDeploymentService();
            epDeployment = epDeploymentService.deploy(epCompiled);
        } catch (EPCompileException | EPDeployException e) {
            throw new RuntimeException(e);
        }

        // Add a listener to the getSensorEvents event
        EPStatement getSensorsEventsStatement = epDeploymentService.getStatement(epDeployment.getDeploymentId(),
                                                                                 "getSensorsEvents"
        );
        getSensorsEventsStatement.addListener(new SensorEventListener());

        // Add a listener to the getAverageSpeedEvents event
        EPStatement getAverageSpeedEventsStatement = epDeploymentService.getStatement(epDeployment.getDeploymentId(),
                                                                                      "getAverageSpeedEvents"
        );
        getAverageSpeedEventsStatement.addListener(new AverageSpeedEventListener());

        // Initialize the sensor event sender
        sensorEventSender = epRuntime.getEventService().getEventSender("SensorEvent");
    }

    /**
     * @return string representation of the needed esper statements.
     */
    public static String getEsperStatementString() {
        return """
               // Event: getSensorsEvents
               @name('getSensorsEvents')
               select sensorId, speed
               from SensorEvent;
                              
               // Event: getAverageSpeedEvents
               @name('getAverageSpeedEvents')
               insert into AverageSpeedEvent
               select sensorId, avg(speed) as averageSpeed from SensorEvent#time(30 sec)
               group by sensorId;
               """;
    }


    public static EsperClient getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new EsperClient();
        }
        return INSTANCE;
    }

    public EventSender getSensorEventSender() {
        return sensorEventSender;
    }
}
