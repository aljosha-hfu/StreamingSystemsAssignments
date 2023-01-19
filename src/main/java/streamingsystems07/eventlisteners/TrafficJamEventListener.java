package streamingsystems07.eventlisteners;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;

public class TrafficJamEventListener implements UpdateListener {
    @Override public void update(
            EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime
    ) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("====> TRAFFIC JAM OCCURRED: for sensor " + newEvents[0].get("sensorId") + "!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
