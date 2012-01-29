package eu.jhouse.server.onewire;

import eu.jhouse.server.NetworkException;
import eu.jhouse.server.device.DeviceNetworkConnector;
import org.owfs.jowfsclient.OwfsClient;

/**
 * @author tomekk
 * @since 2010-10-10, 22:59:52
 */
public class OWFSDeviceNetworkConnector implements DeviceNetworkConnector {

	private String path;

	private OwfsClient client;

	private static final String SLASH = "/";

	public OWFSDeviceNetworkConnector(String path, OwfsClient client) {
		this.path = path;
		this.client = client;
	}

	@Override
	public String read(String command) throws NetworkException {
		try {
			return client.read(path + SLASH + command);
		} catch (Exception e) {
			throw new NetworkException("Error reading. Path: '" + path + "', command: '" + command + "'", e);
		}
	}

	@Override
	public void write(String command, String parameters) throws NetworkException {
		try {
			client.write(path + SLASH + command, parameters);
		} catch (Exception e) {
			throw new NetworkException("Error writing. Path: '" + path + "', command: '" + command + "'", e);
		}
	}
}
