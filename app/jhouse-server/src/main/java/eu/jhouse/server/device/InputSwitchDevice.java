package eu.jhouse.server.device;

import eu.jhouse.server.NetworkException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tomekk
 * @since 2010-10-01, 23:05:17
 */
public class InputSwitchDevice extends SwitchDevice<Sensor> {

	private static final String SENSED_ALL = "sensed.ALL";

	private static final String LATCH_RESET = "0,0,0,0,0,0,0,0";

	private static final String LATCH_ALL = "latch.ALL";

	private static final int NUMBER_OF_SENSORS = 8;

	protected InputSwitchDevice() {
		super(NUMBER_OF_SENSORS);
	}

	public void initOnStartup() throws NetworkException {
		read();
		setLatchAlarm();
	}

	public void read() throws NetworkException {
		Set<Sensor> sensorChanged = new HashSet<Sensor>();
		String s = networkConnector.read(SENSED_ALL);
		boolean[] values = convertToBooleanArray(s);
		for (int i = 0; i < values.length; i++) {
			boolean changed = units.get(i).setEnabled(values[i]);
			if (changed) {
				sensorChanged.add(units.get(i));
			}
		}
		sendNotificationEventToBus(sensorChanged);
	}

	private void sendNotificationEventToBus(Set<Sensor> sensorChanged) {
		if (sensorChanged.size() > 0) {
			getBus().dispatch(new SensorsChangedEvent(sensorChanged));
		}
	}

	private void setLatchAlarm() {
		// todo
	}

	@Override
	public void write() throws NetworkException {
		latchReset();
	}

	private void latchReset() throws NetworkException {
		networkConnector.write(LATCH_ALL, LATCH_RESET);
	}
}
