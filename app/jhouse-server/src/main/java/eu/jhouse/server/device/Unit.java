package eu.jhouse.server.device;

/**
 * @author tomekk
 * @since 2010-10-03, 02:12:24
 */
public class Unit {

	private String name;

	private Device device;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
