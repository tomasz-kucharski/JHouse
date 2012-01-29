package eu.jhouse.server.device;

import java.util.Set;

/**
 * @author tomekk
 * @since 2010-10-07, 22:15:40
 */
public class SensorsChangedEvent {

	private Set<Sensor> sensorChanged;

	public SensorsChangedEvent(Set<Sensor> sensorChanged) {
		this.sensorChanged = sensorChanged;
	}

	public Set<Sensor> getSensorChanged() {
		return sensorChanged;
	}
}
