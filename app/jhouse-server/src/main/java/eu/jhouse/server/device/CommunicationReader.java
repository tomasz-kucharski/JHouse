package eu.jhouse.server.device;

import eu.jhouse.server.NetworkException;
import java.util.Set;

/**
 * @author tomekk
 * @since 2010-10-10, 23:20:29
 */
public interface CommunicationReader {

	void setInputDevices(Set<Device> devices);

	void setOutputDevices(Set<Device> devices);

	void init() throws NetworkException;

	void read() throws NetworkException;

	void close() throws NetworkException;
}
