package eu.jhouse.server.device;

import eu.jhouse.server.NetworkException;

/**
 * @author tomekk
 * @since 2010-10-10, 23:09:00
 */
public interface DeviceNetworkConnector {

	String read(String command) throws NetworkException;

	void write(String command, String parameters) throws NetworkException;
}
