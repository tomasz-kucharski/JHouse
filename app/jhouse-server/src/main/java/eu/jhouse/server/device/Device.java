package eu.jhouse.server.device;

import eu.jhouse.server.NetworkException;
import eu.jhouse.server.bus.Bus;

/**
 * @author tomekk
 * @since 2010-10-01, 23:08:27
 */
public abstract class Device {

    protected String id;
    protected String name;

    protected DeviceNetworkConnector networkConnector;
    private Bus bus;

    public void setNetworkConnector(DeviceNetworkConnector networkConnector) {
        this.networkConnector = networkConnector;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public abstract void initOnStartup() throws NetworkException;

    public abstract void read() throws NetworkException;

    public abstract void write() throws NetworkException;
}
