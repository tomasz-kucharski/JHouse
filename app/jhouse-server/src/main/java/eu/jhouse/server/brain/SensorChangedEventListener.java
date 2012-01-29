package eu.jhouse.server.brain;

import eu.jhouse.server.bus.Command;
import eu.jhouse.server.device.Sensor;
import eu.jhouse.server.device.SensorsChangedEvent;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tomekk
 * @since 2010-10-07, 22:18:04
 */
public class SensorChangedEventListener implements Command {
	private static final Logger log = LoggerFactory.getLogger(SensorChangedEventListener.class);

	@Override
	public void execute(Object event) {
		SensorsChangedEvent changedEvent = (SensorsChangedEvent) event;
		for (Sensor sensor : changedEvent.getSensorChanged()) {
			logSensor(sensor);
		}
	}

	private void logSensor(Sensor sensor) {
		log.info("Sensor: name=" + sensor.getName() + ", state=" + sensor.isEnabled() + ", lastEnabledChangedTime=" + new Date(sensor.getLastEnabledChangedTime()));
	}


}
