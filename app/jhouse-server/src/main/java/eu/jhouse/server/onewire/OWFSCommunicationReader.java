package eu.jhouse.server.onewire;

import eu.jhouse.server.NetworkException;
import eu.jhouse.server.bus.Bus;
import eu.jhouse.server.device.CommunicationReader;
import eu.jhouse.server.device.CommunicationWriter;
import eu.jhouse.server.device.Device;
import eu.jhouse.server.device.OutputSwitchDevice;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.owfs.jowfsclient.OwfsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tomekk
 * @since 2010-10-01, 23:29:20
 */
public class OWFSCommunicationReader implements CommunicationReader {
	private static final Logger log = LoggerFactory.getLogger(OWFSCommunicationReader.class);

	private OWFSFileSystem fileSystem = new OWFSFileSystem();
	
	private Bus bus;

	private CommunicationWriter communicationWriter;

	private Map<String, Device> inputDevices = new HashMap<String, Device>();

	private Map<String, Device> outputDevices = new HashMap<String, Device>();

	@Override
	public void setInputDevices(Set<Device> inputDevices) {
		for (Device device : inputDevices) {
			device.setBus(bus);
			this.inputDevices.put(device.getId(), device);
		}
	}

	@Override
	public void setOutputDevices(Set<Device> outputDevices) {
		// TODO this definitely must be refactored and removed
		for (Device device : outputDevices) {
			device.setBus(bus);
			if (device instanceof OutputSwitchDevice) {
				((OutputSwitchDevice)device).setConnectionWriter(communicationWriter);
			}
			this.outputDevices.put(device.getId(), device);
		}
	}

	public void setCommunicationWriter(CommunicationWriter communicationWriter) {
		this.communicationWriter = communicationWriter;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public void setFileSystem(OWFSFileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

	@Override
	public void init() throws NetworkException {
		try {
			List<String> devicesDirectory = listRootDirectory();
			scanAndInitializeDevices(devicesDirectory);
			fileSystem.changeDirectoryRoot();
			fileSystem.changeDirectory(OWFSFileSystem.UNCACHED);
		} catch (Exception e) {
			throw new NetworkException("Network initialization error", e);
		}
	}

	@Override
	public void read() throws NetworkException {
		try {
			for (Device device : getAllActiveDevices()) {
				fileSystem.changeDirectory(device.getId());
				try {
					device.read();
				} catch (NetworkException e) {
					log.warn("Device unavailable: Device id: " + device.getId() + ", name: " + device.getName(), e);
				}
				fileSystem.changeDirectoryUp();
			}
		} catch (Exception e) {
			throw new NetworkException("Network initialization error", e);
		}
	}

	@Override
	public void close() throws NetworkException {
		//todo  do we need any cleanup
	}

	public List<String> listRootDirectory() throws OwfsException, IOException {
		fileSystem.changeDirectory(OWFSFileSystem.UNCACHED);
		return fileSystem.listDirectoryAll();
	}

	public void scanAndInitializeDevices(List<String> devicesDirectory) throws Exception {
		for (String deviceDirectory : devicesDirectory) {
			if (isDeviceDirectory(deviceDirectory)) {
				fileSystem.changeDirectory(deviceDirectory);
				initializeDevice(deviceDirectory);
				fileSystem.changeDirectoryUp();
			}
		}
	}

	public boolean isDeviceDirectory(String deviceDirectory) {
		return deviceDirectory.matches("^29.*");
	}

	public void initializeDevice(String deviceDirectory) throws Exception {
		Device device = inputDevices.get(deviceDirectory);
		if (device == null) {
			device = outputDevices.get(deviceDirectory);
		}
		if (device != null) {
			device.setNetworkConnector(new OWFSDeviceNetworkConnector(fileSystem.getCurrentPath(), fileSystem.getClient()));
			device.initOnStartup();
		}
	}

	public Set<Device> getAllActiveDevices() throws OwfsException, IOException {
		Set<Device> set = new HashSet<Device>();
		for (String deviceDirectory : fileSystem.listDirectoryAll()) {
			if (isDeviceDirectory(deviceDirectory)) {
				Device device = inputDevices.get(deviceDirectory);
				if (device != null) {
					set.add(device);
				} else {
					logUnmappedDevices(deviceDirectory);
				}
			}
		}
		return set;
	}

	private void logUnmappedDevices(String deviceDirectory) {
//        System.out.println("Unmapped device: "+deviceDirectory);
	}
}
