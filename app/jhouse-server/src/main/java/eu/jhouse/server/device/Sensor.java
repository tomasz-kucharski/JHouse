package eu.jhouse.server.device;

/**
 * @author tomekk
 * @since 2010-10-03, 18:40:49
 */
public class Sensor extends Unit {

	private boolean enabled;

	private long lastEnabledChangedTime;

	public boolean isEnabled() {
		return enabled;
	}

	public long getLastEnabledChangedTime() {
		return lastEnabledChangedTime;
	}

	public boolean setEnabled(boolean enabled) {
		if (this.enabled != enabled) {
			this.enabled = enabled;
			this.lastEnabledChangedTime = System.currentTimeMillis();
			return true;
		} else {
			return false;
		}
	}
}
