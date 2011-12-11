package eu.jhouse.server.brain;

import eu.jhouse.server.bus.Command;
import eu.jhouse.server.device.SensorsChangedEvent;

/**
 * @author tomekk
 * @since 2010-10-09, 00:03:55
 */
public class SensorChangedBrainConnector implements Command {
    private Brain brain;

    @Override
    public void execute(Object event) {
        SensorsChangedEvent changedEvent = (SensorsChangedEvent) event;
            brain.thinkForNewFacts(changedEvent.getSensorChanged());
    }

    public void setBrain(Brain brain) {
        this.brain = brain;
    }
}
