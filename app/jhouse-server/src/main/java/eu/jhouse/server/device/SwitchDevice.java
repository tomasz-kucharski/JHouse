package eu.jhouse.server.device;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomekk
 * @since 2010-10-02, 00:36:34
 */
public abstract class SwitchDevice<T extends Unit> extends Device {

	private final int numberOfUnits;

	protected List<T> units = new ArrayList<T>();

	protected SwitchDevice(int numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
		this.units = new ArrayList<T>(numberOfUnits);
	}

	public void setUnits(List<T> units) {
		if (units.size() != numberOfUnits) {
			throw new IllegalArgumentException("Position must be between 0 and " + numberOfUnits);
		}
		this.units = units;

		for (Unit unit : this.units) {
			unit.setDevice(this);
		}
	}


	public boolean[] convertToBooleanArray(String stateString) {
		String values[] = stateString.split(",");
		boolean state[] = new boolean[values.length];
		for (int i = 0; i < values.length; i++) {
			state[i] = (Integer.parseInt(values[i]) == 1);
		}
		return state;
	}
}
