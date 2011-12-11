package eu.jhouse.server.onewire;

import eu.jhouse.server.NetworkException;
import eu.jhouse.server.device.CommunicationWriter;
import eu.jhouse.server.device.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author tomekk
 * @since 2010-10-01, 23:29:20
 */
public class OWFSCommunicationWriter implements CommunicationWriter {
    private static final Logger log = LoggerFactory.getLogger(OWFSCommunicationWriter.class);

    private Set<Device> devices = new HashSet<Device>();

    @Override
    public void flush() {
        for (Device device : devices) {
            try {
                device.write();
            } catch (NetworkException e) {
                log.warn("Device communication problem. Device:"+device,e);
            }
        }
        clear();
    }

    @Override
    public void clear() {
        devices.clear();
    }

    @Override
    public void push(Device device) {
        devices.add(device);
    }
}
