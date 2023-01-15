package streamingsystems;

import com.espertech.esper.common.client.EPCompiled;
import com.espertech.esper.common.client.EventSender;
import com.espertech.esper.common.client.configuration.Configuration;
import com.espertech.esper.compiler.client.CompilerArguments;
import com.espertech.esper.compiler.client.EPCompileException;
import com.espertech.esper.compiler.client.EPCompiler;
import com.espertech.esper.compiler.client.EPCompilerProvider;
import com.espertech.esper.runtime.client.*;
import streamingsystems.eventlisteners.SensorEventListener;
import streamingsystems.events.AverageSpeedEvent;
import streamingsystems.events.SensorEvent;
import streamingsystems.events.TrafficJamEvent;

public class EsperClient {
    private static EsperClient INSTANCE;
    private final EventSender sensorEventSender;

    private EsperClient() {
        Configuration configuration = new Configuration();
        configuration.getCommon().addEventType(SensorEvent.class);
        configuration.getCommon().addEventType(AverageSpeedEvent.class);
        configuration.getCommon().addEventType(TrafficJamEvent.class);
        EPRuntime epRuntime = EPRuntimeProvider.getDefaultRuntime(configuration);

        EPCompiler compiler = EPCompilerProvider.getCompiler();
        CompilerArguments args = new CompilerArguments(configuration);
        epRuntime.initialize();
        EPDeployment epDeployment = null;
        try {
            EPCompiled epCompiled = compiler.compile(getEsperStatementString(), args);
            epDeployment = epRuntime.getDeploymentService().deploy(epCompiled);
        } catch (EPCompileException | EPDeployException e) {
            throw new RuntimeException(e);
        }

        EPStatement epStatement = epRuntime.getDeploymentService().getStatement(epDeployment.getDeploymentId(),
                                                                                "getSensorsEvents"
        );
        epStatement.addListener(new SensorEventListener());
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
