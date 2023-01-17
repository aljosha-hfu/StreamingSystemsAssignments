package streamingsystems.eventlisteners;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;

import java.util.Arrays;

/**
 * Event listener for avg speed events
 */
public class AverageSpeedEventListener implements UpdateListener {
    @Override public void update(
            EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime
    ) {
        // newEvents enthäht die Ereignisinstanzen,
        // die die Anfrage erfüllen

        //System.out.println(Arrays.toString(newEvents));
//        Arrays.stream(newEvents).forEach(x -> System.out.println("Average Speed: for sensor "
//                                                                 + x.get("sensorId")
//                                                                 + ": "
//                                                                 + x.get("averageSpeed")));
    }
}
